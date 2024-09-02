package com.example.Empleados.controller;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.service.IEmpleadoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<?> altaEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) throws BadRequestException {
        this.empleadoService.altaEmpleado(empleadoDTO);
        return ResponseEntity.created(URI.create("/empleado/" + empleadoDTO.getEmpleadoId()))
                .body(empleadoService.getClass());
    }

    @GetMapping
    public ResponseEntity<List<?>> findEmpleados() {
        List<EmpleadoDTO> empleados = this.empleadoService.findAllEmpleados();
        return ResponseEntity.ok(empleados);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmpleadoDTO> findEmpleadoById(@NotNull @PathVariable("id") Long id) {
        EmpleadoDTO empleadoDTO = this.empleadoService.findEmpleadoById(id);
        return ResponseEntity.ok(empleadoDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmpleadoDTO> updateEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO, @NotNull @PathVariable("id") Long id) throws BadRequestException {
        EmpleadoDTO empleadoActualizadoDTO = this.empleadoService.updateEmpleado(empleadoDTO, id);
        return ResponseEntity.ok(empleadoActualizadoDTO);
    }


}


