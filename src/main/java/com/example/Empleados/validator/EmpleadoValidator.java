package com.example.Empleados.validator;
import com.example.Empleados.exceptions.ConflictException;
import com.example.Empleados.exceptions.CustomBadRequestException;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.coyote.BadRequestException;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Component
public class EmpleadoValidator {

    @Autowired
    private EmpleadoRepository repository;


    public void validarEdad(LocalDate fechaNacimiento) throws CustomBadRequestException {
        int edadEmpleado = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edadEmpleado < 18) {
            throw new ConflictException("La edad del empleado no puede ser menor a 18 años.");
        }
    }

    public void validarEmailUnico(String emailEmpleado){
        if (repository.existsByEmail(emailEmpleado)){
            throw new ConflictException("Ya existe un empleado con el email ingresado. ");
        }
    }

    public void validarDocumentoUnico(Integer documentoEmpleado){
        if (repository.existsByNroDocumento(documentoEmpleado)){
            throw new ConflictException("Ya existe un empleado con el documento ingresado. ");
        }
    }


    public void validarFechas(LocalDate fechaIngresoEmpleado, LocalDate fechaNacimiento) throws CustomBadRequestException {
        if(fechaIngresoEmpleado.isAfter(LocalDate.now())){
            throw new CustomBadRequestException("La fecha de ingreso no puede ser posterior al día de la fecha.");
        }
        if(fechaNacimiento.isAfter(LocalDate.now())){
            throw new CustomBadRequestException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
        }
    }

    public void validarSoloLetrasEnNombreYApellido(String campo, String valor) throws CustomBadRequestException {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        if (!pattern.matcher(valor).matches()) {
            throw new CustomBadRequestException("Solo se permiten letras en el campo '" + campo + "'");
        }
    }

    public void validarCamposVacios(Integer documentoEmpleado, String nombre, String apellido, String mail, LocalDate fechaNacimiento, LocalDate fehcaIngreso) {
        if(documentoEmpleado == null){
            throw new ConflictException("Es necesario que ingrese un numero de documento para crear un empleado. ");
        }
        if(nombre.isEmpty()) {
            throw new ConflictException("Es necesario que ingrese un nombre para crear un empleado. ");
        }
        if(apellido.isEmpty()) {
            throw new ConflictException("Es necesario que ingrese un apellido para crear un empleado. ");
        }
        if(mail.isEmpty()) {
            throw new ConflictException("Es necesario que ingrese un mail para crear un empleado. ");
        }
        if(fechaNacimiento == null){
            throw new ConflictException("Es necesario que ingrese una fechaNacimiento para crear un empleado. ");
        }
        if(fehcaIngreso == null){
            throw new ConflictException("Es necesario que ingrese una fehcaIngreso para crear un empleado. ");
        }
    }
}
