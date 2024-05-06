package springboot.models;

import lombok.Data;

@Data
public class GitlabUser {
    private String name;
    private String email;
    private String username;
    private String bio;
    private String location;
    private boolean force_random_password = true;
}
