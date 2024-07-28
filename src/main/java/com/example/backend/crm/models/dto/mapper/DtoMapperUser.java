package com.example.backend.crm.models.dto.mapper;

import com.example.backend.crm.models.dto.UserDto;
import com.example.backend.crm.models.entities.User;

/**
 * La clase DtoMapperUser corresponde al patrón de diseño conocido como
 * "Builder" o "Fluent Builder", que se utiliza para crear objetos de tipo
 * UserDto a partir de objetos de tipo User.
 */
public class DtoMapperUser {

    private User user;
    private boolean admin;

    /*
     * El constructor privado no permita crear instancias
     * directamente, el patrón builder requiere instancear
     * la clase a través de un método estático builder()
     */
    private DtoMapperUser() {
    }

    /*
     * Devuelve una instancia de la clase DtoMapperUser,
     * utilizada para construir un objeto UserDto.
     */
    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    

    /*
     * El setter permite establecer el objeto User que se
     * utilizará para construir el objeto UserDto.
     * Devuelve la instancia actual de la clase DtoMapperUse
     * por lo que permite llamar a otro método sin 
     * necesidad de asignar el resultado a una variable
     */
    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public DtoMapperUser setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }


    

    public boolean isAdmin() {
        return admin;
    }

    /*
     * El método build() crea un nuevo objeto UserDto y lo
     * configura con los valores del objeto User
     */
    public UserDto build() {
        if (user == null) {
            throw new RuntimeException(
                    "Debe pasar el entity User"
            );
        }
        return new UserDto(
                this.user.getId(),
                this.user.getUsername(),
                this.user.getEmail(),
                isAdmin()
        );

    }

 

}
