package com.bancointernacional.plataformaBI.models.DTO;

import com.bancointernacional.plataformaBI.models.entities.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String openIdUser;
    private String name;
    private String lastName;
    private String status;
    private String role;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String openIdUser, String name, String lastName, String status, String role, String email) {
        this.openIdUser = openIdUser;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDTO build(User user) {
        if (user == null) {
            throw new RuntimeException("Debe pasar el entity user!");
        }
        return new UserDTO(user.getOpenIdUser(),
                user.getName(),
                user.getLastName(),
                user.getStatus(),
                user.getRole(),
                user.getEmail());

    }

}
