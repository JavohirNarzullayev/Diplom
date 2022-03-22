package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:19 PM*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntityDto implements Serializable {

    private static final long serialVersionUID = 3968859956324167751L;
    private String name;
    private String extension;
    private String path;
    private Integer size;
    private String title;
    private String description;
}
