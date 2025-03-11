package com.unilabs.gestionhospital.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Hospital {

    private static Hospital instance;


    private String nombre;
    private String direccion;

    private Hospital() {

    }


    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }


}