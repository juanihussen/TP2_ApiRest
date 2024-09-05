package com.example.Empleados.entity;

import com.example.Empleados.dto.EmpleadoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empleados")

public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nro_documento")
    private Integer nroDocumento;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public EmpleadoDTO toDTO() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setEmpleadoId(this.getId());
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
