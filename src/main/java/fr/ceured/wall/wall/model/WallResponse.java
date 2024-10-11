package fr.ceured.wall.wall.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WallResponse {

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
