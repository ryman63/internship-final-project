package springboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class CheckTask {
    private Long taskId;
    private String taskName;
    private Long usernameId;
    private String username;
    private OffsetDateTime dateLastCommit;
    private String authorLastCommit;
    private String commitUrl;

    public CheckTask(){}
}
