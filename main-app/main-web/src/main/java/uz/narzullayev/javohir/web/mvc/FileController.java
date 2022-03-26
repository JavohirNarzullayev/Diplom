package uz.narzullayev.javohir.web.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.narzullayev.javohir.service.FileEntityService;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileEntityService fileEntityService;


    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam("id") String uuid) {
        var file = fileEntityService.findByUUID(UUID.fromString(uuid));
        return fileEntityService.getFileAsResourceForDownloading(file);
    }
}
