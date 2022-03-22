package uz.narzullayev.javohir.domain.audit;/* 
 @author: Javohir
  Date: 3/22/2022
  Time: 1:28 AM*/

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    protected Long id;
}
