package com.datastruct.datastructure.services;

import com.datastruct.datastructure.domain.User;
import com.datastruct.datastructure.exceptions.EtAuthException;

public interface UserService {
    //good to use interface because service layer should be decoupled from data access layer

    //2 use cases: validate and create user

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}
