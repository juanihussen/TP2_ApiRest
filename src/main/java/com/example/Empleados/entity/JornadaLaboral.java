package com.example.Empleados.entity;

import com.example.Empleados.dto.ConceptoDTO;
import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.dto.JornadaLaboralDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jornadas_laborales")
public class JornadaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Column(name = "horas_trabajadas")
    private Integer horasTrabajadas;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "concepto_id")
    private Concepto concepto;



}
