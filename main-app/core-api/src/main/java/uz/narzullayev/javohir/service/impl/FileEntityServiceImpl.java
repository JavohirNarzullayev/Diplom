package uz.narzullayev.javohir.service.impl;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import uz.narzullayev.javohir.AppProperties;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.domain.FileEntity;
import uz.narzullayev.javohir.domain.UserEntity;
import uz.narzullayev.javohir.exception.RecordNotFoundException;
import uz.narzullayev.javohir.repository.FileEntityRepository;
import uz.narzullayev.javohir.service.FileEntityService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

import static uz.narzullayev.javohir.service.impl.FileEntityServiceImpl.FileContentType.findByName;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileEntityServiceImpl implements FileEntityService {
    private final AppProperties appProperties;
    private final FileEntityRepository fileEntityRepository;
    private final DateTimeFormatter uploadFolder = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter creatFile = DateTimeFormatter.ofPattern("HHmmss");

    @AllArgsConstructor
    @Getter
    public enum FileContentType {
        PDF("application/pdf"),
        ZIP("application/zip"),
        RAR("application/x-rar-compressed"),
        JPG("image/jpeg"),
        JPEG("image/jpeg"),
        PNG("image/png"),
        GIF("image/gif"),
        //Document WORD,EXCEL,POWERPOINT
        DOC("application/msword"),
        DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
        XLS("application/vnd.ms-excel"),
        XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        PPT("application/vnd.ms-powerpoint"),
        PPTX("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
        PPTM("application/vnd.ms-powerpoint.presentation.macroEnabled.12"),

        MP4("video/mp4"),
        OTHER("application/octet-stream");

        private String mediaType;

        public static FileContentType findByName(String extension) {
            return Arrays.stream(values())
                    .filter(fileContentType -> fileContentType.name().equals(extension.toUpperCase()))
                    .findAny().orElse(FileContentType.OTHER);

        }

        public MultiValueMap<String, String> mergeMediaType(MultiValueMap<String, String> headers) {
            headers.add(HttpHeaders.CONTENT_TYPE, this.mediaType);
            return headers;
        }
    }

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

    @SneakyThrows
    private Resource getFileAsResource(FileEntity file) {
        try {
            Path filePath = Paths.get(file.getPath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) return resource;
            else log.error("could not read file: " + file.getPath());
        } catch (Exception e) {
            log.error("FilesServiceImpl.getFileAsResource", e.getMessage());
            e.printStackTrace();
        }
        throw new IllegalAccessException("Could not create path");
    }

    public ResponseEntity<Resource> getFileAsResourceForDownloading(FileEntity file) {
        var fileAsResource = getFileAsResource(file);
        if (fileAsResource == null) throw new RecordNotFoundException("File not found", "File", "NOT_FOUND");
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

        if (file.getSize() != null) {
            headers.add(HttpHeaders.CONTENT_LENGTH, file.getSize().toString());
        }
        if (file.getExtension() != null) {
            headers = findByName(file.getExtension()).mergeMediaType(headers);
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
        String pathFile = appProperties.getFileStorage().getUploadFolder();
        String newCurrentDir = pathFile + "/" + uploadFolder.format(LocalDate.now());
        File root = new File(newCurrentDir);
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
       fileEntityRepository.deleteById(fileId);
    }


}
