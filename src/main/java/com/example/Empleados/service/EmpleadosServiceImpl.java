package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Service
public class EmpleadosServiceImpl implements IEmpleadoService{

    @Autowired
    EmpleadoRepository repository;

    //checkear problema H001
    @PostMapping(name = "/altaEmpleado/")
    public ResponseEntity<?> altaEmpleado(@RequestBody EmpleadoDTO empeladoDTO) {
        Empleado empleado = empeladoDTO.toEntity();
        empleado = this.repository.save(empleado);
        return ResponseEntity.created(URI.create("/empleado/" + empleado.getId()))
                .body(empeladoDTO);
        }
}
