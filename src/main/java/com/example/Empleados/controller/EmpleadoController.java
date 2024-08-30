package com.example.Empleados.controller;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.service.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<Class<? extends IEmpleadoService>> altaEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        this.empleadoService.altaEmpleado(empleadoDTO);
        return ResponseEntity.created(URI.create("/empleado/" + empleadoDTO.getId()))
                .body(empleadoService.getClass());
    }
}


