package com.example.Empleados.service;

import com.example.Empleados.entity.Empleado;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {

    List<Empleado> findAllEmpleados();

    Optional<Empleado> findEmpleadosById(Long id);

    void altaEmpleado(Empleado empleado);

    void deleteEmpleadoById(Long id);

}
