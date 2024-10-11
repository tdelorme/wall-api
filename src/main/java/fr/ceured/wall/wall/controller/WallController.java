package fr.ceured.wall.wall.controller;

import fr.ceured.wall.wall.model.WallResponse;
import fr.ceured.wall.wall.service.WallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wall")
public class WallController {

    private final WallService wallService;

    @GetMapping("")
    public ResponseEntity<List<WallResponse>> listProject() {
        return ResponseEntity.ok(wallService.getLatestPipelineProjects());
    }

    @GetMapping("/branch")
    public ResponseEntity<Map<String, List<WallResponse>>> listProjetsBranchs() {
        return ResponseEntity.ok(wallService.getPipelineProjectsOnSpecificBranch());
    }

}
