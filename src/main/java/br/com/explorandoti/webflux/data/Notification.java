package br.com.explorandoti.webflux.data;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
    private int id;
    private LocalDateTime createdAt;
    private String message;
}