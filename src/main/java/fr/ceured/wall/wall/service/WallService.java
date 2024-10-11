package fr.ceured.wall.wall.service;

import fr.ceured.wall.wall.client.GitlabFeignClient;
import fr.ceured.wall.wall.mapper.WallMapper;
import fr.ceured.wall.wall.model.GitlabApiResponse;
import fr.ceured.wall.wall.model.WallResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class WallService {

    public static final String PIPELINES_PATH = "/-/pipelines/";
    @Value("${wall.gitlab.ids}")
    private List<String> ids;

    @Value("${wall.gitlab.branch}")
    private List<String> branch;

    @Value("${wall.gitlab.path}")
    private String path;

    @Value("${wall.gitlab.baseHost}")
    private String baseHost;

    @Value("${wall.gitlab.token}")
    private String privateToken;

    private final GitlabFeignClient client;
    private final WallMapper mapper;


    public List<WallResponse> getLatestPipelineProjects() {

        List<WallResponse> result = new ArrayList<>();

        ids.forEach(id -> {
            GitlabApiResponse response = client.getLatestPipeline(privateToken, id);
            result.add(mapWallResponse(response));
        });

        return result;
    }

    public Map<String, List<WallResponse>> getPipelineProjectsOnSpecificBranch() {

        Map<String, List<WallResponse>> resultMap= new HashMap<>();

        ids.forEach(id -> {
            String projectName = client.getNameProject(privateToken, id).getName();

            List<GitlabApiResponse> gitlabApiResponses = new ArrayList<>();

            branch.forEach(branchName -> {
                GitlabApiResponse response = getLastPipelineByIdProjectAndBranch(id, branchName);
                gitlabApiResponses.add(response);
            });

            List<WallResponse> test = gitlabApiResponses.stream()
                    .filter(Objects::nonNull)
                    .map(this::mapWallResponse)
                    .toList();

            resultMap.put(projectName, test);
        });

        return resultMap;
    }

    private WallResponse mapWallResponse(GitlabApiResponse response) {
        WallResponse wallResponse = mapper.toGitlabResponse(response);
        String[] tab = cleanUrlPipeline(wallResponse).split(PIPELINES_PATH);
        wallResponse.setName(tab[0]);
        wallResponse.setPipelineNumber(Integer.parseInt(tab[1]));
        return wallResponse;
    }

    private String cleanUrlPipeline(WallResponse wallResponse) {
        return wallResponse.getName().replace(baseHost, "").replace(path, "");
    }

    private GitlabApiResponse getLastPipelineByIdProjectAndBranch(String id, String branchName) {
        List<GitlabApiResponse> branch = client.getPipelineByBranch(privateToken, id, branchName, 1, 1);
        return CollectionUtils.isEmpty(branch) ? null : branch.get(0);
    }
}
