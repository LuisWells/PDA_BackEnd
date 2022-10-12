package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.model.GraphLink;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import com.duberlyguarnizo.designartifacts.util.SvgParser;
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
@RequestMapping("api/link")
public class LinkController {
    private final LinkRepository linkRepository;
    private final ContentRepository contentRepository;
    private final GraphRepository graphRepository;

    public LinkController(LinkRepository linkRepository, ContentRepository contentRepository, GraphRepository graphRepository) {
        this.linkRepository = linkRepository;
        this.contentRepository = contentRepository;
        this.graphRepository = graphRepository;
    }

    @Operation(summary = "Listado de todos los links")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de links encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphLink.class))}),
            @ApiResponse(responseCode = "204", description = "Solicitud exitosa pero no hay links",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/all")
    public ResponseEntity<List<GraphLink>> getAllLinks() {
        List<GraphLink> result = linkRepository.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listado de links por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Link con el id encontrado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphLink.class))}),
            @ApiResponse(responseCode = "404", description = "No hay link con el Id especificado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/id/{id}")
    public ResponseEntity<GraphLink> getLinkById(@PathVariable("id") Long id) {
        GraphLink result = linkRepository.findById(id).orElse(null);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Listado de links por ruta en la url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Link con la ruta especificada encontrada",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphLink.class))}),
            @ApiResponse(responseCode = "404", description = "No hay link con la ruta especificada",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping("/path/{path}")
    public ResponseEntity<GraphLink> getLinkByPath(@PathVariable("path") String path) {
        GraphLink result = linkRepository.findByPath(path);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Creación de nuevo link")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Link creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphLink.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @PostMapping("/create")
    public ResponseEntity<GraphLink> createLink(@RequestBody GraphLink graphLink) {
        GraphLink result = linkRepository.save(graphLink);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Imagen en SVG según definición de gráfico y contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gráfico retornado",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GraphLink.class))}),
            @ApiResponse(responseCode = "400", description = "Error en la petición desde el cliente",
                    content = @Content)})
    @GetMapping(value = "/svg/{graphId}/{contentId}")
    public @ResponseBody ResponseEntity<String> getSvgImage(@PathVariable("graphId") Long graphId, @PathVariable("contentId") Long contentId) {
        GraphContent content = contentRepository.findById(contentId).orElse(null);
        String contentString = content == null ? "" : content.getContentJson();
        GraphDefinition graphDefinition = graphRepository.findById(graphId).orElse(null);
        String graphDefinitionString = graphDefinition == null ? "" : graphDefinition.getSvg();
        SvgParser svgParser = new SvgParser(graphDefinitionString);
        return new ResponseEntity<>(svgParser.parse(contentString), HttpStatus.OK);
    }

}
