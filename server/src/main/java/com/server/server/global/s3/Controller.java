package com.server.server.global.s3;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/team-service")
public class Controller {
    private TeamService teamService;

    @PostMapping(path = "/teams", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createTeam(
            @RequestPart(value = "name") String name,
            @RequestPart(value = "file", required = false) MultipartFile file
    ){

        teamService.createTeam(name, file);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
