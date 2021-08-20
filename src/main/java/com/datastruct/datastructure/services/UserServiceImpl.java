package com.datastruct.datastructure.services;

import com.datastruct.datastructure.domain.User;
import com.datastruct.datastructure.exceptions.EtAuthException;
import com.datastruct.datastructure.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.regex.Pattern;

//Service needed so spring can detect the type of the class and
// use it in the application context. difference between these is
// that they are used for different classifications. Although they
// are all components, we must use the annotation for that tier.

//transactional provides transactional behaviour for all methods in class.
// When method is invoked, new transaction is created and commit happens to
// db only when commit was made successfully. this is why we throw error.
@Service
@Transactional
public class UserServiceImpl implements UserService{

    //we are using an interface not the class, which decouples accross tiers
    // which is what we want.
    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid email format");
        }
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0){
            throw new EtAuthException("Email in use");
        }
        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}
