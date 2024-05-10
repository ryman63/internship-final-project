package springboot.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPerformance {
    String taskName;
    LocalDateTime writeDate;
    String status;
}
