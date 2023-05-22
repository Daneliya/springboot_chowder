package com.xxl.minio.test;

import com.google.api.client.util.IOUtils;
import com.xxl.minio.result.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Description:
 * @Author: xxl
 * @Date: 2023/05/21 18:37
 * @Version: 1.0
 */
@RestController
public class MultipartFileTest {

    private static final String FILE_PATH = "D://home//fileUpload//";

    /**
     * MultipartFile 接收文件流并保存至本地
     *
     * @param myFile
     * @return
     */
    @GetMapping("/uploadFile")
    public ApiResult uploadFile(MultipartFile myFile) {
        try {
            uploadByMultipartFile(myFile);
            return ApiResult.success("SUCCESS");
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.error("ERROR");
        }
    }

    /**
     * MultipartFile 接收文件流并保存至本地指定目录
     *
     * @param myFile
     * @return
     */
    @GetMapping("/uploadFile2")
    public ApiResult uploadFile2(MultipartFile myFile) {
        uploadByMultipartFile(FILE_PATH, myFile);
        return ApiResult.success("SUCCESS");
    }


    /**
     * 文件上传（不依赖spring）
     *
     * @param path      文件绝对路径
     * @param fileBytes 文件字节数据
     */
//    public void uploadByByte(String path, byte[] fileBytes) {
//
//        //保存文件到对应位置
//        File dir = new File(path);
//        if (!dir.getParentFile().exists()) {
//            boolean mkdir = dir.getParentFile().mkdirs();
//            if (!mkdir) {
//                // 文件父路径创建失败，父路径['{0}']
//                throw IctsServiceError.FILE_SAVE_ERROR.toException(i18n(i18nPreFix + "file.parent.path.create.failure", dir.getParentFile().getAbsolutePath()));
//            }
//        }
//        try {
//            FileUtils.writeByteArrayToFile(dir, fileBytes);
//        } catch (IOException e) {
//            //抛出异常
//        }
//    }

    /**
     * 文件上传，接收文件流并保存至本地（依赖spring）
     *
     * @param myFile 文件数据
     */
    private void uploadByMultipartFile(MultipartFile myFile) throws IOException {
        //1.获取源文件的输入流
        InputStream is = myFile.getInputStream();
        //2.获取源文件类型，文件后缀名
        String originalFileName = myFile.getOriginalFilename();
        //3.定义上传后的目标文件名(为了避免文件名称重复，此时使用UUID)
        String newFileName = UUID.randomUUID().toString() + "." + originalFileName;
        //4.通过上传路径得到上传的文件夹
        File file = new File("imageFile");
        //4.1.若目标文件夹不存在，则创建
        if (!file.exists()) { //判断目标文件夹是否存在
            file.mkdirs();//4.2.不存在，则创建文件夹
        }
        //5.根据目标文件夹和目标文件名新建目标文件（上传后的文件）
        File newFile = new File("imageFile", newFileName);  //空的目标文件
        //6.根据目标文件的新建其输出流对象
        FileOutputStream os = new FileOutputStream(newFile);
        //7.完成输入流到输出流的复制
        IOUtils.copy(is, os);
        //8.关闭流(先开后关)
        os.close();
        is.close();
    }


    /**
     * 文件上传（依赖spring）
     *
     * @param path 文件绝对路径
     * @param file 文件数据
     */
    private void uploadByMultipartFile(String path, MultipartFile file) {
        // 文件名
        //String fileName = file.getName();
        // 后缀名
        String originalFileName = file.getOriginalFilename();
        // 保存文件到对应位置
        File dir = new File(path + "/" + originalFileName);
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        try {
            // 在填写文件路径时，一定要是具体的文件名称（xx.txt），否则会出现拒绝访问
            file.transferTo(dir);
        } catch (IOException e) {
            //抛出异常
            e.printStackTrace();
        }
    }

    /**
     * 大文件上传（不依赖spring）
     *
     * @param path        文件绝对路径
     * @param inputStream 文件流
     */
    public void uploadByInputStream(String path, InputStream inputStream) {
        //保存文件到对应位置
        File dir = new File(path);
        boolean fileExists = dir.getParentFile().exists();
        if (!fileExists) {
            boolean mkdir = dir.getParentFile().mkdirs();
            if (!mkdir) {
                // 文件路径创建失败，
            }
        }
        try (FileOutputStream fos = new FileOutputStream(dir)) {
            byte[] b = new byte[1024];
            while ((inputStream.read(b)) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            //文件流转读取失败
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //流关闭失败
                }
            }
        }
    }

}
