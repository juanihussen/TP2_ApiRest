package com.example.Empleados.dto;

import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JornadaRequestDTO {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ConceptoRepository conceptoRepository;

    private Long idEmpleado;

    private Integer idConcepto;

    private LocalDate fecha;

    private Integer horasTrabajadas;


    public JornadaLaboral toEntity(Empleado empleado, Concepto concepto) {
        JornadaLaboral jornadaLaboral = new JornadaLaboral();

        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConcepto(concepto);
        jornadaLaboral.setFecha(this.getFecha()); // Usando los valores del DTO
        jornadaLaboral.setHorasTrabajadas(this.getHorasTrabajadas());

        return jornadaLaboral;
    }




}
