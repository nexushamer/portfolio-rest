package com.zemoga.porfolio.adapters.mappers;

import com.zemoga.porfolio.core.domain.Profile;
import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity mapModelToEntity(Profile model);

    @Mapping(target = "tweets", ignore = true)
    @Mapping(target = "pictureFileName", ignore = true)
    Profile mapEntityToModel(ProfileEntity entity);
}
