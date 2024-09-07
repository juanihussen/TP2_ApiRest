package com.example.Empleados.validator;

import com.example.Empleados.dto.JornadaRequest;
import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.entity.JornadaLaboral;
import com.example.Empleados.exceptions.CustomBadRequestException;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import com.example.Empleados.repository.jornadas.JornadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class JornadaValidator {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ConceptoRepository conceptoRepository;

    @Autowired
    private JornadasRepository jornadasRepository;


    public void validarCamposRequeridos(JornadaRequest requestDTO){
        if(requestDTO.getIdEmpleado() == null){
            throw new CustomBadRequestException("Es necesario que ingrese el id de algun empleado. ");
        }
        if (requestDTO.getIdConcepto() == null){
            throw new CustomBadRequestException("Es necesario que ingrese el id de algun concepto. ");
        }
        if (requestDTO.getFecha() == null){
            throw new CustomBadRequestException("Es necesario que ingrese una fecha. ");
        }
    }

    public Empleado findEmpleadoById(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if(empleado.isEmpty()){
            throw new NotFoundException("No existe el empleado ingresado.");
        }
        return empleado.get();

    }

    public Concepto findConceptoById(Integer id) {
        Optional<Concepto> concepto = conceptoRepository.findById(id);
        if(concepto.isEmpty()){
            throw new NotFoundException("No existe el concepto ingresado.");
        }
        return concepto.get();
    }

    public void validarTurnosSinHorasTrabajadas(JornadaRequest requestDTO) {

        if(requestDTO.getIdConcepto() != 3 && requestDTO.getHorasTrabajadas() == null){
            throw new CustomBadRequestException("hsTrabajadas es obligatorio para el concepto ingresado. ");
        }
    }

    public void validarHorasDiaLibre(JornadaRequest requestDTO) {
        Optional<Concepto> concepto = conceptoRepository.findById(requestDTO.getIdConcepto());
        if(concepto.get().getId() == 3 && (requestDTO.getHorasTrabajadas() != null)){
            throw new CustomBadRequestException("El concepto ingresado no requiere el ingreso de hsTrabajadas.");
        }
    }

    public void validarRangoHorasIngresadas(JornadaRequest requestDTO) {
        Optional<Concepto> concepto = conceptoRepository.findById(requestDTO.getIdConcepto());

        if(requestDTO.getIdConcepto() != 3) {
            if(concepto.get().getHsMAximo() < requestDTO.getHorasTrabajadas() || concepto.get().getHsMinimo() > requestDTO.getHorasTrabajadas()) {
                throw new CustomBadRequestException("El rango de horas que se puede cargar para este concepto es de minimo : "  +  concepto.get().getHsMinimo() + " y maximo : " + concepto.get().getHsMAximo() );
            }
        }
    }

    public void validarHorasTotalesDia(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        if(requestDTO.getIdConcepto() != 3){
            int horasRegistradas = jornadasRepository.sumarHorasTrabajadasPorEmpleadoYFecha(empleado.get().getId(), requestDTO.getFecha()).orElse(0);

            int totalHoras = horasRegistradas + requestDTO.getHorasTrabajadas();

            if (totalHoras > 14) {
                throw new CustomBadRequestException("Un empleado no puede cargar más de 14 horas trabajadas en un día.");
            }
        }
    }

    public void validarHorasSemanalesYMensuales(JornadaRequest requestDTO) {

        if(requestDTO.getIdConcepto() != 3){
            Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

            LocalDate fecha = requestDTO.getFecha();
            int horasIngresadas = requestDTO.getHorasTrabajadas();

            LocalDate comienzoSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate finalSemana = fecha.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            LocalDate comienzoMes = fecha.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate findMes = fecha.with(TemporalAdjusters.lastDayOfMonth());

            int horasSemanales = jornadasRepository.sumarHorasTrabajadasPorEmpleadoYSemana(empleado.get().getId(), comienzoSemana, finalSemana)
                    .orElse(0) + horasIngresadas;

            int horasMensuales = jornadasRepository
                    .sumarHorasTrabajadasPorEmpleadoYMes(empleado.get().getId(), comienzoMes, findMes)
                    .orElse(0) + horasIngresadas;

            if (horasSemanales > 52) {
                throw new CustomBadRequestException("El empleado ingresado supera las 52 horas semanales.");
            }
            if (horasMensuales > 190) {
                throw new CustomBadRequestException("El empleado ingresado supera las 190 horas mensuales.");
            }
        }

    }

    public void validarDiaLibreEnFecha(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        JornadaLaboral jornadaExistente = jornadasRepository.findByEmpleadoIdAndFecha(empleado.get().getId(), requestDTO.getFecha());

        if (jornadaExistente != null && "Día Libre".equals(jornadaExistente.getConcepto().getNombre())) {
            throw new CustomBadRequestException("El empleado ingresado cuenta con un día libre en esa fecha.");
        }
    }

    public void validarDiaLibreEnFechaLuegoDeTurno(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());
        JornadaLaboral jornadaExistente = jornadasRepository.findByEmpleadoIdAndFecha(empleado.get().getId(), requestDTO.getFecha());

        if(jornadaExistente != null) {
            if ((jornadaExistente.getConcepto().getId() == 1 || jornadaExistente.getConcepto().getId() == 2) && requestDTO.getIdConcepto() == 3) {
                throw new CustomBadRequestException("No se puede ingresar un Dia Libre luego de haber ingresado un turno previamente. ");
            }
        }

    }

    public void validarMaximoTurnosEnSemana(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        LocalDate fechaTurno = requestDTO.getFecha();
        LocalDate inicioSemana = fechaTurno.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = fechaTurno.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        long cantidadTurnosExtra = jornadasRepository.countTurnosExtraPorEmpleadoYSemana(empleado.get().getId(), inicioSemana, finSemana);

        long cantidadTurnosNormal = jornadasRepository.countTurnosNormalPorEmpleadoYSemana(empleado.get().getId(), inicioSemana, finSemana);

        if (cantidadTurnosExtra == 3) {
            throw new CustomBadRequestException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.");
        }
        if (cantidadTurnosNormal == 5) {
            throw new CustomBadRequestException("El empleado ingresado ya cuenta con 3 turnos normales esta semana.");
        }
    }

    public void validarMaximoDiasLibresEnSemana(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        LocalDate fechaTurno = requestDTO.getFecha();
        LocalDate inicioSemana = fechaTurno.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = fechaTurno.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        long cantidadDiasLibres = jornadasRepository.countDiasLibresPorEmpleadoYSemana(empleado.get().getId(), inicioSemana, finSemana);

        if (cantidadDiasLibres >= 2) {
            throw new CustomBadRequestException("El empleado no cuenta con más días libres esta semana.");
        }
    }


    public void validarMaximoDiasLibresEnMes(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        LocalDate fechaTurno = requestDTO.getFecha();
        LocalDate primerDiaDelMes = fechaTurno.withDayOfMonth(1);
        LocalDate ultimoDiaDelMes = fechaTurno.with(TemporalAdjusters.lastDayOfMonth());

        long cantidadDiasLibres = jornadasRepository.countDiasLibresPorEmpleadoYMes(empleado.get().getId(), primerDiaDelMes, ultimoDiaDelMes);

        if (cantidadDiasLibres >= 5) {
            throw new CustomBadRequestException("El empleado no cuenta con más días libres este mes.");
        }
    }

    public void validarMaximoEmpleadosPorConceptoYFecha(JornadaRequest requestDTO) {
        LocalDate fecha = requestDTO.getFecha();
        Optional<Concepto> concepto = conceptoRepository.findById(requestDTO.getIdConcepto());

        long cantidadEmpleados = jornadasRepository.countEmpleadosPorConceptoYFecha(concepto.get().getId(), fecha);

        if (cantidadEmpleados >= 2) {
            throw new CustomBadRequestException("Ya existen 2 empleados registrados para este concepto en la fecha ingresada.");
        }
    }

    public void validarJornadaDuplicadaPorEyC(JornadaRequest requestDTO) {
        Optional<Empleado> empleado = empleadoRepository.findById(requestDTO.getIdEmpleado());

        Optional<Concepto> concepto = conceptoRepository.findById(requestDTO.getIdConcepto());

        LocalDate fechaTurno = requestDTO.getFecha();

        boolean jornadaDuplicada = jornadasRepository.existsJornadaPorEmpleadoYConceptoYFecha(empleado.get().getId(), concepto.get().getId(), fechaTurno);

        if (jornadaDuplicada) {
            throw new CustomBadRequestException("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }
    }

    public void validarFechaDesdeMenoraFechaHasta(LocalDate fechaDesde, LocalDate fechaHasta) {
        if(fechaDesde != null && fechaHasta != null) {
            if(fechaDesde.isAfter(fechaHasta)) {
                throw new CustomBadRequestException("El campo ‘fechaDesde’ no puede ser mayor que ‘fechaHasta’.");
            }
        }
    }

    public void validarTipoDatoNroDocumento(String nroDocumento) {
        if (!Pattern.matches("\\d+", nroDocumento)) {
            throw new CustomBadRequestException("El campo 'nroDocumento' solo puede contener números enteros.");
        }
    }









}
