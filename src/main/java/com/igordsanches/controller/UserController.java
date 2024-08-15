package com.igordsanches.controller;

import com.igordsanches.models.User;
import com.igordsanches.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository _repository;

    UserController(UserRepository repository) {
        this._repository = repository;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> get() {
        return _repository.findAll();
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getByUsername(@PathVariable(value = "username") String username)
    {
        User user = _repository.findByUsername(username);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user", method =  RequestMethod.POST)
    public User createNewUser(@Validated @RequestBody User pessoa)
    {
        return _repository.save(pessoa);
    }

    @RequestMapping(value = "/user/{id}", method =  RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") String id, @Validated @RequestBody User newUser)
    {
        Optional<User> oldPessoa = _repository.findById(id);
        if(oldPessoa.isPresent()){
            User user = oldPessoa.get();
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            _repository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") String id)
    {
        Optional<User> user = _repository.findById(id);
        if(user.isPresent()){
            _repository.delete(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
