package com.steve.authenticationService.repository;

import com.steve.authenticationService.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

public class UserRepository {

    private final MongoTemplate userDao;

    public UserRepository(MongoTemplate userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findByEmail(String email) {
        Criteria criteria = Criteria.where("email").is(email);
        Query query = new Query(criteria);
        User user = userDao.findOne(query, User.class);
        return Optional.ofNullable(user);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }
}
