package com.example.Empleados.dto;

import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JornadaRequest {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ConceptoRepository conceptoRepository;

    @NotNull
    private Long idEmpleado;

    @NotNull
    private Integer idConcepto;

    private LocalDate fecha;

    private Integer horasTrabajadas;

    public JornadaLaboral toEntity(Empleado empleado, Concepto concepto) {
        JornadaLaboral jornadaLaboral = new JornadaLaboral();

        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConcepto(concepto);
        jornadaLaboral.setFecha(this.getFecha());
        jornadaLaboral.setHorasTrabajadas(this.getHorasTrabajadas());

        return jornadaLaboral;
    }
}
