package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/graph")
public class GraphController {
    final
    GraphRepository graphRepository;

    public GraphController(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    @Operation(summary = "Listar todas las definiciones de gráficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de definiciones de gráficos",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay definiciones de gráficos en la base de datos",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<GraphDefinition>> getAllGraphs() {
        List<GraphDefinition> result = graphRepository.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Detalle de definición de gráfico por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definición de grafico con Id encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado definición de gráfico con el Id especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/id/{id}")
    public ResponseEntity<GraphDefinition> getGraphById(@PathVariable("id") Long id) {
        GraphDefinition result = graphRepository.findById(id).orElse(null);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Detalle de definición de gráfico por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definición de grafico con nombre encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado definición de gráfico con el nombre especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/name/{name}")
    public ResponseEntity<GraphDefinition> getGraphByName(@PathVariable("name") String name) { //TODO: Must return list???
        GraphDefinition result = graphRepository.findByName(name);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listado de definiciones de gráfico por id de usuario creador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definiciones de grafico creados por el id con el nombre indicado encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "404", description = "No se han encontrado definiciones de gráfico creados por usuario especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<GraphDefinition>> getGraphsByCreationUserName(@PathVariable("userId") Long adminId) {
        List<GraphDefinition> result = graphRepository.findByCreationAdmin_AdminId(adminId);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listado de definiciones de gráfico por id de usuario que actualizó por última vez")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definiciones de grafico actualizadas por el id con el nombre indicado encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "404", description = "No se han encontrado definiciones de gráfico actualizados por usuario especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/updater/{adminId}")
    public ResponseEntity<List<GraphDefinition>> getGraphsByUpdateUserName(@PathVariable("adminId") Long adminId) {
        List<GraphDefinition> result = graphRepository.findByUpdateAdmin_AdminId(adminId);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Creación de nueva definición de gráfico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Definición de gráfico creada exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<GraphDefinition> createGraph(@RequestBody GraphDefinition graphDefinition) {
        GraphDefinition result = graphRepository.save(graphDefinition);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
