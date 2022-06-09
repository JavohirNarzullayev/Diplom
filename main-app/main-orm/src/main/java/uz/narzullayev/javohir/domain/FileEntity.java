package uz.narzullayev.javohir.domain;/*
 @author: Javohir
  Date: 1/21/2022
  Time: 10:07 AM*/

import lombok.Data;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Entity
@Table(name = "file_entity")
@Data
public class FileEntity extends BaseAuditingEntity implements Serializable {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String extension;
    @Column(nullable = false)
    private String path;
    private UUID uuid = UUID.randomUUID();
    @Column
    private Long size;
    @Column
    private String title;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;


    @PreRemove
    public void preRemove() {
        Path path = Paths.get(this.path);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostLoad
    public void postLoad(){
        System.out.println("Post load working....");
    }
}
