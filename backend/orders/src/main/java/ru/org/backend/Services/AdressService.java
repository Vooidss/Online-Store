package ru.org.backend.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.org.backend.DTO.AdressDetails;
import ru.org.backend.Models.Adress;
import ru.org.backend.Repositories.AdressRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdressService {
    private final AdressRepository adressRepository;

    public Adress generateAdress(AdressDetails adressDetails, Integer userId){
        return Adress
                .builder()
                .userId(userId)
                .city(adressDetails.getCity())
                .street(adressDetails.getStreet())
                .numberHouse(adressDetails.getNumberHouse())
                .numberApartment(adressDetails.getNumberApartment())
                .numberIntercom(adressDetails.getNumberIntercom())
                .created_at(LocalDateTime.now())
                .build();
    }

    public Adress save(Adress adress){
        return adressRepository.save(adress);
    }

}
