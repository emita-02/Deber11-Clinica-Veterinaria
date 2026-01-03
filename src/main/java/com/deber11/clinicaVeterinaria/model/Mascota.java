package com.deber11.clinicaVeterinaria.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Mascota {
    private Long id;
    private String nombre;
    private String especie;
    private String fechaNacimiento;
    private String propietario;
}
