package ru.org.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.org.backend.Repositories.UserRepositories;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.Role;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final UserRepositories userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByLogin(username);

        if(user.isPresent()){
            MyUser userObj = user.get();
            return User.builder()
                    .username(userObj.getLogin())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole().toString())
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
