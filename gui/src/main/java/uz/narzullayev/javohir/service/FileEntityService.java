package uz.narzullayev.javohir.service;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:20 PM*/

import javassist.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.entity.FileEntity;
import uz.narzullayev.javohir.entity.UserEntity;


public interface FileEntityService {

    FileEntity findById(Long fileId);

    ResponseEntity<Resource> getFileAsResourceForDownloading(FileEntity file);

    FileEntity uploadFile(MultipartFile multipartFile, Long userId, String title, FileType fileType);

    FileEntity findByIdAndUploadUserId(Long id, UserEntity user);

    FileEntity save(FileEntity file);

    void remove(Long fileId) throws NotFoundException;
}
