package com.e_learning.Sikshyalaya.service;

import com.e_learning.Sikshyalaya.entities.Resource;
import com.e_learning.Sikshyalaya.entities.Section;
import com.e_learning.Sikshyalaya.repositories.CourseRepository;
import com.e_learning.Sikshyalaya.repositories.ResourceRepository;
import com.e_learning.Sikshyalaya.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;


    private final ResourceRepository resourceRepository;

    public SectionService(SectionRepository sectionRepository, ResourceRepository resourceRepository) {
        this.sectionRepository = sectionRepository;
        this.resourceRepository = resourceRepository;
    }

    public void add(Section section) {
        sectionRepository.save(section);
    }

    public void delete(Section section) {
        sectionRepository.delete(section);
    }

    public void deleteById(Integer id) {
        sectionRepository.deleteById(id);
    }

    public void update(Section section) {
        sectionRepository.save(section);
    }

    public void addResource(Integer sectionId, Resource resource) {
        Resource oldResource = resourceRepository.findById(resource.getResourceId()).orElse(null);
        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (oldResource == null && section != null) {
//           section.addResource(resource);
        }
    }

    public Optional<Section> findById(Integer id) {
        return sectionRepository.findById(id);
    }

    public void addLecture() {

    }

    public List<Section> findSectionsByCourse(int course) {
        List<Section> sections = sectionRepository.findAll();
        return sections.stream().filter(section -> section.getCourse().getCourseID() == course).toList();
    }

}
