package com.akash.instructorapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.akash.instructorapp.model.Course;

@Service
public class CourseService {
	private static List<Course> courses = new ArrayList<>();
	private static long idCounter = 0;
	private static final String USERNAME = "akash";

	static {
		courses.add(new Course(++idCounter, USERNAME, "Learn Full stack with Spring Boot and Angular"));
		courses.add(new Course(++idCounter, "vikash", "Learn Full stack with Spring Boot and React"));
		courses.add(new Course(++idCounter, USERNAME, "Master Microservices with Spring Boot and Spring Cloud"));
		courses.add(new Course(++idCounter, USERNAME,
				"Deploy Spring Boot Microservices to Cloud with Docker and Kubernetes"));
	}

	public List<Course> findAll(String username) {
		return courses.stream().filter(c-> c.getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
	}

	public static Course save(Course course) {
		if (course.getId() == -1 || course.getId() == 0) {
			course.setId(++idCounter);
			courses.add(course);
		} else {
			deleteById(course.getId());
			courses.add(course);
		}
		return course;
	}

	public static Course deleteById(long id) {
		Course course = findById(id);

		if (course == null)
			return null;

		if (courses.remove(course)) {
			return course;
		}

		return null;
	}

	public static Course findById(long id) {
		for (Course course : courses) {
			if (course.getId() == id) {
				return course;
			}
		}

		return null;
	}
}
