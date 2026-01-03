package com.deber11.clinicaVeterinaria.controller;

import com.deber11.clinicaVeterinaria.model.Mascota;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/veterinaria")
public class VeterinariaController {
    //Lista simulada de pacientes en la clinica
    private List<Mascota> listaMascotas = new ArrayList<>();

    public VeterinariaController(){
        listaMascotas.add(new Mascota(1L, "Luna", "Perro", LocalDate.of(2021,5,12), "Andrea Gómez"));
        listaMascotas.add(new Mascota(2L, "Kitty", "Gato", LocalDate.of(2024,11,3), "Alison Mora"));
        listaMascotas.add(new Mascota(3L, "Mailo", "Perro", LocalDate.of(2015,11,2), "Paulethe Pazmiño"));
        listaMascotas.add(new Mascota(4L, "Kiara", "Tortuga", LocalDate.of(2019,7,18), "Diego Herrera"));
        listaMascotas.add(new Mascota(5L, "Nala", "Hamster", LocalDate.of(2023,8,21), "Mariana López"));
    }

    //1. Devolver todas las mascotas registradas en la clinica usnado @ResponseEntity
    @GetMapping("/mascotas")
    public ResponseEntity<List<Mascota>> devolverMascota(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("X-App-Version", "1.0")
                .header("X-Response-Time", LocalDateTime.now().toString())
                .body(listaMascotas);
    }

    //2. Devolver una mascota por su id usando @PathVariable
    @GetMapping("/mascotaID/{id}")
    public ResponseEntity<Mascota> devolverMascotaPorID(@PathVariable Long id){
        for (Mascota l : listaMascotas){
            if (l.getId().equals(id)){
                return ResponseEntity.status(HttpStatus.OK).body(l);
            }
        }

        //Si no hay resultado devolver ResponseEntity con 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //3. Devolver las mascotas menores a la edad que se ingrese usando @RequestParam
    @GetMapping("/mascotas/menores")
    public ResponseEntity<List<Mascota>> devolverMascotaMenor(@RequestParam int edad){
        List<Mascota> mascotaResultado = new ArrayList<>();

        for (Mascota m : listaMascotas){

            int edadMascota = Period.between(m.getFechaNacimiento(), LocalDate.now()).getYears();

            if (edadMascota < edad){
                mascotaResultado.add(m);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(mascotaResultado);
    }

    //4. Registrar una nueva mascota usando @RequestBody
    @PostMapping("/mascotas/nuevaMascota")
    public ResponseEntity<Mascota> registrarNuevaMascota(@RequestBody Mascota masc){
        listaMascotas.add(masc);
        System.out.println("✅Nuevo libro registrado.");
        System.out.println("Nombre: "+masc.getNombre());
        System.out.println("Especie: "+masc.getEspecie());
        System.out.println("Fecha de Nacimiento: "+masc.getFechaNacimiento());
        System.out.println("Propietario: "+masc.getPropietario());

        //Devuelve codigo 201 y libro en el cuerpo
        return ResponseEntity.status(HttpStatus.CREATED).body(masc);
    }
}
