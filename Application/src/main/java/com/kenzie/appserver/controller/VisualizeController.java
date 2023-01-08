package com.kenzie.appserver.controller;


import com.kenzie.appserver.FileEventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/visualize")
public class VisualizeController {


    @GetMapping
    public ResponseEntity<String> visualize(){
        File file = FileEventListener.getFile();
        try {
            Runtime run = Runtime.getRuntime();
            Process proc = run.exec(file.getAbsolutePath());
            return ResponseEntity.ok("started");
        }
        catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("file not found");
        }
    }
}
