package com.app.storeInTwoDatabases.controller;

import com.app.storeInTwoDatabases.model.mysql.MysqlEntity;
import com.app.storeInTwoDatabases.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mysql")
public class MysqlController {

    @Autowired
    private MysqlService service;

    @PostMapping("/save")
    public ResponseEntity<MysqlEntity> save(MysqlEntity entity)
    {
        return new ResponseEntity<>(service.saveEntity(entity) ,HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<MysqlEntity>> getAll()
    {
        return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
    }
}
