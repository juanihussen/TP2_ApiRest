package com.example.Empleados.dto;

import com.example.Empleados.entity.Empleado;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {

    private Long empleadoId;
    private Long nroDocumento;
    private String nombre;
    private String apellido;

    @Email(message = "El email ingresado no es correcto.")
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;
    private LocalDateTime fechaCreacion;

    public  Empleado toEntity() {
        Empleado empleado = new Empleado();
        empleado.setId(this.getEmpleadoId());
        empleado.setNombre(this.getNombre());
        empleado.setApellido(this.getApellido());
        empleado.setEmail(this.getEmail());
        empleado.setNroDocumento((this.getNroDocumento()));
        empleado.setFechaNacimiento(this.getFechaNacimiento());
        empleado.setFechaIngreso(this.getFechaIngreso());
        empleado.setFechaCreacion(this.getFechaCreacion());
        return empleado;
    }
}

