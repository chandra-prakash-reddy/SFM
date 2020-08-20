package com.github.controller;

import com.github.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping(value = "/getLogFileContent")
    ResponseEntity<String> saveFileContent(@RequestParam("path") String filePath,@RequestParam("lines") int lines) throws IOException {
        return ResponseEntity.ok().body(logService.getLastNLines(filePath,lines));
    }
}
