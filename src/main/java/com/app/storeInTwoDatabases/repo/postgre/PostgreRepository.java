package com.app.storeInTwoDatabases.repo.postgre;

import com.app.storeInTwoDatabases.model.postgre.PostgreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgreRepository extends JpaRepository<PostgreEntity,Integer> {
}
