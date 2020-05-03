package com.zemoga.porfolio.external.datasources.repositories;

import com.zemoga.porfolio.external.datasources.entities.ProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity,String> {
}
