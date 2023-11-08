package net.javaguides.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Employees2")
public class Employees2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name")
    private String firstName;

    @Column(name = "Age")
    private int age;

    public Employees2() {
    }

    public Employees2(long id, String firstName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
