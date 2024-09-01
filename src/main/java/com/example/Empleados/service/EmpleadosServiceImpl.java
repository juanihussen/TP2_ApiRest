package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.EmpleadoRepository;
import com.example.Empleados.validator.EmpleadoValidator;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadosServiceImpl implements IEmpleadoService{

    @Autowired
    EmpleadoRepository repository;

    @Autowired
    EmpleadoValidator validator;

    @Override
    public List<EmpleadoDTO> findAllEmpleados() {
        List<Empleado> empleados = this.repository.findAll();
        return empleados.stream().map(Empleado::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO findEmpleadoById(Long id) {
        Optional<Empleado> empleado = this.repository.findById(id);
        if (empleado.isPresent()) {
            return empleado.get().toDTO();
        } else {
            throw new NotFoundException("No se encontró el empleado con Id: " + id);
        }
    }


    @PostMapping
    public void altaEmpleado(@RequestBody EmpleadoDTO empeladoDTO) throws BadRequestException {
        Empleado empleado = empeladoDTO.toEntity();
        validator.validarEdad(empleado.getFechaNacimiento());
        validator.validarDocumentoUnico(empleado.getNroDocumento());
        validator.validarEmail(empleado.getEmail());
        validator.validarFechas(empleado.getFechaIngreso(),empleado.getFechaNacimiento());
        validator.validarSoloLetrasEnNombreYApellido("nombre",empleado.getNombre());
        validator.validarSoloLetrasEnNombreYApellido("apellido",empleado.getApellido());
        empleado = this.repository.save(empleado);
    }

    @Override
    public EmpleadoDTO updateEmpleado(Long id) {
        return null;
    }
}
