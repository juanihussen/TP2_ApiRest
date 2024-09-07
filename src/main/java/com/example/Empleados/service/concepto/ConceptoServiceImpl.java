package com.example.Empleados.service.concepto;

import com.example.Empleados.dto.ConceptoDTO;
import com.example.Empleados.dto.EmpleadoDTO;
import com.example.Empleados.entity.Concepto;
import com.example.Empleados.entity.Empleado;
import com.example.Empleados.exceptions.NotFoundException;
import com.example.Empleados.repository.concepto.ConceptoRepository;
import com.example.Empleados.repository.empleado.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConceptoServiceImpl implements IConceptoService {

    @Autowired
    ConceptoRepository repository;

    @Override
    public List<ConceptoDTO> findAllConceptos(Integer id, String nombre) {
        List<Concepto> conceptos = new ArrayList<>();

        if (id != null && nombre == null) {
            Optional<Concepto> concepto = this.repository.findById(id);
            if (concepto.isPresent()) {
                conceptos.add(concepto.get());
            }
        } else if (id == null && nombre != null) {
            Optional<Concepto> concepto = this.repository.findByNombre(nombre);
            if (concepto.isPresent()) {
                conceptos.add(concepto.get());
            }
        } else if (id == null && nombre == null){
            conceptos = this.repository.findAll();
        }else {
            Optional<Concepto> conceptoNombre = this.repository.findByNombre(nombre);
            Optional<Concepto> conceptoId = this.repository.findById(id);
            if (conceptoNombre.isPresent() && conceptoId.isPresent()) {
                if(conceptoNombre.equals(conceptoId)){
                    conceptos.add(conceptoId.get());
                }
            }
        }

        return conceptos.stream().map(Concepto::toDTO).collect(Collectors.toList());
    }





}
