package com.example.Empleados.dto;

import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoDTO {

    private Long id;

    private String nombre;

    private Integer hsMinimo;

    private Integer hsMAximo;

    private boolean laborable;

    public Concepto toEntity() {
        Concepto concepto = new Concepto();
        concepto.setId(this.getId());
        concepto.setNombre(this.getNombre());
        concepto.setHsMinimo(this.getHsMinimo());
        concepto.setHsMAximo(this.getHsMAximo());
        concepto.setLaborable((this.isLaborable()));
        return concepto;
    }

}




