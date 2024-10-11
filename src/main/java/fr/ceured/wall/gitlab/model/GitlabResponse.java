package fr.ceured.wall.gitlab.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitlabResponse {

    private String name;
    private Integer pipelineNumber;
    private String branchName;
    private String status;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String owner;
    private Integer launchSinceSeconds;
    private Integer waitingSinceSeconds;

}
