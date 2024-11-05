package com.example.security;

import com.example.model.Person;
import com.example.service.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonDao personDao;

    @Autowired
    public UserDetailsServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        return SecurityUser.fromPerson(person);
    }
}
