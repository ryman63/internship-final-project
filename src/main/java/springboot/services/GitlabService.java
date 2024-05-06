package springboot.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.mapper.ParticipantGitlabUserMapper;
import springboot.models.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GitlabService {

    @Value("${gitlab.apiUrl}")
    private String gitLabApiUrl;

    @Value("${gitlab.token}")
    private String gitLabToken;
    RestTemplate restTemplate = new RestTemplate();


    public GitlabService() {

    }

    public void deleteUserById(String userId) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String url = gitLabApiUrl + "/users/" + userId;

            // посылаем запрос на удаление пользователя из гитлаба
            restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public void deleteProjectById(String projectId) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String url = gitLabApiUrl + "/projects/" + projectId;

            // посылаем запрос на удаление пользователя из гитлаба
            restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Long createUser(GitlabUser gitlabUser) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String userUrl = gitLabApiUrl + "/users";
            HttpEntity<GitlabUser> requestEntity = new HttpEntity<>(gitlabUser, headers);

            // Отправляем запрос
            ResponseEntity<GitlabUser> response = restTemplate.exchange(
                    userUrl, // URL API
                    HttpMethod.POST, // Метод запроса
                    requestEntity, // Тело запроса и заголовки
                    GitlabUser.class); // Ожидаемый тип ответа

            return getGitlabUserId(gitlabUser.getUsername());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Long getGitlabUserId(String username){
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String projectUrl = gitLabApiUrl + "/users?username=" + username;

            ResponseEntity<String> response = restTemplate.exchange(projectUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            JSONObject responseObject = new JSONArray(response.getBody()).getJSONObject(0);

            return responseObject.getLong("id");
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Long createProject(Task task) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String projectUrl = gitLabApiUrl + "/projects";

            HttpEntity<String> requestEntity = new HttpEntity<>(
                    "{\"name\": \"" + task.getName() + "\", "
                            + "\"initialize_with_readme\": \"true\"}", headers);

            ResponseEntity<String> response = restTemplate.exchange(projectUrl, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() != HttpStatus.CREATED)
                throw new Exception("Project not created");

            JSONObject responseObject = new JSONObject(response.getBody());

            return responseObject.getLong("id");
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public CheckTask getLastForkCommit(Fork fork) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String url = gitLabApiUrl + "/projects/" + fork.getGitLabRepositoryId() + "/repository/commits?author=" + fork.getUser().getUsername() +"&order_by=created_at&sort=desc&per_page=1";

            // Отправка запроса
            ResponseEntity<String> responseCommits = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            JSONArray jsonArray = new JSONArray(responseCommits.getBody());

            if(jsonArray.length() == 0)
                return null;

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            CheckTask checkTask = new CheckTask(fork.getTask().getId(),
                    fork.getTask().getName(),
                    fork.getUser().getId(),
                    fork.getUser().getUsername(),
                    OffsetDateTime.parse(jsonObject.getString("committed_date")),
                    jsonObject.getString("author_name"),
                    jsonObject.getString("web_url"));

            return checkTask;
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Long createFork(Task task, String participantUsername) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            // создаём форк таски
            HttpEntity<String> requestEntity = new HttpEntity<>(
                    "{\"name\": \"" + task.getName() + "\", "
                            + "\"namespace_path\": \"" + participantUsername + "\", "
                            + "\"path\": \"" + task.getName() + "\"}", headers);

            String url = gitLabApiUrl + "/projects/" + task.getGitLabRepositoryId() + "/fork";

            // Отправка запроса
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            JSONObject responseJsonObject = new JSONObject(response.getBody());

            // тут получаем из ответа гитлаба id
            return responseJsonObject.getLong("id");
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }
}
