package com.server.server.global.s3;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeamService {
    private final S3Uploader s3Uploader;

    public TeamService(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }


    @Transactional
    public void createTeam(String name, MultipartFile file) {
        String url = "";
        if(file != null)  url = s3Uploader.uploadFileToS3(file, "static/team-image");

    }

}