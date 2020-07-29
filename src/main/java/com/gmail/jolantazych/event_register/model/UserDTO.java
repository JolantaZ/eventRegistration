package com.gmail.jolantazych.event_register.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {


    // własne walidatory np. Email? https://www.baeldung.com/registration-with-spring-mvc-and-spring-security

    @NotEmpty(message = "Field cannot be empty!")
    // @Email(message = "Invalid email address!") // walidacja w formularzu html
    private String email;

    @NotEmpty(message = "Field cannot be empty!")
    @Size(min = 6, max = 15, message = "Password min lenght is 6 characters")
    private String password;

    @NotEmpty(message = "Field cannot be empty!")
    @Size(min = 6, max = 15, message = "Password min lenght is 6 characters")
    private String retypePassword;

    @NotEmpty(message = "Field cannot be empty!")
    private String name;

    // String? możliwość wpisywania tylo cyfr? - czasem podaje się +/()
    private String phone;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", retypePassword='" + retypePassword + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
