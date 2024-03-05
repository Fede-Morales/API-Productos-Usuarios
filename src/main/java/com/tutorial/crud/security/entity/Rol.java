package com.tutorial.crud.security.entity;

import com.tutorial.crud.security.enums.RolNombre;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;
    
    public Rol(@NotNull RolNombre rolNombre){
        this.rolNombre = rolNombre;
    }
}
