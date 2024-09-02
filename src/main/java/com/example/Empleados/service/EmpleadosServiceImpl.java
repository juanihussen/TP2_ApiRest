package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.exceptions.ConflictException;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.EmpleadoRepository;
import com.example.Empleados.validator.EmpleadoValidator;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
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


    @Override
    public void altaEmpleado(EmpleadoDTO empleadoDTO) throws BadRequestException {
        Empleado empleado = empleadoDTO.toEntity();
        validarEmpleado(empleado);
        empleado = this.repository.save(empleado);
    }

    @Override
    public EmpleadoDTO updateEmpleado(EmpleadoDTO empleadoActualizadoDTO, Long id) throws BadRequestException {
        Empleado empleadoActualizado = empleadoActualizadoDTO.toEntity();

        EmpleadoDTO empleadoEncontradoDTO = findEmpleadoById(id);
        Empleado empleadoEncontrado = empleadoEncontradoDTO.toEntity();

        if (!empleadoActualizado.getEmail().equals(empleadoEncontrado.getEmail())) {
            validator.validarEmailUnico(empleadoActualizado.getEmail());
            empleadoEncontrado.setEmail(empleadoActualizado.getEmail());
        }
        if (!empleadoActualizado.getNroDocumento().equals(empleadoEncontrado.getNroDocumento())) {
            validator.validarDocumentoUnico(empleadoActualizado.getNroDocumento());
            empleadoEncontrado.setNroDocumento(empleadoActualizado.getNroDocumento());
        }

        validator.validarEdad(empleadoActualizado.getFechaNacimiento());
        validator.validarFechas(empleadoActualizado.getFechaIngreso(), empleadoActualizado.getFechaNacimiento());
        validator.validarSoloLetrasEnNombreYApellido("nombre", empleadoActualizado.getNombre());
        validator.validarSoloLetrasEnNombreYApellido("apellido", empleadoActualizado.getApellido());

        empleadoEncontrado.setNombre(empleadoActualizado.getNombre());
        empleadoEncontrado.setApellido(empleadoActualizado.getApellido());
        empleadoEncontrado.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleadoEncontrado.setFechaIngreso(empleadoActualizado.getFechaIngreso());

        empleadoEncontrado = this.repository.save(empleadoEncontrado);

        return empleadoEncontrado.toDTO();
    }

    @Override
    public void deleteEmpleadoById(Long id) {

    }


    public void validarEmpleado(Empleado empleado) throws BadRequestException {
        validator.validarEmailUnico(empleado.getEmail());
        validator.validarEmailNotNull(empleado.getEmail());
        validator.validarDocumentoNotNull(empleado.getNroDocumento());
        validator.validarDocumentoUnico(empleado.getNroDocumento());
        validator.validarEdad(empleado.getFechaNacimiento());
        validator.validarFechas(empleado.getFechaIngreso(),empleado.getFechaNacimiento());
        validator.validarSoloLetrasEnNombreYApellido("nombre",empleado.getNombre());
        validator.validarSoloLetrasEnNombreYApellido("apellido",empleado.getApellido());
    }


}
