package com.example.Empleados.entity;


import com.example.Empleados.dto.ConceptoDTO;
import com.example.Empleados.dto.EmpleadoDTO;
import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "concepto_laboral")
public class Concepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "hs_minimo")
    private Integer hsMinimo;

    @Column(name = "hs_maximo")
    private Integer hsMAximo;

    private boolean laborable;


    public ConceptoDTO toDTO() {
        ConceptoDTO dto = new ConceptoDTO();
        dto.setId(this.getId());
        dto.setNombre(this.getNombre());
        dto.setHsMinimo(this.getHsMinimo());
        dto.setHsMAximo(this.getHsMAximo());
        dto.setLaborable((this.isLaborable()));
        return dto;
    }

}
