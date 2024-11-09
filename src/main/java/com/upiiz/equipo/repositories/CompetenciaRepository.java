package com.upiiz.equipo.repositories;

import com.upiiz.equipo.entities.Competencia;
import com.upiiz.equipo.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

    // Métodos para buscar cliente por ID usando Query personalizada
    @Query("SELECT c FROM Competencia c WHERE c.id = :id")
    Competencia findCompetenciaById(@Param("id") Long id);

    // @Query("SELECT e FROM Equipo e")
    // List<Equipo> findAllEquipos();

    // Métodos para actualizar cliente

}
