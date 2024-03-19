package com.api.APICall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apicall")
public class APICallController {

    @Autowired
    private APICallClient client;

    @Autowired
    private DataRepository repository;

    @GetMapping("/getdata")
    public String getData() {
        Data data = new Data();
        data.getContent();
        return data.getContent();
    }

    @PostMapping("senddata")
    public ResponseEntity<String> sendData(@RequestBody String content) {
        Data data = new Data();
        try {
            client.sendData(content);
            return ResponseEntity.ok("Data sent successfully to third-party API");
        } catch (Exception e) {
            // If the third-party API is down, save data to the database
            data.setContent(content);
            repository.save(data);
            return ResponseEntity.ok("Third-party API is down, data saved to database");
        }
    }
}
