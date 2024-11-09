package com.upiiz.equipo.repositories;

import com.upiiz.equipo.entities.Equipo;
import com.upiiz.equipo.entities.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {

    // Métodos para buscar cliente por ID usando Query personalizada
    @Query("SELECT l FROM Liga l WHERE l.id = :id")
    Liga findLigaById(@Param("id") Long id);

    // Métodos para actualizar liga

}
