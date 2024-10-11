package fr.ceured.wall.gitlab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitlabApiResponse {
    private int id;
    private int iid;
    @JsonProperty("project_id")
    private int projectId;
    private String name;
    private String sha;
    private String ref;
    private String status;
    private String source;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("before_sha")
    private String beforeSha;
    private boolean tag;
    private GitlabUser user;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("finished_at")
    private LocalDateTime finishedAt;
    private int duration;
    @JsonProperty("queued_duration")
    private int queuedDuration;
}
