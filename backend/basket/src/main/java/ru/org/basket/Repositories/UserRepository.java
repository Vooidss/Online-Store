package ru.org.basket.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.basket.Model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
