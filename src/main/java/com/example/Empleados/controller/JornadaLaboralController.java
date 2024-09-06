package com.example.Empleados.controller;

import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequest;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.service.Jornadas.IJornadasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {

    @Autowired
    private IJornadasService jornadasService;

    @PostMapping
    public ResponseEntity<JornadaLaboralDTO> crearJornadaLaboral(@RequestBody @Valid JornadaRequest requestDTO) {
        JornadaLaboralDTO jornadaLaboralDTO = jornadasService.crearJornadaLaboral(requestDTO);
        return new ResponseEntity<>(jornadaLaboralDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JornadaLaboralDTO>> obtenerJornadas(
            @RequestParam(required = false) String nroDocumento,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta) {

        List<JornadaLaboralDTO> jornadas = jornadasService.findJornadas(nroDocumento, fechaDesde, fechaHasta);
        return ResponseEntity.ok(jornadas);
    }
}
