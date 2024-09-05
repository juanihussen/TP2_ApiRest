package com.example.Empleados.controller;

import com.example.Empleados.dto.ConceptoDTO;
import com.example.Empleados.entity.Concepto;
import com.example.Empleados.service.concepto.ConceptoServiceImpl;
import com.example.Empleados.service.concepto.IConceptoService;
import com.example.Empleados.service.empelado.IEmpleadoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/concepto")
public class ConceptoController {

    @Autowired
    private IConceptoService conceptoService;

    @GetMapping
    public ResponseEntity<List<ConceptoDTO>> findAllConceptos(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "nombre", required = false) String nombre) {
        List<ConceptoDTO> conceptos = this.conceptoService.findAllConceptos(id, nombre);
        return ResponseEntity.ok(conceptos);
    }


}
