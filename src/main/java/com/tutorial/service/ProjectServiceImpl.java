package com.tutorial.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tutorial.model.Project;
import com.tutorial.repository.ProjectRepository;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public List<Project> getAll() {
		return projectRepository.findAll();
	}

	@Override
	public List<Project> getAll(String text) {
		List<String> attributes = Arrays.asList("projectId", "projectName", "dateOfStart", "teamSize");        
        if (!text.contains("%")) {
            text = "%" + text + "%";
        }
        String finalText = text;
        Specification<Project> spec = (root, query, builder) -> 
        		builder.or(root.getModel().getDeclaredSingularAttributes().stream()
        				.filter(a -> attributes.contains(a.getName()))
        				.map(a -> {
        					if(a.getJavaType().getSimpleName().equalsIgnoreCase("Long")) {
        						return builder.like(root.get(a.getName()).as(String.class), finalText);
        					} else if(a.getJavaType().getSimpleName().equalsIgnoreCase("int")) {
        						return builder.like(root.get(a.getName()).as(String.class), finalText);
        					}
        					return builder.like(root.get(a.getName()), finalText);
        				})
        				.toArray(Predicate[]::new)
        );       
        return projectRepository.findAll(Specification.where(spec));
	}
	
	@Override
	public Project insertProject(Project project) {
		project = projectRepository.save(project);
		return project;
	}

	@Override
	public Project updateProject(Project project) {
		Optional<Project> projectDb = projectRepository.findById(project.getProjectId());
		if(projectDb.isPresent()) {
			Project existingProject = projectDb.get();
			existingProject.setProjectId(project.getProjectId());
			existingProject.setProjectName(project.getProjectName());
			existingProject.setDateOfStart(project.getDateOfStart());
			existingProject.setTeamSize(project.getTeamSize());
			return projectRepository.save(existingProject);
		}
		return null;
	}

	@Override
	public long deleteProject(Long id) {
		Optional<Project> projectDb = projectRepository.findById(id);
		if(projectDb.isPresent()) {
			projectRepository.delete(projectDb.get());
			return id;
		}
		return -1;
	}
}
