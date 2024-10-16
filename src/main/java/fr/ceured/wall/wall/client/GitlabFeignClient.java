package fr.ceured.wall.wall.client;

import fr.ceured.wall.wall.model.GitlabApiResponse;
import fr.ceured.wall.wall.model.GitlabProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        value = "gitlab-feign-client",
        url="${wall.gitlab.baseHost}/api/v4/projects"
)
public interface GitlabFeignClient {

    @GetMapping("/{id}/pipelines/latest")
    GitlabApiResponse getLatestPipeline(@RequestHeader("PRIVATE-TOKEN") String privateToken,
                                        @PathVariable("id") String id);

    @GetMapping("/{id}/pipelines")
    List<GitlabApiResponse> getPipelineByBranch(@RequestHeader("PRIVATE-TOKEN") String privateToken,
                                                @PathVariable("id") String id,
                                                @RequestParam("ref") String branch,
                                                @RequestParam("per_page") Integer perPage,
                                                @RequestParam("page") Integer page);

    @GetMapping("/{id}")
    GitlabProjectResponse getNameProject(@RequestHeader("PRIVATE-TOKEN") String privateToken,
                                         @PathVariable("id") String id);
}
