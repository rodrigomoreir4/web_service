package com.rodrigomoreira.myapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigomoreira.myapp.domain.courses.Course;
import com.rodrigomoreira.myapp.domain.users.User;
import com.rodrigomoreira.myapp.repositories.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;

    public Course findCourseById(Long id) throws Exception {
        return courseRepository.findCourseById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public Course findCourseByName(String name) throws Exception{
        return courseRepository.findCourseByName(name).orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }
    
    public Course createCourse(Course course) throws Exception {
        course.setName(course.getName().toLowerCase());
        if(course.getName().trim().isEmpty()){
            throw new Exception("The course must have a name");
        }
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() throws Exception{
        List<Course> courses = courseRepository.findAll();
        if(courses.isEmpty()){
            throw new Exception("No courses found");
        }
        return courses;
    }

    public void deleteCourseById(Long id) throws Exception{
        Course course = findCourseById(id);
        for (User user : course.getUsers()){
            user.getCourses().remove(course);
        }
        courseRepository.deleteById(course.getId());
    }  
}
