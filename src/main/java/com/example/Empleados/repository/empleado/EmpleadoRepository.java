package com.example.Empleados.repository.empleado;

import com.example.Empleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

    boolean existsByNroDocumento(Integer nroDocumento);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean deleteEmpleadoById(Long id);

}
