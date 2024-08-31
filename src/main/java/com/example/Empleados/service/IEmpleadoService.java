package com.example.Empleados.service;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {

    // List<Empleado> findAllEmpleados();

    // Optional<Empleado> findEmpleadosById(Long id);

    void altaEmpleado(EmpleadoDTO empleadoDTO) throws BadRequestException;

    // void deleteEmpleadoById(Long id);

}
