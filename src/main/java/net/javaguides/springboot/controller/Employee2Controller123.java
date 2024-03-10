package net.javaguides.springboot.controller;

import net.javaguides.springboot.dao.Employee2Repository;
import net.javaguides.springboot.model.Employees2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class Employee2Controller123 {

    private final Employee2Repository employee2Repository;

    @Autowired
    public Employee2Controller123(Employee2Repository employee2Repository) {
        this.employee2Repository = employee2Repository;
    }

    @GetMapping("/employee2")
    public List<Employees2> findAllEmployees() {
        return employee2Repository.findAll();
    }

    @GetMapping("employee2/{id}")
    public ResponseEntity<Employees2> findEmployeeById(@PathVariable Long id) {
        Optional<Employees2> employee = employee2Repository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
