package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.repository.EmpleadoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



public class EmpleadosServiceImpl implements IEmpleadoService{

    @Autowired
    EmpleadoRepository repository;

    @GetMapping(name = "/findAllEmpelados/")
    public List<Empleado> findAllEmpleados() {
        return List.of();
    }
    @GetMapping(name = "/findEmpleadoById/{id}")
    public Optional<Empleado> findEmpleadosById(@PathVariable Long id) {
        return Optional.empty();
    }


    //checkear problema H001
    @PostMapping(name = "/altaEmpleado/")
    public ResponseEntity<?> altaEmpleado(@RequestBody EmpleadoDTO empeladoDTO) {
        Empleado empleado = empeladoDTO.toEntity();
        if(repository.findById(empleado.getId())){

        } else {
            empleado = this.repository.save(empleado);
            return ResponseEntity()

        }
    }

    @DeleteMapping(name = "/deleteEmpleado")
    public void deleteEmpleadoById(Long id) {
    }
}
