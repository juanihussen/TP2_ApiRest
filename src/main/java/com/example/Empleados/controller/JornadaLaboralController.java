package com.example.Empleados.controller;

import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequestDTO;
import com.example.Empleados.service.Jornadas.IJornadasService;
import com.example.Empleados.service.empelado.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {

    @Autowired
    private IJornadasService jornadasService;

    @PostMapping
    public ResponseEntity<JornadaLaboralDTO> crearJornadaLaboral(@RequestBody JornadaRequestDTO requestDTO) {
        JornadaLaboralDTO jornadaLaboralDTO = jornadasService.crearJornadaLaboral(requestDTO);
        return new ResponseEntity<>(jornadaLaboralDTO, HttpStatus.CREATED);
    }
}
