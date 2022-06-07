package uz.narzullayev.javohir.domain;/* 
 @author: Javohir
  Date: 6/4/2022
  Time: 12:32 AM*/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.*;
import uz.narzullayev.javohir.constant.QuizChoice;
import uz.narzullayev.javohir.domain.audit.BaseAuditingEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "quiz")
@Data
@Where(clause = "deleted = false")
@SQLDelete(sql = "update quiz set deleted=true where id=? and version=?")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quiz extends BaseAuditingEntity implements Serializable {

    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @Type(type = "jsonb")
    @Column(name = "choices", columnDefinition = "jsonb")
    private List<QuizChoice> choices;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "science_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Science science;


}
