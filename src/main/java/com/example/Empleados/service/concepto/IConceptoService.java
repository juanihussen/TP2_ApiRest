package com.example.Empleados.service.concepto;

import com.example.Empleados.dto.ConceptoDTO;
import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Concepto;

import java.util.List;

public interface IConceptoService {
    List<ConceptoDTO> findAllConceptos(Integer id,String nombreConcepto);
}
