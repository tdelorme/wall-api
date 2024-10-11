package fr.ceured.wall.gitlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitlabProjectResponse {
    private String id;
    private String name;
}
