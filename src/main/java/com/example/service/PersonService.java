package com.example.service;

import com.example.model.Person;
import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PersonService {


    private final PersonDao personDao;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> getPersons() {
        return personDao.findAll();
    }

    public Person getPersonByEmail(String email) {
        return personDao.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с email = " + email + "не существует"));
    }

    public Person getPerson(Long id) {
        return personDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователя с id = " + id + "не существует"));
    }

    public void addNewPerson(Person person) {
        Optional<Person> personOptional = personDao.findByEmail(person.getEmail());
        if (personOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDao.save(person);
    }

    @Transactional
    public void updatePerson(Long id, String firstName, String lastName, String email,
                             int age, String password, Role role) {
        Person person = personDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("пользователя с id = " + id + "не существует"));
        if (firstName != null && !firstName.isEmpty() && !Objects.equals(firstName, person.getFirstName())) {
            person.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty() && !Objects.equals(lastName, person.getLastName())) {
            person.setLastName(lastName);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(email, person.getEmail())) {
            Optional<Person> personOptional = personDao.findByEmail(email);
            if (personOptional.isPresent()) {
                throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
            }
            person.setEmail(email);
        }
        if (age != person.getAge() && age != 0) {
            person.setAge(age);
        }
        if (password != null && !password.isEmpty() && !Objects.equals(password, person.getPassword())) {
            person.setPassword(passwordEncoder.encode(password));
        }
        if (role != person.getRole() && role != null) {
            person.setRole(role);
        }
    }

    public void deletePerson(Long personId) {
        boolean exists = personDao.existsById(personId);
        if (!exists) {
            throw new IllegalArgumentException("Пользователь с id" + personId + "не существует");
        }
        personDao.deleteById(personId);
    }
}