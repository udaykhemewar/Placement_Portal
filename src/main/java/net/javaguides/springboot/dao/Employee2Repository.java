package net.javaguides.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employees2;

@Repository
public interface Employee2Repository extends JpaRepository<Employees2, Long> {

}