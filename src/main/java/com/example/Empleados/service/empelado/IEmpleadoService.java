package com.example.Empleados.service.empelado;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IEmpleadoService {

    List<EmpleadoDTO> findAllEmpleados();

    EmpleadoDTO findEmpleadoById(Long id);

    Empleado altaEmpleado(EmpleadoDTO empleadoDTO) throws BadRequestException;

    EmpleadoDTO updateEmpleado(EmpleadoDTO empleadoDTO,Long id) throws BadRequestException;

    void deleteEmpleadoById(Long id);

}
