package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/graph")
public class GraphAPIController {
    private static final Logger logger = LoggerFactory.getLogger(GraphAPIController.class);
    private final GraphRepository graphRepository;

    @Autowired
    public GraphAPIController(GraphRepository graphRepository) {
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
    public ResponseEntity<List<GraphDefinition>> getGraphByName(@PathVariable("name") String name) {
        List<GraphDefinition> result = graphRepository.findByName(name);
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

    @GetMapping("/variables/{graphId}")
    public ResponseEntity<List<String>> getGraphVariablesAsString(@PathVariable("graphId") Long graphId) {
        GraphDefinition graph = graphRepository.findById(graphId).orElse(null);
        if (graph == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String variablesComment = graph.getSvg();
        String variablesCommentText = variablesComment.substring(variablesComment.indexOf("<!--@@@") + 7, variablesComment.indexOf("@@@-->"));
        String[] variablesArray = variablesCommentText.split(";");
        List<String> result = new ArrayList<>();
        for (String variable : variablesArray) {
            result.add(variable.split("=")[0]);
        }
        logger.warn("Variables: " + result);
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

    @Operation(summary = "Actualizar una definición de gráfico mediante PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definición de gráfico actualizada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphContent.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado la definición de gráfico con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez los Id no corresponden?)",
                    content = @Content)})

    @PutMapping("/update/{id}")
    public ResponseEntity<GraphDefinition> updateDefinition(@PathVariable("id") Long definitionId, @RequestBody GraphDefinition definition) {
        if (graphRepository.findById(definitionId).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!definitionId.equals(definition.getGraphId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        graphRepository.save(definition);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Borrar una definición de gráfico mediante DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Definición de gráfico con id indicado borrada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphDefinition.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado la definición de gráfico con el id indicado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente (tal vez Ids no se corresponden?)",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GraphDefinition> deleteDefinition(@PathVariable("id") Long id) {
        if (graphRepository.findById(id).orElse(null) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        graphRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
