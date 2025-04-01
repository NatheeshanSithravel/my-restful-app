package com.example.service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.example.entity.User;

@Stateless
public class UserService {
    
    @PersistenceContext(unitName = "myAppPU")
    private EntityManager entityManager;
    
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }
    
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }
    
    public User updateUser(User user) {
        return entityManager.merge(user);
    }
    
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
    
    public List<User> findUsersByUsername(String username) {
        return entityManager.createQuery(
            "SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(:username)", 
            User.class)
            .setParameter("username", "%" + username + "%")
            .getResultList();
    }
}