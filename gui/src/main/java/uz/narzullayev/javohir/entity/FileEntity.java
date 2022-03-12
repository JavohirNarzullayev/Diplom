package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/21/2022
  Time: 10:07 AM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.narzullayev.javohir.constant.FileType;
import uz.narzullayev.javohir.entity.extra.Additional;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ToString
@Entity
@Table(name = "file_entity")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileEntity extends Additional {
    private static final long serialVersionUID = 1L;


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
