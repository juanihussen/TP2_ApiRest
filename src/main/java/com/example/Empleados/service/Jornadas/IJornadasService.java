package com.example.Empleados.service.Jornadas;

import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequest;
import com.example.Empleados.entity.JornadaLaboral;

import java.time.LocalDate;
import java.util.List;

public interface IJornadasService {

    JornadaLaboralDTO  crearJornadaLaboral(JornadaRequest requestDTO);

    List<JornadaLaboralDTO> findJornadas(String nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta);

}
