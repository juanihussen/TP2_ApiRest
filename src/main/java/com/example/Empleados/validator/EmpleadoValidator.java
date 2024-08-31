package com.example.Empleados.validator;
import com.example.Empleados.exceptions.ConflictException;
import com.example.Empleados.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Component
public class EmpleadoValidator {

    @Autowired
    private EmpleadoRepository repository;

    public void validarEdad(LocalDate fechaNacimiento) throws BadRequestException {
        if(fechaNacimiento == null){
            throw new BadRequestException("Es necesarop que coloques una fecha de nacimiento.");
        } else {
            int edadEmpleado = Period.between(fechaNacimiento, LocalDate.now()).getYears();
            if (edadEmpleado < 18) {
                throw new BadRequestException("El empleado no puede ser menor a 18 años. ");
            }
        }
    }

    public void validarDocumentoUnico(Long documentoEmpleado) {
        if(documentoEmpleado == null){
            throw new ConflictException("Es necesario que ingrese un numero de documento para crear un empleado. ");
        } else {
            if (repository.existsByNroDocumento(documentoEmpleado)){
                throw new ConflictException("Ya existe un empleado con el documento ingresado. ");
            }
        }
    }

    public void validarEmail(String emailEmpleado) {
        if(emailEmpleado == null){
            throw new ConflictException("Es necesario que ingrese un email para crear un empleado. ");
        } else {
            if (repository.existsByEmail(emailEmpleado)){
                throw new ConflictException("Ya existe un empleado con el email ingresado. ");
            }
        }
    }

    public void validarFechas(LocalDate fechaIngresoEmpleado, LocalDate fechaNacimiento) throws BadRequestException {
        if(fechaIngresoEmpleado == null){
            throw new BadRequestException("Es necesario que ingrese la fecha de ingreso para crear un empleado. ");
        } else {
            if(fechaIngresoEmpleado.isAfter(LocalDate.now())){
                throw new BadRequestException("La fecha de ingreso no puede ser posterior al día de la fecha.");
            }
            if(fechaNacimiento.isAfter(LocalDate.now())){
                throw new BadRequestException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
            }
        }
    }

    /*
    public void formatoMailIngresado (String emailEmpleado) {

    }
     */

    public void validarSoloLetrasEnNombreYApellido(String campo, String valor) throws BadRequestException {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        if (!pattern.matcher(valor).matches()) {
            throw new BadRequestException("Solo se permiten letras en el campo '" + campo + "'");
        }
    }














}
