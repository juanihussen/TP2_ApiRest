package com.example.Empleados.validator;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class EmpleadoValidator {

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



}
