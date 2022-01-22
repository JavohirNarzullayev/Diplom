package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/21/2022
  Time: 10:07 AM*/

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@Table(name = "file_entity")
@Getter
@Setter
public class FileEntity  extends ExtraInfo implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "file_entity_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String extension;
    @Column(nullable = false)
    private String path;
    @Column
    private Integer size;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
}
