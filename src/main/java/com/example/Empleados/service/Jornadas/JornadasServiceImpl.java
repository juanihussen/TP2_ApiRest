package com.example.Empleados.service.Jornadas;

import com.example.Empleados.dto.JornadaLaboralDTO;
import com.example.Empleados.dto.JornadaRequestDTO;
import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import com.example.Empleados.repository.jornadas.JornadasRepository;
import com.example.Empleados.validator.JornadaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public JornadaLaboralDTO crearJornadaLaboral(JornadaRequestDTO requestDTO) {
        Optional<Empleado> empleado = jornadaValidator.findEmpleadoById(requestDTO.getIdEmpleado());
        Optional<Concepto> concepto = jornadaValidator.findConceptoById(requestDTO.getIdConcepto());
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
        JornadaLaboral jornadaLaboral = requestDTO.toEntity(empleado.get(), concepto.get());
        jornadasRepository.save(jornadaLaboral);
        return JornadaLaboralDTO.toDTO(jornadaLaboral);
    }







    /*

    @Override
    public List<JornadaLaboralDTO> findAllJornadas() {
        return List.of();
    }

    @Override
    public List<JornadaLaboralDTO> findJornadasByNroDocumento(String nroDocumento) {
        return List.of();
    }

    //Estas 4 van dentro de una. Como se hizo en el Concepto. Con 4 if, con o no cada parametro.....NotRequired

    @Override
    public List<JornadaLaboralDTO> findJornadasAPartirDeFecha(String fechaDesde) {
        return List.of();
    }

    @Override
    public List<JornadaLaboralDTO> findJornadasHastaFecha(String fechaHasta) {
        return List.of();
    }

    @Override
    public List<JornadaLaboralDTO> findJornadasRangoFecha(String fechaDesde, String fechaHasta) {
        return List.of();
    }

    @Override
    public List<JornadaLaboralDTO> findJornadasRangoFehcasNroDocumento(String fechaDesde, String fechaHasta, String nroDocumento) {
        return List.of();
    }

     */
}
