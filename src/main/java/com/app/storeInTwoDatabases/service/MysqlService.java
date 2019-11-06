package com.app.storeInTwoDatabases.service;

import com.app.storeInTwoDatabases.model.mysql.MysqlEntity;
import com.app.storeInTwoDatabases.repo.mysql.MysqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MysqlService {

    @Autowired
    private MysqlRepository repo;

    @Transactional
    public MysqlEntity saveEntity(MysqlEntity entity)
    {
        return repo.save(entity);
    }


    @Transactional(readOnly = true)
    public List<MysqlEntity> getAll()
    {
        return repo.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}
