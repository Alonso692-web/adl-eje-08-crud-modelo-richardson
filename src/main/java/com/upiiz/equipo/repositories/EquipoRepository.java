package com.upiiz.equipo.repositories;

import com.upiiz.equipo.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    // Métodos para buscar cliente por ID usando Query personalizada
    @Query("SELECT e FROM Equipo e WHERE e.id = :id")
    Equipo findEquipoById(@Param("id") Long id);

    // @Query("SELECT e FROM Equipo e")
    // List<Equipo> findAllEquipos();

    // Métodos para actualizar cliente

}
