package fr.ceured.wall.gitlab.controller;

import fr.ceured.wall.gitlab.model.GitlabResponse;
import fr.ceured.wall.gitlab.service.GitlabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gitlab")
public class GitlabController {

    private final GitlabService gitlabService;

    @GetMapping("")
    public ResponseEntity<List<GitlabResponse>> listProject() {
        return ResponseEntity.ok(gitlabService.getLatestPipelineProjects());
    }

    @GetMapping("/branch")
    public ResponseEntity<Map<String, List<GitlabResponse>>> listProjetsBranchs() {
        return ResponseEntity.ok(gitlabService.getPipelineProjectsOnSpecificBranch());
    }

}
