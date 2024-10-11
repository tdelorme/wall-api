package fr.ceured.wall.wall.mapper;

import fr.ceured.wall.wall.model.GitlabApiResponse;
import fr.ceured.wall.wall.model.WallResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface WallMapper {

    @Mapping(target = "name", source="webUrl")
    @Mapping(source = "ref", target="branchName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "source", target = "source")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "user.username", target = "owner")
    @Mapping(source = "duration", target = "launchSinceSeconds")
    @Mapping(source = "queuedDuration", target = "waitingSinceSeconds")
    WallResponse toGitlabResponse(GitlabApiResponse source);
}
