package com.example.conroller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PersonResource {

    private final PersonService personService;

    @Autowired
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/user{id}")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    public void editPerson(@RequestBody Person person) {
        personService.updatePerson(
                person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge(),
                person.getPassword(), person.getRole());
    }
}
