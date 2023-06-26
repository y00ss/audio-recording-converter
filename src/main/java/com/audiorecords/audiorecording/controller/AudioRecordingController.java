package com.audiorecords.audiorecording.controller;

/*
 * @created 09/06/2023
 * @project IUSPRIVACY-AudioRecording
 * @package com.audiorecords.audiorecording.controller
 * @author Youssef
 * @contact youssef.dev@pm.me
 */

import com.audiorecords.audiorecording.service.AudioRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AudioRecordingController {

    @Autowired
    AudioRecordService audioRecordService;

    @PostMapping(value = "/api/upload-audio")
    public ResponseEntity<byte[]> uploadAudio(@RequestParam("blob") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            try {
                File tempFile = File.createTempFile("temp", ".wav");
                Files.copy(file.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                File convertedFile = audioRecordService.convertWavToMp4(tempFile);

                byte[] fileContent = Files.readAllBytes(convertedFile.toPath());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", file.getName());

                return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

            } catch (IOException e) {
                return ResponseEntity.status(500).body("No file".getBytes());
            }
        } else {
            return ResponseEntity.status(404).body("File".getBytes());
        }
    }
}
