package com.example.Empleados.dto;

import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JornadaLaboralDTO {

    private Long id;
    private Integer nroDocumento;
    private String nombreCompleto;
    private LocalDate fecha;
    private String concepto;
    private Integer hsTrabajadas;


    public static JornadaLaboralDTO toDTO(JornadaLaboral jornadaLaboral) {
        JornadaLaboralDTO dto = new JornadaLaboralDTO();
        dto.setId(jornadaLaboral.getId());
        dto.setNroDocumento(jornadaLaboral.getEmpleado().getNroDocumento());
        dto.setNombreCompleto(jornadaLaboral.getEmpleado().getNombre() + " " + jornadaLaboral.getEmpleado().getApellido());
        dto.setFecha(jornadaLaboral.getFecha());
        dto.setConcepto(jornadaLaboral.getConcepto().getNombre());
        dto.setHsTrabajadas(jornadaLaboral.getHorasTrabajadas());
        dto.setHsTrabajadas(jornadaLaboral.getHorasTrabajadas() != null ? jornadaLaboral.getHorasTrabajadas() : null);
        return dto;
    }

    public JornadaLaboral toEntity() {
        JornadaLaboral jornadaLaboral = new JornadaLaboral();
        Empleado empleado = new Empleado();
        Concepto concepto = new Concepto();

        empleado.setId(this.getId());
        concepto.setId(this.getId());

        jornadaLaboral.setId(this.getId());
        jornadaLaboral.setFecha(this.getFecha());
        jornadaLaboral.setHorasTrabajadas(this.getHsTrabajadas());
        jornadaLaboral.setEmpleado(empleado);
        jornadaLaboral.setConcepto(concepto);

        return jornadaLaboral;
    }
}
