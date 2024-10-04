package ru.org.backend.Services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.org.backend.Repositories.UserRepositories;
import ru.org.backend.user.MyUser;
import ru.org.backend.user.Role;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepositories userRepository;

    public void save(MyUser user)
        throws ConstraintViolationException, DataIntegrityViolationException, UnexpectedTypeException {
        userRepository.save(user);
    }

    public MyUser getByLogin(String login) {
        return userRepository
            .findByLogin(login)
            .orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден")
            );
    }

    public MyUser getByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException("Пользователь не найден")
            );
    }

    public MyUser getCurrentUser() {
        var username = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        return getByLogin(username);
    }

    public ResponseEntity<MyUser> getResponseCurrentUser() {
        return ResponseEntity.ok(getCurrentUser());
    }

    public ResponseEntity<Map<String, Integer>> findUserId() {
        MyUser myUser = getCurrentUser();

        return ResponseEntity.ok(Map.of("Id", myUser.getId()));
    }

    public  ResponseEntity<Map<String,Object>> updateUser(MyUser myUser) {
        log.info("Получаем id залогированного пользователя...");

        int id = getCurrentUser().getId();

        log.info("id получен!");

        log.info("Проверяем существует ли пользователь с таким id в базе данных...");

        if(userRepository.existsById(id)){
            log.info("Пользователь существует!");
            log.info("Сохраняем пользователя...");

            MyUser updateUser = userRepository.save(myUser);

            log.info("Пользователь сохранён!");

            return ResponseEntity.ok().body(
                    Map.of(
                            "user", updateUser,
                            "code", HttpStatus.OK.value(),
                            "status", HttpStatus.OK,
                            "message", "Данные пользователя обновлены"
                    ));
        }else{
            log.error("Пользователя с таким ID не существует. :(");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "user",null,
                            "code",HttpStatus.NOT_FOUND.value(),
                            "status",HttpStatus.NOT_FOUND,
                            "message","Пользователя не найден"
                    ));
        }
    }

    public ResponseEntity<Map<String, Object>> replenishmentMoney(Integer money) {

        int id = 0;
        int currentMoneys = 0;
        MyUser myUser = null;

        log.info("Денег к зачислению: " + money);
        log.info("Получаем текущего пользователя...");

        try {
             myUser = getCurrentUser();
        }catch (RuntimeException e){
            log.error("Пользователь не найден");
        }

        log.info("Пользовательб получен");
        log.info("Поулчаем id");

        try{
            assert myUser != null;
            id = myUser.getId();
        }catch (RuntimeException e){
            log.error("Ошибка получения данных");
            throw new RuntimeException(e);
        }

        log.info("Данные получены");
        log.info("Пополняем счёт...");

        userRepository.addToWallet(id,money);

        try {
            log.info("Счёт успешно пополнен");
        }catch (RuntimeException e) {
            log.error("Счёт не пополнен.");
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "code",HttpStatus.NOT_FOUND.value(),
                            "status",HttpStatus.NOT_FOUND,
                            "message", "Счёт не поплнен."
                    )
            );
        }
        return ResponseEntity.ok().body(
                Map.of(
                        "code",HttpStatus.OK.value(),
                        "status",HttpStatus.OK,
                        "message", "Счёт успешно пополнен на " + money + "рублей \n Текущие счёт " + myUser.getMoney()
                )
        );
    }
}
