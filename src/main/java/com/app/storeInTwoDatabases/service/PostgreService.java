package com.app.storeInTwoDatabases.service;

import com.app.storeInTwoDatabases.model.postgre.PostgreEntity;
import com.app.storeInTwoDatabases.repo.postgre.PostgreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostgreService {

    @Autowired
    private PostgreRepository repo;

    @Transactional
    public PostgreEntity saveEntity(PostgreEntity entity)
    {
        return repo.save(entity);
    }


    @Transactional(readOnly = true)
    public List<PostgreEntity> getAll()
    {
        return repo.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

}
