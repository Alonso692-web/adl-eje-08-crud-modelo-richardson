package com.upiiz.equipo.controllers;

import com.upiiz.equipo.entities.Competencia;
import com.upiiz.equipo.responses.CustomResponseCompetencia;
import com.upiiz.equipo.services.CompetenciaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/competencias")
@Tag(
        name = "Competencias"
)
public class CompetenciaController {

    @Autowired
    CompetenciaService competenciaService;

    @GetMapping
    public ResponseEntity<CustomResponseCompetencia<List<Competencia>>> getCompetencia() {
        List<Competencia> competencias = new ArrayList<>();
        Link allClientsLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            competencias = competenciaService.obtenerTodos();
            if (!competencias.isEmpty()) {
                CustomResponseCompetencia<List<Competencia>> response = new CustomResponseCompetencia<>(1, "Competencias encontrados", competencias, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseCompetencia<>(6, "Competencias no encontrados", competencias, links));
            }
        } catch (Exception e) {
            CustomResponseCompetencia<List<Competencia>> response = new CustomResponseCompetencia<>(8, "Error interno de servidor", competencias, links);
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener competencias por Id
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseCompetencia<Competencia>> getCompetenciaById(@PathVariable Long id) {
        Optional<Competencia> competencias = null;
        CustomResponseCompetencia<Competencia> response = null;
        Link allCompetenciasLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allCompetenciasLink);
        try {
            competencias = competenciaService.obtenerCompetenciaPorId(id);
            if (competencias.isPresent()) {
                response = new CustomResponseCompetencia<>(1, "Competencia encontrado", competencias.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseCompetencia<>(0, "Competencia no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseCompetencia<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Guardar Competencia
    @PostMapping
    public ResponseEntity<CustomResponseCompetencia<Competencia>> crearCompetencia(@RequestBody Competencia competencias) {
        Link allClientsLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            Competencia competenciasEntity = competenciaService.guardarCompetencia(competencias);
            if (competenciasEntity != null) {
                CustomResponseCompetencia<Competencia> response = new CustomResponseCompetencia<>(1, "Competencias creado", competenciasEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseCompetencia<>(6, "Competencias no encontrados", competenciasEntity, links));
            }
        } catch (Exception e) {
            CustomResponseCompetencia<Competencia> response = new CustomResponseCompetencia<>(8, "Error interno de servidor", null, links);
            System.out.println("Error en Post Competencia: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar Competencia
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseCompetencia<Competencia>> deleteCompetenciaById(@PathVariable Long id) {
        Optional<Competencia> competencias = null;
        CustomResponseCompetencia<Competencia> response = null;
        Link allCompetenciasLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allCompetenciasLink);

        try {
            competencias = competenciaService.obtenerCompetenciaPorId(id);
            if (competencias.isPresent()) {
                competenciaService.deleteCompetencia(id);
                response = new CustomResponseCompetencia<>(1, "Competencia eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseCompetencia<>(0, "Competencia no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseCompetencia<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar competencias por id
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseCompetencia<Competencia>> updateCompetencia(@RequestBody Competencia competencias, @PathVariable Long id) {
        Link allCompetenciasLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allCompetenciasLink);
        try {
            competencias.setId(id);
            if (competenciaService.obtenerCompetenciaPorId(id).isPresent()) {
                Competencia competenciasEntity = competenciaService.actualizarCompetencia(competencias);
                CustomResponseCompetencia<Competencia> response = new CustomResponseCompetencia<>(1, "Competencia actualizado", competenciasEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                CustomResponseCompetencia<Competencia> response = new CustomResponseCompetencia<>(0, "Competencia no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            CustomResponseCompetencia<Competencia> response = new CustomResponseCompetencia<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
