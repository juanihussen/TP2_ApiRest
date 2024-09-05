package com.example.Empleados.repository.concepto;

import com.example.Empleados.entity.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto,Integer> {
    Optional<Concepto> findByNombre(String nombreConcepto);
}

