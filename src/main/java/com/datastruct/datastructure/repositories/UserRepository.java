package com.datastruct.datastructure.repositories;

import com.datastruct.datastructure.domain.User;
import com.datastruct.datastructure.exceptions.EtAuthException;

public interface UserRepository {

    //takes all params as args and returns generated uid
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email, String password) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
