package com.tutorial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.model.Project;
import com.tutorial.service.ProjectService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@GetMapping
	public ResponseEntity<List<Project>> getAllProjects() {
		return ResponseEntity.ok().body(projectService.getAll());
	}

	@GetMapping(params = "search")
	public ResponseEntity<List<Project>> getAllProjects(@RequestParam(value = "search", required = true) String search) {
		return ResponseEntity.ok().body(projectService.getAll(search));
	}
	
	@PostMapping
	public ResponseEntity<Project> insertProject(@RequestBody Project project) {
		return ResponseEntity.ok().body(projectService.insertProject(project));
	}

	@PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        return ResponseEntity.ok().body(projectService.updateProject(project));
    }
	
	@DeleteMapping
	public ResponseEntity<Long> deleteProduct(@RequestParam(name="projectId", value="projectId") long id) {
        return ResponseEntity.ok().body(projectService.deleteProject(id));
    }
}
