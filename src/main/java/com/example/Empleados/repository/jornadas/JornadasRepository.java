package com.example.Empleados.repository.jornadas;

import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JornadasRepository extends JpaRepository<JornadaLaboral,Integer> {
    List<JornadaLaboral> findAll();

    Boolean existsByEmpleadoId(Long empleadoId);

    JornadaLaboral findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);

    @Query("SELECT SUM(j.horasTrabajadas) FROM JornadaLaboral j WHERE j.empleado.id = :idEmpleado AND j.fecha = :fecha")
    Optional<Integer> sumarHorasTrabajadasPorEmpleadoYFecha(@Param("idEmpleado") Long idEmpleado, @Param("fecha") LocalDate fecha);

    @Query("SELECT SUM(j.horasTrabajadas) FROM JornadaLaboral j WHERE j.empleado.id = :idEmpleado AND j.fecha BETWEEN :startOfWeek AND :endOfWeek")
    Optional<Integer> sumarHorasTrabajadasPorEmpleadoYSemana(@Param("idEmpleado") Long idEmpleado, @Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT SUM(j.horasTrabajadas) FROM JornadaLaboral j WHERE j.empleado.id = :idEmpleado AND j.fecha BETWEEN :startOfMonth AND :endOfMonth")
    Optional<Integer> sumarHorasTrabajadasPorEmpleadoYMes(@Param("idEmpleado") Long idEmpleado, @Param("startOfMonth") LocalDate startOfMonth, @Param("endOfMonth") LocalDate endOfMonth);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j " + "WHERE j.empleado.id = :empleadoId " + "AND j.fecha BETWEEN :startOfWeek AND :endOfWeek " + "AND j.concepto.nombre = 'Turno Extra'")
    long countTurnosExtraPorEmpleadoYSemana(@Param("empleadoId") Long empleadoId, @Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j " + "WHERE j.empleado.id = :empleadoId " + "AND j.fecha BETWEEN :startOfWeek AND :endOfWeek " + "AND j.concepto.nombre = 'Turno Normal'")
    long countTurnosNormalPorEmpleadoYSemana(@Param("empleadoId") Long empleadoId, @Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j " + "WHERE j.empleado.id = :empleadoId " + "AND j.fecha BETWEEN :inicioSemana AND :finSemana " + "AND j.concepto.nombre = 'Día Libre'")
    long countDiasLibresPorEmpleadoYSemana(@Param("empleadoId") Long empleadoId, @Param("inicioSemana") LocalDate inicioSemana, @Param("finSemana") LocalDate finSemana);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j " + "WHERE j.empleado.id = :empleadoId " + "AND j.fecha BETWEEN :primerDiaDelMes AND :ultimoDiaDelMes " + "AND j.concepto.nombre = 'Día Libre'")
    long countDiasLibresPorEmpleadoYMes(@Param("empleadoId") Long empleadoId, @Param("primerDiaDelMes") LocalDate primerDiaDelMes, @Param("ultimoDiaDelMes") LocalDate ultimoDiaDelMes);

    @Query("SELECT COUNT(j) FROM JornadaLaboral j " + "WHERE j.concepto.id = :conceptoId " + "AND j.fecha = :fechaTurno")
    Long countEmpleadosPorConceptoYFecha(@Param("conceptoId") Long conceptoId, @Param("fechaTurno") LocalDate fechaTurno);

    @Query("SELECT COUNT(j) > 0 FROM JornadaLaboral j " + "WHERE j.empleado.id = :empleadoId " + "AND j.concepto.id = :conceptoId " + "AND j.fecha = :fechaTurno")
    boolean existsJornadaPorEmpleadoYConceptoYFecha(@Param("empleadoId") Long empleadoId, @Param("conceptoId") Long conceptoId, @Param("fechaTurno") LocalDate fechaTurno);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.fecha BETWEEN :fechaDesde AND :fechaHasta")
    List<JornadaLaboral> findAllByFechaBetween(@Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.empleado.nroDocumento = :nroDocumento")
    List<JornadaLaboral> findAllByEmpleadoNroDocumento(@Param("nroDocumento") String nroDocumento);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.empleado.nroDocumento = :nroDocumento " + "AND j.fecha BETWEEN :fechaDesde AND :fechaHasta")
    List<JornadaLaboral> findAllByEmpleadoNroDocumentoAndFechaBetween(@Param("nroDocumento") String nroDocumento, @Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.fecha >= :fechaDesde")
    List<JornadaLaboral> findAllByFechaDesde(@Param("fechaDesde") LocalDate fechaDesde);

    @Query("SELECT j FROM JornadaLaboral j WHERE j.fecha <= :fechaHasta")
    List<JornadaLaboral> findAllByFechaHasta(@Param("fechaHasta") LocalDate fechaHasta);

    @Modifying
    @Query("DELETE FROM JornadaLaboral j WHERE j.empleado.id = :empleadoId")
    void deleteByEmpleadoId(@Param("empleadoId") Long empleadoId);

}
