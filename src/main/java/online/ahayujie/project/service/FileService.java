package online.ahayujie.project.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件 Service
 * @author aha
 * @since 2020/11/19
 */
public interface FileService {
    /**
     * 上传文件
     * @param file 文件
     * @return 文件的url
     * @throws IOException 上传文件失败
     */
    String upload(MultipartFile file) throws IOException;
}
