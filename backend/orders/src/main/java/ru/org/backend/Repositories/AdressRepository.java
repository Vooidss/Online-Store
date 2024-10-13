package ru.org.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.org.backend.Models.Adress;

public interface AdressRepository extends JpaRepository<Adress,Integer> {
}
