package com.tutorial.service;

import java.util.List;

import com.tutorial.model.Project;

public interface ProjectService {

	List<Project> getAll();
	
	List<Project> getAll(String text);
	
	Project insertProject(Project project);
	
	Project updateProject(Project project);
	
	long deleteProject(Long id);
}
