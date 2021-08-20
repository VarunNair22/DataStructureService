package com.datastruct.datastructure.domain;

public class User {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(Integer userId, String firstName, String lastName, String email, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //services and repositories: services are central part of app and most business logic goes there.
    // names of services should resemble business use cases. repositories are closer to db, each method of repo
    // performs db op (create, delete, select, etc.) repose are used  by service method to create large business
    // transaction
}
