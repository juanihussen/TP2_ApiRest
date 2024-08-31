package com.example.Empleados.dto;

import com.example.Empleados.entity.Empleado;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;



@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long nroDocumento;
    private String nombre;
    private String apellido;

    @Email(message = "El email ingresado no es correcto.")
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;
    private LocalDate fechaCreacion;

    public  Empleado toEntity() {
        Empleado empleado = new Empleado();
        empleado.setId(this.getId());
        empleado.setNombre(this.getNombre());
        empleado.setApellido(this.getApellido());
        empleado.setEmail(this.getEmail());
        empleado.setNroDocumento((this.getNroDocumento()));
        empleado.setFechaNacimiento(this.getFechaNacimiento());
        empleado.setFechaIngreso(this.getFechaIngreso());
        empleado.setFechaCreacion(this.getFechaCreacion());
        return empleado;
    }

    public EmpleadoDTO toDTO() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(this.getId());
        dto.setNombre(this.getNombre());
        dto.setApellido(this.getApellido());
        dto.setEmail(this.getEmail());
        dto.setNroDocumento((this.getNroDocumento()));
        dto.setFechaNacimiento(this.getFechaNacimiento());
        dto.setFechaIngreso(this.getFechaIngreso());
        dto.setFechaCreacion(this.getFechaCreacion());
        return dto;
    }
}

