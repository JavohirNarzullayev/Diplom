package uz.narzullayev.javohir.service.impl;/* 
 @author: Javohir
  Date: 1/22/2022
  Time: 1:22 PM*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.narzullayev.javohir.repository.FileEntityRepository;
import uz.narzullayev.javohir.service.FileEntityService;

@Service
public class FileEntityServiceImpl implements FileEntityService {
    @Autowired
    private FileEntityRepository fileEntityRepository;

}
