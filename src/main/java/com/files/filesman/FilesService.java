package com.files.filesman;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {
    public void saveFile(MultipartFile file) throws Exception {
        file.getName();
    }
}
