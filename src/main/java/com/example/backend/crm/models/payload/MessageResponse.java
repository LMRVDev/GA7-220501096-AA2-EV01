package com.example.backend.crm.models.payload;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/*
 * MensajeResponse es una clase DTO (Data Transfer Object)
 * y se utiliza para encapsular la respuesta de los
 * métodos del controlador en una estructura estándar,
 * permita encapsular las respuestas en un objeto
 * estandarizado en lugar de retornar tipos simples o
 * entidades directamente desde los controladores
 */
@Data
@ToString
@Builder
public class MessageResponse implements Serializable {

    private String message;
    private Object object;

}
