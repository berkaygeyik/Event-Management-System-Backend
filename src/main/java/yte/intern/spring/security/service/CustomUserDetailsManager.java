package yte.intern.spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import yte.intern.spring.security.entity.Users;
import yte.intern.spring.security.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void createUser(UserDetails userDetails) {
        Users users = (Users) userDetails;
        if (usersRepository.existsByUsername(users.getUsername())) {
            System.out.println("The person with this username has been already created.");
        }
        else{
            users.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            usersRepository.save(users);
        }
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        Users users = (Users) loadUserByUsername(userDetails.getUsername());
        Users oldUser = (Users) loadUserByUsername(userDetails.getUsername());
        Users newUser = (Users) userDetails;
        newUser.setId(oldUser.getId());
        usersRepository.save(newUser);

    }

    @Override
    public void deleteUser(String username) {
        usersRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String username) {
        return usersRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username);
    }
}
