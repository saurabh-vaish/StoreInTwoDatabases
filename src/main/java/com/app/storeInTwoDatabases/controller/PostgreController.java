package com.app.storeInTwoDatabases.controller;

import com.app.storeInTwoDatabases.model.postgre.PostgreEntity;
import com.app.storeInTwoDatabases.service.PostgreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/postgre")
public class PostgreController {

    @Autowired
    private PostgreService service;

    @PostMapping("/save")
    public ResponseEntity<PostgreEntity> save(PostgreEntity entity)
    {
        return new ResponseEntity<>(service.saveEntity(entity) ,HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PostgreEntity>> getAll()
    {
        return new ResponseEntity<>(service.getAll(),HttpStatus.OK);
    }
}
