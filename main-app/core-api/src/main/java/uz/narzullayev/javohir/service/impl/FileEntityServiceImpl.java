package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:22 PM*/

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.domain.FileEntity;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.repository.FileEntityRepository;
import uz.narzullayev.javohir.service.FileEntityService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileEntityServiceImpl implements FileEntityService {
    @Value(value = "${path.file}")
    private String pathFile;
    private final FileEntityRepository fileEntityRepository;
    private final DateTimeFormatter uploadFolder = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter creatFile = DateTimeFormatter.ofPattern("HHmmss");


    @Override
    @Cacheable(value = "fileFindById", key = "#id", unless = "#result == ''")
    public FileEntity findById(Long id) {
        return fileEntityRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Cacheable(value = "findByUUID", key = "#uuid", unless = "#result == ''")
    public FileEntity findByUUID(UUID uuid) {
        return fileEntityRepository.findByUuidAndDeletedFalse(uuid)
                .orElseThrow(IllegalArgumentException::new);
    }

    private Resource getFileAsResource(FileEntity file) {
        try {
            Path filePath = Paths.get(file.getPath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("could not read file: " + file.getPath());

            }
        } catch (Exception e) {
            log.error("FilesServiceImpl.getFileAsResource", e);
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<Resource> getFileAsResourceForDownloading(FileEntity file) {
        var fileAsResource = getFileAsResource(file);

        if (fileAsResource == null) return null;

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

        if (file.getSize() != null) {
            headers.add(HttpHeaders.CONTENT_LENGTH, file.getSize().toString());
        }
        if (file.getExtension() != null) {
            switch (file.getExtension().toLowerCase()) {
                case "pdf":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
                    break;
                case "zip":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");
                    break;
                case "rar":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/x-rar-compressed");
                    break;
                case "jpeg":
                case "jpg":
                    headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
                    break;
                case "png":
                    headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
                    break;
                case "gif":
                    headers.add(HttpHeaders.CONTENT_TYPE, "image/gif");
                    break;
                case "doc":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/msword");
                    break;
                case "docx":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    break;
                case "xls":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
                    break;
                case "xlsx":
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    break;
            }
        }
        return new ResponseEntity<>(fileAsResource, headers, HttpStatus.OK);
    }

    @Override
    public FileEntity uploadFile(final MultipartFile multipartFile,
                                 final Long userId,
                                 final String title,
                                 final FileType fileType) {
        Assert.notNull(multipartFile,"Multipart file not be null");
        Assert.isTrue(!multipartFile.isEmpty(),"Multipart file not be empty");
        try {
            var extension = getExtensionFromFileName(multipartFile.getOriginalFilename());
            var filename = userId + "_" + creatFile.format(LocalTime.now()) + "." + extension;
            var directory = getPathForUpload();
            var filePath = Paths.get(directory, filename);
            // save the file localy
            Files.copy(multipartFile.getInputStream(), filePath);
            log.info("File saved to: {}",filePath);

            var file = new FileEntity();
            file.setName(multipartFile.getOriginalFilename());
            file.setExtension(extension);
            file.setSize(multipartFile.getSize());
            file.setTitle(title);
            file.setPath(directory + "/" + filename);
            file.setFileType(fileType);
            return fileEntityRepository.saveAndFlush(file);
        } catch (Exception e) {
            log.error("FilesServiceImpl.uploadFile", e);
        }
        return null;
    }

    private String getExtensionFromFileName(final String fileName) {
        var extension = fileName;
        int i = extension.lastIndexOf('.');
        if (i >= 0) {
            extension = extension.substring(i + 1);
        } else {
            extension = "";
        }
        return extension.toLowerCase();
    }

    public synchronized String getPathForUpload() {
        String newCurrentDir = pathFile + "/" + uploadFolder.format(LocalDate.now());
        java.io.File root = new java.io.File(newCurrentDir);
        if (!root.exists() || !root.isDirectory()) root.mkdirs();
        return newCurrentDir;

    }

    @Override
    public FileEntity findByIdAndUploadUserId(Long id, UserEntity user) {
        return fileEntityRepository.findByIdAndRegisteredByAndDeletedFalse(id, user)
                                   .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public FileEntity save(FileEntity file) {
        return fileEntityRepository.save(file);
    }

    @Override
    public void remove(Long fileId) throws NotFoundException {
        fileEntityRepository.findById(fileId);
    }


}
