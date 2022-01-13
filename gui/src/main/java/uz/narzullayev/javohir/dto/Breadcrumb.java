package uz.narzullayev.javohir.dto;/* 
 @author: Javohir
  Date: 1/11/2022
  Time: 4:23 PM*/

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Breadcrumb {
    @Data
    @AllArgsConstructor
    public static class Link {
        private final String url;
        private final String label;

    }
    List<Link> links=new LinkedList<>();

    public void addLink(String label, String url) {
        getLinks().add(new Link(url,label));
    }

}
