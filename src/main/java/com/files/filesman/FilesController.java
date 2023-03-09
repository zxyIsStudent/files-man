package com.files.filesman;

import ch.qos.logback.core.util.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.files.filesman.mapper.FilesInformMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 *  Why do you have a Band-Aid on your face. Band-Aid. It's not a
 * Band-Aid it's an acne patch. I have a big pimple here and I'm hoping
 * the patch will make it go away fat. Bad acne.
 */
@Slf4j
@RestController
@RequestMapping("files")
public class FilesController {

    public static String path = "/Users/zhaoxiangyuan/Documents/img/";

    @Resource
    private FilesInformMapper informMapper;


    @GetMapping("test")
    public String test() {

        log.info("aaaa");
        return "hello world";
    }

    @PostMapping("upload")
    public void saveFile(@RequestParam("file")MultipartFile file) throws Exception {
        String md5 = getMd5(file);
        String name = file.getOriginalFilename();
        log.info("md5=====>:{}",md5);
        int count = informMapper.selectCount(new QueryWrapper<FilesInform>().lambda().eq(FilesInform::getFileMd5, md5));
        if (count > 0) {
            log.info("count=====>:{}",count);
            return;
        }
        FilesInform inform = new FilesInform();
        inform.setFileMd5(md5);
        inform.setFileName(name);
        inform.setFilePath(path + name);
        informMapper.insert(inform);


        File f = new File(path + file.getOriginalFilename());
        file.transferTo(f);
    }
    @PostMapping("uploads")
    @Async
    public void saveFiles(@RequestParam("file")MultipartFile[] files) throws Exception {
        Arrays.stream(files)
                .parallel()
                .forEach(file->{
                    String md5 = getMd5(file);
                    String name = file.getOriginalFilename();
                    log.info("md5=====>:{}",md5);
                    int count = informMapper.selectCount(new QueryWrapper<FilesInform>().lambda().eq(FilesInform::getFileMd5, md5));
                    if (count > 0) {
                        log.info("count=====>:{}",count);
                        return;
                    }
                    FilesInform inform = new FilesInform();
                    inform.setFileMd5(md5);
                    inform.setFileName(name);
                    inform.setFilePath(path + name);
                    informMapper.insert(inform);


                    File f = new File(path + file.getOriginalFilename());
                    try {
                        file.transferTo(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }
    /**
     * 获取上传文件的md5
     * @param file
     * @return
     * @throws
     */
    public String getMd5(MultipartFile file) {
        try {
            //获取文件的byte信息
            byte[] uploadBytes = file.getBytes();
            // 拿到一个MD5转换器
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            //转换为16进制
            return new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

//    @GetMapping(value = "/down")
//    public void downloadFileStream(HttpServletResponse response) throws UnsupportedEncodingException {
//        String name = "公共人员信息模板.xlsx";
//        String fileUrl = bootdoConfig.getUploadPath() + name;
//        File file = FileUtil.file(fileUrl);
//        if (StringUtils.isEmpty(fileUrl) || file == null || !file.exists()) {
//            System.out.println("文件不存在");
//        }
//        ServletOutputStream outputStream = null;
//        response.setContentType("application/force-download");// 设置强制下载不打开
//        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
//        try {
//            outputStream = response.getOutputStream();
//            outputStream.write(FileUtil.readBytes(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        IoUtil.close(outputStream);
//
//    }

    @GetMapping("/page")
    public IPage<FilesInform> getPage(@RequestParam( defaultValue = "1")int pageNum, @RequestParam( defaultValue = "10")int pageSize, Object query) {
        Page<FilesInform> page = new Page<>(pageNum,pageSize);
        page.addOrder(OrderItem.desc("file_id"));
        return informMapper.selectPage(page,new QueryWrapper<>());
    }
}
