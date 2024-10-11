package fr.ceured.wall.gitlab.service;

import fr.ceured.wall.gitlab.client.GitlabFeignClient;
import fr.ceured.wall.gitlab.mapper.GitlabMapper;
import fr.ceured.wall.gitlab.model.GitlabApiResponse;
import fr.ceured.wall.gitlab.model.GitlabProjectResponse;
import fr.ceured.wall.gitlab.model.GitlabResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitlabService {

    @Value("${wall.gitlab.ids}")
    private List<String> ids;

    @Value("${wall.gitlab.token}")
    private String privateToken;

    private final GitlabFeignClient client;
    private final GitlabMapper mapper;


    public List<GitlabResponse> getLatestPipelineProjects() {

        List<GitlabResponse> result = new ArrayList<>();

        ids.forEach(id -> {
            log.info("run for id {}", id);
            GitlabApiResponse response = client.getLatestPipeline(privateToken, id);
            GitlabResponse gitlabResponse = mapper.toGitlabResponse(response);
            String[] tab = gitlabResponse.getName().replace("http://gitlab.ent.local/etude/fmi/java/", "").split("/-/pipelines/");
            gitlabResponse.setName(tab[0]);
            gitlabResponse.setPipelineNumber(Integer.parseInt(tab[1]));
            result.add(gitlabResponse);
        });

        return result;
    }

    public Map<String, List<GitlabResponse>> getPipelineProjectsOnSpecificBranch() {

        Map<String, List<GitlabResponse>> resultMap= new HashMap<>();

        ids.forEach(id -> {
            log.info("run for id {}", id);

            String projectName = this.getNameProjectById(id).getName();

            List<GitlabApiResponse> production = client.getPipelineByBranch(privateToken, id, "production", 1, 1);
            GitlabApiResponse responseProduction = CollectionUtils.isEmpty(production) ? null : production.get(0);

            List<GitlabApiResponse> recette = client.getPipelineByBranch(privateToken, id, "recette", 1, 1);
            GitlabApiResponse responseRecette = CollectionUtils.isEmpty(recette) ? null: recette.get(0);

            List<GitlabApiResponse> tma = client.getPipelineByBranch(privateToken, id, "tma", 1, 1);
            GitlabApiResponse responseTma = CollectionUtils.isEmpty(tma) ? null: tma.get(0);

            List<GitlabApiResponse> develop = client.getPipelineByBranch(privateToken, id, "develop", 1, 1);
            GitlabApiResponse responseDevelop = CollectionUtils.isEmpty(develop) ? null: develop.get(0);

            List<GitlabResponse> test = Stream.of(responseDevelop, responseRecette, responseTma, responseProduction)
                    .filter(Objects::nonNull)
                    .map(response -> {
                        GitlabResponse gitlabResponse = mapper.toGitlabResponse(response);
                        String[] tab = gitlabResponse.getName().replace("http://gitlab.ent.local/etude/fmi/java/", "").split("/-/pipelines/");
                        gitlabResponse.setName(tab[0]);
                        gitlabResponse.setPipelineNumber(Integer.parseInt(tab[1]));
                        return gitlabResponse;
                    })
                    .toList();

            resultMap.put(projectName, test);
        });

        return resultMap;
    }

    public GitlabProjectResponse getNameProjectById(String id) {
        log.info("get name for id {}", id);

        return client.getNameProject(privateToken, id);
    }
}
