package com.akash.instructorapp.rest.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.akash.instructorapp.model.Course;
import com.akash.instructorapp.service.CourseService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class CourseResource {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/instructors/{username}/courses")
	public List<Course> getAllCourses(@PathVariable String username){
		return courseService.findAll(username);
	}
	
	  @DeleteMapping("/instructors/{username}/courses/{id}")
	  public ResponseEntity<Void> deleteCourse(@PathVariable String username, @PathVariable long id) {

	    Course course = courseService.deleteById(id);

	    if (course != null) {
	      return ResponseEntity.noContent().build();
	    }

	    return ResponseEntity.notFound().build();
	  }
	  
	  @GetMapping("/instructors/{username}/courses/{id}")
	  public Course getCourse(@PathVariable String username, @PathVariable long id) {
	    return courseService.findById(id);
	  }
	  
	  @PutMapping("/instructors/{username}/courses/{id}")
	  public ResponseEntity<Course> updateCourse(@PathVariable String username, @PathVariable long id,
	      @RequestBody Course course) {
		course.setUsername(username);
	    Course courseUpdated = courseService.save(course);

	    return new ResponseEntity<>(courseUpdated, HttpStatus.OK);
	  }
	  
	  @PostMapping("/instructors/{username}/courses")
	  public ResponseEntity<Void> createCourse(@PathVariable String username, @RequestBody Course course) {

		course.setUsername(username);
	    Course createdCourse = courseService.save(course);

	    // Location
	    // Get current resource url
	    /// {id}
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
	        .toUri();

	    return ResponseEntity.created(uri).build();
	  }

}
