package com.mrtech.adminportal.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrtech.adminportal.entity.Payment;
import com.mrtech.adminportal.entity.Student;
import com.mrtech.adminportal.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
    }
    
    @GetMapping("/search")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam("keyword") String keyword) {
        List<Student> result = studentRepository.findByNameContainingIgnoreCaseOrMobileContaining(keyword, keyword);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getstudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedData) {
        return studentRepository.findById(id).map(existing -> {
            existing.setName(updatedData.getName());
            existing.setEmail(updatedData.getEmail());
            existing.setMobile(updatedData.getMobile());
            existing.setDob(updatedData.getDob());
            existing.setGender(updatedData.getGender());
            existing.setAddress(updatedData.getAddress());
            existing.setQualification(updatedData.getQualification());
            existing.setCourse(updatedData.getCourse());
            existing.setDuration(updatedData.getDuration());
            existing.setJoiningDate(updatedData.getJoiningDate());
            existing.setBatch(updatedData.getBatch());
            existing.setCoursefee(updatedData.getCoursefee());     
            existing.setDiscount(updatedData.getDiscount());       
            existing.setTotalfee(updatedData.getTotalfee());       
            existing.setTerm_1(updatedData.getTerm_1());           
            existing.setDuefee(updatedData.getDuefee());
            studentRepository.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudents(
            @RequestParam String course,
            @RequestParam String status,
            @RequestParam String batch) {

    	List<Student> students = studentRepository.findByCourseAndStatusAndBatch(course, status, batch);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/student")
    public String getAllStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "your-html-template-name"; // e.g., "dashboard"
    }






}

