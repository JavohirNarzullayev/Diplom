package uz.narzullayev.javohir.entity;/* 
 @author: Javohir
  Date: 1/30/2022
  Time: 11:54 AM*/

import org.hibernate.annotations.GenericGenerator;
import uz.narzullayev.javohir.entity.info.ExtraInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Literature extends ExtraInfo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "literature_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
}
