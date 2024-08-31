package com.example.Empleados.repository;

import com.example.Empleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

    boolean existsByNroDocumento(Long nroDocumento);

    boolean existsByEmail(String email);

}
