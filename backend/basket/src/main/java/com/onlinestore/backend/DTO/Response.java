package ru.org.basket.DTO;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
public abstract class Response{
    protected String message;
    protected HttpStatus status;
    protected int code;
}
