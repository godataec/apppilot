package com.bancointernacional.plataformaBI.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;




@Entity
@Table(name="users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 30)
    @Column(name = "lastname")
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = "workposition")
    private String workPosition;

    

    @Column(name = "active")
    private Boolean active;



    public User() {
    }

    public User(Long id, @NotBlank @Size(max = 30) String name, @NotBlank @Size(max = 30) String lastName,
            @NotBlank @Size(max = 50) String email, @NotBlank @Size(max = 50) String workPosition, Boolean active) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.workPosition = workPosition;
        this.active = active;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    
    

}
