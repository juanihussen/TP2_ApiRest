package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.repository.EmpleadoRepository;
import com.example.Empleados.validator.EmpleadoValidator;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
public class EmpleadosServiceImpl implements IEmpleadoService{

    @Autowired
    EmpleadoRepository repository;

    @Autowired
    EmpleadoValidator validator;

    //checkear validaciones H001

    @PostMapping
    public void altaEmpleado(@RequestBody EmpleadoDTO empeladoDTO) throws BadRequestException {
        Empleado empleado = empeladoDTO.toEntity();
        //validaciones
        validator.validarEdad(empleado.getFechaNacimiento());


        empleado = this.repository.save(empleado);
    }
}
