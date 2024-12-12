package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Resource;
import com.e_learning.Sikshyalaya.repositories.ResourceRepository;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    ResourceRepository resourceRepository;

    public void addResource(Resource resource){
        resourceRepository.save(resource);
    }

    
}
