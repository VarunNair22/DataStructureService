package com.datastruct.datastructure.repositories;

import com.datastruct.datastructure.domain.User;
import com.datastruct.datastructure.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository{

    //?'s correspond to the parameter that we pass in with the preparedstatement, where we can fill in ?'s
    private static final String SQL_CREATE = "INSERT INTO DS_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('DS_USERS_SEQ'), ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM DS_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD" +
            " FROM DS_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM DS_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException{
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println(hashedPass);
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPass);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        }catch (Exception e){
            throw new EtAuthException("Invalid details, no account created");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException{
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, userRowMapper, new Object[]{email});
            if(!BCrypt.checkpw(password, user.getPassword())) throw new EtAuthException("Invalid email/password");
            //email is in db and passwords match
            return user;
        }
        catch (EmptyResultDataAccessException e){
            throw new EtAuthException("invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        //second object represents all parameters in query, last object is expected return type
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, new Object[]{email});
    }

    @Override
    public User findById(Integer userId) {
        //row mapper: take result set and return a proper user object
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, new Object[]{userId});
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        //rs is result set
       return new User(rs.getInt("USER_ID"),
               rs.getString("FIRST_NAME"),
               rs.getString("LAST_NAME"),
               rs.getString("EMAIL"),
               rs.getString("PASSWORD"));
    });
}
