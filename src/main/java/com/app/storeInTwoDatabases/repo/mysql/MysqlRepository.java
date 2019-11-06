package com.app.storeInTwoDatabases.repo.mysql;

import com.app.storeInTwoDatabases.model.mysql.MysqlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlRepository extends JpaRepository<MysqlEntity,Integer> {
}
