package com.tutorial.crud.dto;
import com.tutorial.crud.security.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsuario {

    private String mensaje;
    private Usuario usuario;
}
