package com.hukising.telegrambot.Services;

import com.hukising.telegrambot.Models.Size;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    public List<Size> convertStringInSize(String size){
        if(size.contains(",")){
            List<String> sizesString = List.of(size.split(","));
            return sizesString
                    .stream()
                    .map(x -> x.replaceAll(" ",""))
                    .map(Size::new)
                    .toList();
        }

        return List.of(new Size(size));
    }

}
