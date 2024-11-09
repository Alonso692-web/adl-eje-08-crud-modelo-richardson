package com.upiiz.equipo.services;

import com.upiiz.equipo.entities.Entrenador;
import com.upiiz.equipo.entities.Equipo;
import com.upiiz.equipo.repositories.EntrenadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {

    @Autowired
    EntrenadorRepository entrenadorRepository;

    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Entrenador guardarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Optional<Entrenador> obtenerEntrenadorPorId(Long id) {
        return Optional.ofNullable(entrenadorRepository.findEntrenadorBy(id));
    }

    @Transactional
    public void deleteEntrenador(Long id){
        entrenadorRepository.deleteById(id);
    }

    public Entrenador actualizarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

}
