package springboot.services;

import com.google.common.annotations.Beta;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.dto.*;
import springboot.entities.*;
import springboot.mapper.ParticipantEntityGitlabUserMapper;
import springboot.repositories.ForkRepository;
import springboot.repositories.ParticipantRepository;

import java.time.OffsetDateTime;

@Service
public class GitlabService {

    ForkRepository forkRepository;
    ParticipantRepository participantRepository;

    public GitlabService(ForkRepository forkRepository, ParticipantRepository participantRepository) {
        this.forkRepository = forkRepository;
        this.participantRepository = participantRepository;
    }

    @Value("${gitlab.apiUrl}")
    private String gitLabApiUrl;

    @Value("${gitlab.token}")
    private String gitLabToken;

    @Value("${gitlab.newUserPassword}")
    private String gitlabNewUserPassword;

    RestTemplate restTemplate = new RestTemplate();

    @Async("threadPoolTaskExecutor")
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

    @Async("threadPoolTaskExecutor")
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

    public Long createUser(Long participantId) {
        try {
            ParticipantEntity participant = participantRepository.getById(participantId);

            GitlabUser gitlabUser = ParticipantEntityGitlabUserMapper.MAPPER.toGitLabUser(participant);

            gitlabUser.setPassword(gitlabNewUserPassword);

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

    public Long createProject(TaskDto taskDto) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String projectUrl = gitLabApiUrl + "/projects";

            HttpEntity<CreateProjectGitlabDto> requestEntity = new HttpEntity<>(
                    CreateProjectGitlabDto.builder().
                            name(taskDto.getName())
                            .initialize_with_readme(true)
                            .build(),
                    headers);

            ResponseEntity<String> response = restTemplate.exchange(projectUrl, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode() != HttpStatus.CREATED)
                throw new Exception("Project not created");

            JSONObject responseObject = new JSONObject(response.getBody());

            return responseObject.getLong("id");
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public CheckTask getLastForkCommit(ForkEntity fork) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            String url = gitLabApiUrl + "/projects/" + fork.getGitLabRepositoryId() + "/repository/commits?author=" + fork.getParticipant().getEmail() +"&order_by=created_at&sort=desc&per_page=1";

            // Отправка запроса
            ResponseEntity<String> responseCommits = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            JSONArray jsonArray = new JSONArray(responseCommits.getBody());

            if(jsonArray.length() == 0)
                return null;

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            CheckTask checkTask = new CheckTask(
                    jsonObject.getString("id"),
                    fork.getTask().getId(),
                    fork.getTask().getName(),
                    fork.getParticipant().getId(),
                    fork.getParticipant().getUsername(),
                    OffsetDateTime.parse(jsonObject.getString("committed_date")),
                    jsonObject.getString("author_name"),
                    jsonObject.getString("web_url"));

            return checkTask;
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    public Long createFork(TaskEntity taskEntity, String participantUsername) {
        try {
            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            CreateForkDto.builder()
                    .name(taskEntity.getName())
                    .namespace_path(participantUsername)
                    .path(taskEntity.getName())
                    .build();

            // создаём форк таски
            HttpEntity<CreateForkDto> requestEntity = new HttpEntity<>(
                    CreateForkDto.builder()
                    .name(taskEntity.getName())
                    .namespace_path(participantUsername)
                    .path(taskEntity.getName())
                    .build(), headers);

            String url = gitLabApiUrl + "/projects/" + taskEntity.getGitLabRepositoryId() + "/fork";

            // Отправка запроса
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            JSONObject responseJsonObject = new JSONObject(response.getBody());

            // тут получаем из ответа гитлаба id
            return responseJsonObject.getLong("id");
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    @Async("threadPoolTaskExecutor")
    public void createCommentForCommit(Long taskId, Long participantId, Comment comment) {
        try {
            ForkEntity forkEntity = forkRepository.getForkByTaskAndParticipant(taskId, participantId);
            CheckTask checkTask = getLastForkCommit(forkEntity);
            if(checkTask == null)
                throw new Exception("коммит не найден");

            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(gitLabToken);
            headers.add("Content-Type", "application/json");

            // создаём коммент под коммитом
            HttpEntity<Comment> requestEntity = new HttpEntity<>(comment, headers);

            String url = gitLabApiUrl + "/projects/"
                    + forkEntity.getGitLabRepositoryId()
                    + "/repository/commits/"
                    + checkTask.getCommitSha()
                    +"/comments";

            // Отправка запроса
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
    }
}
