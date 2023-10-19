package cl.tenpo.rest.api.controller.interceptor;

import cl.tenpo.rest.api.model.exception.ApiBadRequestException;
import cl.tenpo.rest.api.model.exception.ErrorDetail;
import cl.tenpo.rest.api.model.exception.RequestLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Value("${spring.application.max.request.count}")
    private Integer maxRequest;

    @Value("${spring.application.max.request.seconds-interval}")
    private Integer durationInSeconds;

    private final Map<String, Bucket> bucketMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var apiKey = validateApiKey(request);
        var remainingTokens = validateRateLimit(apiKey);

        response.addHeader("X-Rate-Limit-Remaining", remainingTokens.toString());
        return true;
    }

    private String validateApiKey(HttpServletRequest request) {
        var apiKey = request.getHeader("api-key");
        if (apiKey == null || apiKey.isEmpty()) {
            var apiError = ErrorDetail.builder().code("MISSING_API_KEY").message("Key is required to complete request").build();
            throw new ApiBadRequestException("Missing Header", apiError);
        }
        return apiKey;
    }

    private Long validateRateLimit(String apiKey) {
        var tokenBucket = resolveBucket(apiKey);
        var bucketAvailability = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (!bucketAvailability.isConsumed()) {
            long secondsToRefill = bucketAvailability.getNanosToWaitForRefill() / 1_000_000_000;
            var availableInMessage = String.format("Available in %s seconds", secondsToRefill);
            var apiError = ErrorDetail.builder()
                    .code("X-Rate-Limit-Retry-After-Seconds")
                    .message(availableInMessage)
                    .build();
            throw new RequestLimitExceededException("You have exhausted your API Request Quota", apiError);
        }
        return bucketAvailability.getRemainingTokens();
    }

    private Bucket resolveBucket(String apiKey) {
        return bucketMap.computeIfAbsent(apiKey, this::buildBucket);
    }

    private Bucket buildBucket(String apiKey) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(maxRequest, Refill.intervally(maxRequest, Duration.ofSeconds(durationInSeconds))))
                .build();
    }
}