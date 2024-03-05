package com.tutorial.crud.entity;



import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "prodcuto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private Float precio;
    
    
    public Producto(String nombre, float precio){
        this.nombre = nombre;
        this.precio = precio;
    }
    
}
