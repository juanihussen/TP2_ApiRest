package com.example.Empleados.service.Jornadas;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequestDTO;
import com.example.Empleados.entity.JornadaLaboral;
import org.apache.coyote.BadRequestException;

import java.time.LocalDate;
import java.util.List;

public interface IJornadasService {

    public JornadaLaboralDTO  crearJornadaLaboral(JornadaRequestDTO requestDTO);

    /*
    List<JornadaLaboralDTO> findAllJornadas();

    List<JornadaLaboralDTO> findJornadasByNroDocumento(String nroDocumento);

    List<JornadaLaboralDTO> findJornadasAPartirDeFecha(String fechaDesde);

    List<JornadaLaboralDTO> findJornadasHastaFecha(String fechaHasta);

    List<JornadaLaboralDTO> findJornadasRangoFecha(String fechaDesde, String fechaHasta);

    List<JornadaLaboralDTO> findJornadasRangoFehcasNroDocumento(String fechaDesde, String fechaHasta,String nroDocumento);
    */


}
