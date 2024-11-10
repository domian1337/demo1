package com.example.conroller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String user(Model model, @AuthenticationPrincipal UserDetails person) {
        model.addAttribute("personU", personService.getPersonByEmail(person.getUsername()));
        return "userPage";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('DEVELOPERS:WRITE')")
    public String getAdminPage(Model model, @AuthenticationPrincipal UserDetails person) {
        model.addAttribute("persons", personService.getPersons());
        model.addAttribute("personU", personService.getPersonByEmail(person.getUsername()));
        model.addAttribute("person", new Person());
        return "adminPage";
    }
}