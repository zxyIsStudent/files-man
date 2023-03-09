package com.files.filesman;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class FilesInform {
    @TableId
    private Long fileId;
    private String filePath;
    private String fileName;
    private String fileMd5;
    private String cteTime;

    public String getImgUrl() {

        return "http://192.168.7.181:8081/file/" + fileName;
    }
}
