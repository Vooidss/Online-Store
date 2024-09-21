package ru.org.backend.Controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/defualt")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DefualtController {

    @GetMapping
    public String getDefualt(){
        return "okay";
    }

}
