package springboot.dto;

import lombok.Data;

@Data
public class GitlabUser {
    private String name;
    private String email;
    private String username;
    private String bio;
    private String location;
    private String password;
    private boolean force_random_password = false;
}
