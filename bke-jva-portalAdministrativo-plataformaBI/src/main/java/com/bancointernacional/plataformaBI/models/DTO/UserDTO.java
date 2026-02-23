package com.bancointernacional.plataformaBI.models.DTO;

import com.bancointernacional.plataformaBI.models.entities.User;

public class UserDTO {

    private String name;
    private String lastName;
    private String email;
    private String workPosition;
    private Boolean active;
    
    

    public UserDTO() {
    }


    public UserDTO(String name, String lastName, String email, String workPosition, Boolean active) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.workPosition = workPosition;
        this.active = active;
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


    public String getWorkPosition() {
        return workPosition;
    }


    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public Boolean getActive() {
        return active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }

    
    public static UserDTO build(User user){

        if( user == null ){
            throw new RuntimeException("Debe pasar el entity user!");
        }

        return new UserDTO(user.getName(),
                           user.getLastName(),
                           user.getEmail(),
                           user.getWorkPosition(),
                           user.getActive());

    }





   


    
}
