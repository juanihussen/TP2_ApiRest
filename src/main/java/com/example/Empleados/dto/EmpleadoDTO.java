package com.example.Empleados.dto;

import com.example.Empleados.entity.Empleado;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;



@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private Long nroDocumento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;


    public  Empleado toEntity() {
        Empleado empleado = new Empleado();
        empleado.setId(this.getId());
        empleado.setNombre(this.getNombre());
        empleado.setApellido(this.getApellido());
        empleado.setEmail(this.getEmail());
        empleado.setFechaNacimiento(this.getFechaNacimiento());
        empleado.setFechaIngreso(this.getFechaIngreso());
        return empleado;
    }

    public EmpleadoDTO toDTO() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(this.getId());
        dto.setNombre(this.getNombre());
        dto.setApellido(this.getApellido());
        dto.setEmail(this.getEmail());
        dto.setFechaNacimiento(this.getFechaNacimiento());
        dto.setFechaIngreso(this.getFechaIngreso());
        return dto;
    }
}

