package com.humy.executor.io;

import com.humy.executor.config.ConfigUtil;
import com.humy.executor.exception.ResultStatusEnum;
import com.humy.executor.exception.UserFriendlyException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * IO流练习
 * 几个常用功能：读取文件，写入文件，复制文件，上传下载，
 *
 * 字符流：reader/writer
 * 字节流：inputStream/outputStream
 *
 * @author Humy
 * @date 2020/9/13 18:55
 */

@Controller
@RequestMapping("/ioStream")
public class IOStreamController {

    private static Logger logger = LoggerFactory.getLogger(IOStreamController.class);


    /**
     * 复制
     *
     */
    @RequestMapping("/copyFile")
    public void copyFile(){

        String originFilePath = "F:\\GitHub\\Test" + File.separator + "temp.xlsx";
        String desFilePath = "F:\\GitHub\\Test" + File.separator + "temp.txt";
        File originFile = new File(originFilePath);
        File desFile = new File(desFilePath);

        if (!originFile.exists()) {
            System.out.println("复制的文件不存在");
            System.exit(1);
        }

        try {
            FileInputStream fis = new FileInputStream(originFile);
            FileOutputStream fos = new FileOutputStream(desFile);
            byte[] len = new byte[1024];
            int temp;
            while ((temp = fis.read(len)) != -1) {
                fos.write(len, 0, temp);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件上传
     *
     * @param file file
     */
    @RequestMapping("/uploadFile")
    public void uploadFile(MultipartFile file) {
        if (file == null) {
            logger.error("上传文件为空");
            throw new UserFriendlyException(ResultStatusEnum.FILE_NULL);
        }
        String originalFilename = file.getOriginalFilename();
        logger.info("文件开始上传：" + originalFilename);
        String filePath = getUploadFilePath();
        String absolutePath = filePath + File.separator + System.currentTimeMillis() + originalFilename;

        File newFile = new File(absolutePath);
        try {
            file.transferTo(newFile);
            logger.info("文件上传完成：" + originalFilename);
        } catch (IOException e) {
            logger.error("文件上传失败：" + originalFilename);
            e.printStackTrace();
        }
    }


    /**
     *文件下载
     *
     * @param filePath filePath
     * @param response response
     * @return HttpServletResponse
     */
    @RequestMapping("/downloadFile")
    public void downloadFile(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        String fileName = file.getName();
        if (!file.exists()) {
            throw  new UserFriendlyException(ResultStatusEnum.FILE_NOT_EXIST);
        }
        try {
//            方法一：
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//
//            //清空response
//            response.reset();
//            //设置response的header
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
//            response.addHeader("Content-Length", "" + file.length());
//            response.setContentType("application/octet-stream");
//
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            outputStream.write(buffer);
//            outputStream.flush();
//            outputStream.close();

//            方法二：
            FileInputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");

            byte[] buffer = new byte[1024] ;
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getUploadFilePath() {
        // windows系统，路径设置为项目根目录
        return isWindows()
                ? System.getProperty("user.dir")
                : Objects.requireNonNull(ConfigUtil.getConfigValue("uploadFilePathLinux")).toString();
    }

    private Boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

}
