package com.example.conroller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PersonResource {

    private final PersonService personService;

    @Autowired
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public Person getPerson(@PathVariable Long id) {
        return personService.getPerson(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public void addPerson(@RequestBody Person person) {
        personService.addNewPerson(person);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public void editPerson(@RequestBody Person person) {
        personService.updatePerson(
                person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge(),
                person.getPassword(), person.getRole());
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public void deletePerson(@RequestBody Person person) {
        personService.deletePerson(person.getId());
    }
}
