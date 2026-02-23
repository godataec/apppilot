package com.bancointernacional.plataformaBI.models.entities;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import java.util.UUID;

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
@Where(clause = "ISDELETED = false")
public class User {

    @Id
    @Column(name = "IDUSER", nullable = false, columnDefinition = "uniqueidentifier", updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID idUser;

    @NotBlank
    @Size(max = 50)
    @Column(name = "openiduser")
    private String openIdUser;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 50)
    @Column(name = "lastname")
    private String lastName;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @Column(name="email")
    private String email;

    @Column(name = "ISDELETED")
    private boolean isDeleted;

    public User() {
    }

    public User(UUID idUser, @NotBlank @Size(max = 30) String openIdUser ,@NotBlank @Size(max = 30) String name,
                @NotBlank @Size(max = 30) String lastName, String status, String role, String email) {
        this.idUser = idUser;
        this.openIdUser = openIdUser;
        this.name = name;
        this.lastName = lastName;
        this.status = status;
        this.role = role;
        this.email = email;
    }


    public UUID getId() {
        return idUser;
    }

    public void setId(UUID idUser) {
        this.idUser = idUser;
    }

    public String getOpenIdUser() {
        return openIdUser;
    }

    public void setOpenIdUser(String openIdUser) {
        this.openIdUser = openIdUser;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
