package ru.org.basket.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.org.basket.Repositories.UserRepository;

@Service
@AllArgsConstructor
public class BasketService {
    private final UserRepository userRepository;

    public void addProductForUser(){

    }
}
