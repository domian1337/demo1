package com.example.conroller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('DEVELOPERS:READ')")
    public String user(Model model, @RequestParam Long id) {
        model.addAttribute("person", personService.getPerson(id));
        return "userPage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public String getAdminPage(Model model) {
        model.addAttribute("persons", personService.getPersons());
        model.addAttribute("person", new Person());
        return "adminPage";
    }

    @GetMapping("/user{id}")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public Person getUserPage(@RequestParam Long id) {
        return personService.getPerson(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public String addPerson(@ModelAttribute("person") Person person) {
        personService.addNewPerson(person);
        return "redirect:/people/admin";
    }

    @PostMapping( "/delete")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public String deletePerson(@RequestParam("id") Long id) {
        personService.deletePerson(id);
        return "redirect:/people/admin";
    }

    @PostMapping( "/edit")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public String updatePerson(@ModelAttribute("personEdit") Person personEdit) {
        personService.updatePerson(
                personEdit.getId(), personEdit.getFirstName(), personEdit.getLastName(), personEdit.getEmail(), personEdit.getAge(),
                personEdit.getPassword(), personEdit.getRole());
        return "redirect:/people/admin";
    }
}
