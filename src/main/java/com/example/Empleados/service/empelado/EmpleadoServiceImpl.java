package com.example.Empleados.service.empelado;

import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.exceptions.CustomBadRequestException;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import com.example.Empleados.repository.jornadas.JornadasRepository;
import com.example.Empleados.validator.EmpleadoValidator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    EmpleadoRepository repository;
    @Autowired
    JornadasRepository jornadasRepository;


    @Autowired
    EmpleadoValidator validator;

    @Override
    public List<EmpleadoDTO> findAllEmpleados() {
        List<Empleado> empleados = this.repository.findAll();
        return empleados.stream().map(Empleado::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO findEmpleadoById(Long id) {
        Optional<Empleado> empleado = this.repository.findById(id);
        if (empleado.isPresent()) {
            return empleado.get().toDTO();
        } else {
            throw new NotFoundException("No se encontró el empleado con Id: " + id);
        }
    }

    @Override
    public Empleado altaEmpleado(EmpleadoDTO empleadoDTO) throws BadRequestException {
        Empleado empleado = empleadoDTO.toEntity();
        validarEmpleado(empleado);
        this.repository.save(empleado);
        return empleado;
    }

    @Override
    public EmpleadoDTO updateEmpleado(EmpleadoDTO empleadoActualizadoDTO, Long id) throws BadRequestException {
        Empleado empleadoActualizado = empleadoActualizadoDTO.toEntity();

        EmpleadoDTO empleadoEncontradoDTO = findEmpleadoById(id);
        Empleado empleadoEncontrado = empleadoEncontradoDTO.toEntity();

        if (!empleadoActualizado.getEmail().equals(empleadoEncontrado.getEmail())) {
            validator.validarEmailUnico(empleadoActualizado.getEmail());
            empleadoEncontrado.setEmail(empleadoActualizado.getEmail());
        }
        validator.validarCamposVacios(empleadoActualizado.getNroDocumento(), empleadoActualizado.getNombre(), empleadoActualizado.getApellido(), empleadoActualizado.getEmail(), empleadoActualizado.getFechaNacimiento(), empleadoActualizado.getFechaIngreso());
        if (!empleadoActualizado.getNroDocumento().equals(empleadoEncontrado.getNroDocumento())) {
            validator.validarDocumentoUnico(empleadoActualizado.getNroDocumento());
            empleadoEncontrado.setNroDocumento(empleadoActualizado.getNroDocumento());
        }

        validator.validarFechas(empleadoActualizado.getFechaIngreso(), empleadoActualizado.getFechaNacimiento());
        validator.validarEdad(empleadoActualizado.getFechaNacimiento());
        validator.validarSoloLetrasEnNombreYApellido("nombre", empleadoActualizado.getNombre());
        validator.validarSoloLetrasEnNombreYApellido("apellido", empleadoActualizado.getApellido());

        empleadoEncontrado.setNombre(empleadoActualizado.getNombre());
        empleadoEncontrado.setApellido(empleadoActualizado.getApellido());
        empleadoEncontrado.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleadoEncontrado.setFechaIngreso(empleadoActualizado.getFechaIngreso());

        empleadoEncontrado = this.repository.save(empleadoEncontrado);

        return empleadoEncontrado.toDTO();
    }


    @Override
    public void deleteEmpleadoById(Long id) {
        Optional<Empleado> empleado = repository.findById(id);

        if (!empleado.isPresent()) {
            throw new NotFoundException("No se encontró el empleado con Id: " + id);
        }
        if (jornadasRepository.existsByEmpleadoId(id)) {
            throw new CustomBadRequestException("No es posible eliminar un empleado con jornadas asociadas.");
        }

        repository.deleteById(id);
    }



    public void validarEmpleado(Empleado empleado) throws BadRequestException {
        validator.validarCamposVacios(empleado.getNroDocumento(), empleado.getNombre(), empleado.getApellido(), empleado.getEmail(), empleado.getFechaNacimiento(), empleado.getFechaIngreso());
        validator.validarEmailUnico(empleado.getEmail());
        validator.validarDocumentoUnico(empleado.getNroDocumento());
        validator.validarFechas(empleado.getFechaIngreso(),empleado.getFechaNacimiento());
        validator.validarEdad(empleado.getFechaNacimiento());
        validator.validarSoloLetrasEnNombreYApellido("nombre",empleado.getNombre());
        validator.validarSoloLetrasEnNombreYApellido("apellido",empleado.getApellido());
    }

}
