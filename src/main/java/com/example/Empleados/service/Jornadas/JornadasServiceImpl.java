package com.example.Empleados.service.Jornadas;

import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequest;
import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import com.example.Empleados.repository.jornadas.JornadasRepository;
import com.example.Empleados.validator.JornadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadasServiceImpl implements IJornadasService{

    @Autowired
    EmpleadoRepository repositoryEmpleados;

    @Autowired
    ConceptoRepository repositoryConceptos;

    @Autowired
    JornadasRepository jornadasRepository;

    @Autowired
    JornadaValidator jornadaValidator;

    @Override
    public JornadaLaboralDTO crearJornadaLaboral(JornadaRequest requestDTO) {
        Empleado empleado = jornadaValidator.findEmpleadoById(requestDTO.getIdEmpleado());
        Concepto concepto = jornadaValidator.findConceptoById(requestDTO.getIdConcepto());
        validacionJornada(requestDTO);
        JornadaLaboral jornadaLaboral = requestDTO.toEntity(empleado, concepto);
        jornadasRepository.save(jornadaLaboral);
        return JornadaLaboralDTO.toDTO(jornadaLaboral);
    }

    @Override
    public List<JornadaLaboralDTO> findJornadas(String nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<JornadaLaboral> jornadas;

        if (nroDocumento != null && fechaDesde != null && fechaHasta != null) {
            jornadaValidator.validarTipoDatoNroDocumento(nroDocumento);
            jornadaValidator.validarFechaDesdeMenoraFechaHasta(fechaDesde,fechaHasta);
            jornadas = jornadasRepository.findAllByEmpleadoNroDocumentoAndFechaBetween(nroDocumento, fechaDesde, fechaHasta);
        } else if (nroDocumento != null) {
            jornadaValidator.validarTipoDatoNroDocumento(nroDocumento);
            jornadas = jornadasRepository.findAllByEmpleadoNroDocumento(nroDocumento);
        } else if (fechaDesde != null || fechaHasta != null) {
            if (fechaDesde != null && fechaHasta != null) {
                jornadaValidator.validarFechaDesdeMenoraFechaHasta(fechaDesde,fechaHasta);
                jornadas = jornadasRepository.findAllByFechaBetween(fechaDesde, fechaHasta);
            } else if (fechaDesde != null) {
                jornadas = jornadasRepository.findAllByFechaDesde(fechaDesde);
            } else {
                jornadas = jornadasRepository.findAllByFechaHasta(fechaHasta);
            }
        } else {
            jornadas = jornadasRepository.findAll();
        }

        return jornadas.stream()
                .filter(jornada -> jornada.getHorasTrabajadas() != null)
                .map(JornadaLaboralDTO::toDTO)
                .collect(Collectors.toList());
    }


    public void validacionJornada(JornadaRequest requestDTO) {
        jornadaValidator.validarDiaLibreEnFechaLuegoDeTurno(requestDTO);
        jornadaValidator.validarCamposRequeridos(requestDTO);
        jornadaValidator.validarTurnosSinHorasTrabajadas(requestDTO);
        jornadaValidator.validarHorasDiaLibre(requestDTO);
        jornadaValidator.validarRangoHorasIngresadas(requestDTO);
        jornadaValidator.validarHorasTotalesDia(requestDTO);
        jornadaValidator.validarHorasSemanalesYMensuales(requestDTO);
        jornadaValidator.validarDiaLibreEnFecha(requestDTO);
        jornadaValidator.validarMaximoTurnosEnSemana(requestDTO);
        jornadaValidator.validarMaximoDiasLibresEnSemana(requestDTO);
        jornadaValidator.validarMaximoDiasLibresEnMes(requestDTO);
        jornadaValidator.validarMaximoEmpleadosPorConceptoYFecha(requestDTO);
        jornadaValidator.validarJornadaDuplicadaPorEyC(requestDTO);
    }
}
