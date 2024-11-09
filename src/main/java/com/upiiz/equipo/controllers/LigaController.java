package com.upiiz.equipo.controllers;

import com.upiiz.equipo.entities.Liga;
import com.upiiz.equipo.entities.Liga;
import com.upiiz.equipo.responses.CustomResponseLiga;
import com.upiiz.equipo.responses.CustomResponseLiga;
import com.upiiz.equipo.services.LigaService;
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
@RequestMapping("/api/v1/liga")
@Tag(
        name = "Liga"
)
public class LigaController {

    @Autowired
    LigaService ligaService;

    @GetMapping
    public ResponseEntity<CustomResponseLiga<List<Liga>>> getLigas() {
        List<Liga> ligas = new ArrayList<>();
        Link allLigasLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allLigasLink);
        try {
            ligas = ligaService.obtenerTodos();
            if (!ligas.isEmpty()) {
                CustomResponseLiga<List<Liga>> response = new CustomResponseLiga<>(1, "Ligas encontrados", ligas, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseLiga<>(6, "Ligas no encontrados", ligas, links));
            }
        } catch (Exception e) {
            CustomResponseLiga<List<Liga>> response = new CustomResponseLiga<>(8, "Error interno de servidor", ligas, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseLiga<Liga>> getLigaById(@PathVariable Long id) {
        Optional<Liga> liga = null;
        CustomResponseLiga<Liga> response = null;
        Link allLigasLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allLigasLink);
        try {
            liga = ligaService.obtenerLigaPorId(id);
            if (liga.isPresent()) {
                response = new CustomResponseLiga<>(1, "Liga encontrado", liga.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseLiga<>(0, "Liga no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseLiga<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Guardar Liga
    @PostMapping
    public ResponseEntity<CustomResponseLiga<Liga>> crearLiga(@RequestBody Liga liga) {
        Link allClientsLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allClientsLink);
        try {
            Liga ligaEntity = ligaService.guardarLiga(liga);
            if (ligaEntity != null) {
                CustomResponseLiga<Liga> response = new CustomResponseLiga<>(1, "Ligas creado", ligaEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseLiga<>(6, "Ligas no encontrados", ligaEntity, links));
            }
        } catch (Exception e) {
            CustomResponseLiga<Liga> response = new CustomResponseLiga<>(8, "Error interno de servidor", null, links);
            System.out.println("Error en Post Liga: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar Liga
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseLiga<Liga>> deleteLigaById(@PathVariable Long id) {
        Optional<Liga> liga = null;
        CustomResponseLiga<Liga> response = null;
        Link allLigasLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allLigasLink);

        try {
            liga = ligaService.obtenerLigaPorId(id);
            if (liga.isPresent()) {
                ligaService.deleteLiga(id);
                response = new CustomResponseLiga<>(1, "Liga eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponseLiga<>(0, "Liga no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponseLiga<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // Actualizar liga por id
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseLiga<Liga>> updateLiga(@RequestBody Liga liga, @PathVariable Long id) {
        Link allLigasLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allLigasLink);
        try {
            liga.setId(id);
            if (ligaService.obtenerLigaPorId(id).isPresent()) {
                Liga ligaEntity = ligaService.actualizarEquipo(liga);
                CustomResponseLiga<Liga> response = new CustomResponseLiga<>(1, "Liga actualizado", ligaEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else{
                CustomResponseLiga<Liga> response = new CustomResponseLiga<>(0, "Liga no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch (Exception e) {
            CustomResponseLiga<Liga> response = new CustomResponseLiga<>(500, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}