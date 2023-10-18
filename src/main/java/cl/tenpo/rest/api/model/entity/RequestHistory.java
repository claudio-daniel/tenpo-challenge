package cl.tenpo.rest.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "request_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requestId;
    private String httpMethod;
    private Integer responseStatus;
    private String responseBody;
    private String url;
    private ZonedDateTime timestamp;
}