package com.upiiz.equipo.services;

import com.upiiz.equipo.entities.Competencia;
import com.upiiz.equipo.repositories.CompetenciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenciaService {

    @Autowired
    CompetenciaRepository competenciaRepository;

    public List<Competencia> obtenerTodos() {
        return competenciaRepository.findAll();
    }

    public Competencia guardarCompetencia(Competencia competencia) {
        return competenciaRepository.save(competencia);
    }

    public Optional<Competencia> obtenerCompetenciaPorId(Long id) {
        return Optional.ofNullable(competenciaRepository.findCompetenciaById(id));
    }

    @Transactional
    public void deleteCompetencia(Long id) {
        competenciaRepository.deleteById(id);
    }

    public Competencia actualizarCompetencia(Competencia equipo) {
        return competenciaRepository.save(equipo);
    }

}
