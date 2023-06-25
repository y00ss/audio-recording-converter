package com.audiorecords.audiorecording.service;/*
 * @created 13/06/2023
 * @project IUSPRIVACY-AudioRecording
 * @package com.audiorecords.audiorecording.service
 * @author Youssef
 * @contact youssef.dev@pm.me
 */

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/*
 * @created 11/06/2023
 * @project IUSPRIVACY-AudioRecording
 * @package com.audiorecords.audiorecording.controller
 * @author Youssef
 * @contact youssef.dev@pm.me
 */

@Service
public class AudioRecordService {



    public File convertWavToMp4(File inputFile) throws IOException {
        String outputFileName = (inputFile.getName()) + ".mp4";
        File outputFile = new File(inputFile.getParent(), outputFileName);

        // Comando FFmpeg per la conversione
        String[] ffmpegCommand = {
                "ffmpeg",
                "-i",
                inputFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };

        // Esegui il comando FFmpeg
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegCommand);
        try {
            Process process = processBuilder.start();
            process.waitFor();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Conversione interrotta", e);
        }
        return outputFile;
    }
}
