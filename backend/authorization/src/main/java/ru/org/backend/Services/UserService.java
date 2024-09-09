package ru.org.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.org.backend.Repositories.UserRepositories;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.Role;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepositories userRepository;

    public Optional<MyUser> findById(int id){
        return userRepository.findById(id);
    }

    public Optional<MyUser> findByName(String name){
        return userRepository.findByName(name);
    }

    public List<MyUser> findAll(){
        return userRepository.findAll();
    }
    public MyUser getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public MyUser getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByLogin(username);
    }
}
