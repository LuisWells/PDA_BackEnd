package com.duberlyguarnizo.designartifacts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log
public class SvgParser {
    private static final String svgContent = """
                            <!--@@@fortalezas=(75,20,32); debilidades=(275,20,32) @@@-->
                            <?xml version="1.0" encoding="utf-8"?>
                            <svg viewBox="0 0 500 500" xmlns="http://www.w3.org/2000/svg">
                              <rect x="50" y="50" width="200" height="150" style="stroke: rgb(0, 0, 0); fill: none;"/>
                              <rect x="250" y="50" width="200" height="150" style="stroke: rgb(0, 0, 0); fill: none;"/>
                              <rect x="50" y="200" width="200" height="150" style="stroke: rgb(0, 0, 0); fill: none;"/>
                              <rect x="250" y="200" width="200" height="150" style="stroke: rgb(0, 0, 0); fill: none;"/>
                              <g transform="matrix(1, 0, 0, 1, -100, -185)">
                                <rect x="150" y="235" width="200" height="30" style="stroke: rgb(0, 0, 0); fill: rgb(241, 241, 241);"/>
                                <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 14px; white-space: pre;" x="217.323" y="254.853">Fortalezas</text>
                              </g>
                              <g transform="matrix(1, 0, 0, 1, -0.723999, -185)">
                                <rect x="250.724" y="235" width="200" height="30" style="stroke: rgb(0, 0, 0); fill: rgb(241, 241, 241);"/>
                                <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 14px; white-space: pre;" x="304.805" y="254.853">Oportunidades</text>
                              </g>
                              <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 10px; white-space: pre;" x="75" y="100">@fortalezas@</text>
                              <g>
                                <rect x="50" y="200" width="200" height="30" style="stroke: rgb(0, 0, 0); fill: rgb(241, 241, 241);"/>
                                <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 14px; white-space: pre;" x="112.53" y="219.372">Debilidades</text>
                              </g>
                              <g>
                                <rect x="250" y="200" width="200" height="30" style="stroke: rgb(0, 0, 0); fill: rgb(241, 241, 241);"/>
                                <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 14px; white-space: pre;" x="313.978" y="220.421">Amenazas</text>
                              </g>
                              <text style="fill: rgb(51, 51, 51); font-family: Arial, sans-serif; font-size: 10px; white-space: pre;" x="275" y="100">@debilidades@</text>
                            </svg>
            """;

    private static String getContenidoTags(TextVariable textVariable, String contenidoTags) {
        if (contenidoTags.length() > textVariable.nCharactersPerLine) {
            String[] contenidoTagsArray = contenidoTags.split(" ");
            StringBuilder contenidoTagsBuilder = new StringBuilder();
            contenidoTagsBuilder.append("<tspan x='").append(textVariable.xOffset).append("' dy='0'>");
            int charsCount = 0;
            int spacesCount = 0;
            for (String contenidoTag : contenidoTagsArray) {
                if (charsCount + contenidoTag.length() + spacesCount < textVariable.nCharactersPerLine) {
                    contenidoTagsBuilder.append(contenidoTag).append(" ");
                    charsCount += contenidoTag.length();
                    spacesCount++;
                } else {
                    contenidoTagsBuilder.append("</tspan><tspan x='").append(textVariable.xOffset).append("' dy='").append(textVariable.yOffset).append("'>").append(contenidoTag).append(" ");
                    charsCount = 0;
                    spacesCount = 0;
                }
            }
            contenidoTagsBuilder.append("</tspan>");
            contenidoTags = contenidoTagsBuilder.toString();
        }
        return contenidoTags;
    }

    public void parse() {

        String contenidosJson = """
                    [
                    {
                        "fortalezas": "Este es un texto relativamente largo, pero texto al fin, no crees?",
                        "debilidades": "Hay muchos tipos de flores en el mundo, pero la mia es mejor."
                    }
                ]
                """;

        String textVariablesString = svgContent.split("@@@")[1];
        log.info(textVariablesString);
        List<TextVariable> textVariables = new ArrayList<>();
        for (String singleVariableString : textVariablesString.split(";")) {
            String[] variableParts = singleVariableString.split("=");
            String variableName = variableParts[0].trim();
            String[] variableValues = variableParts[1].replace("(", "").replace(")", "").split(",");
            int x = Integer.parseInt(variableValues[0].trim());
            int y = Integer.parseInt(variableValues[1].trim());
            int charsPerLine = Integer.parseInt(variableValues[2].trim());
            TextVariable textVariable = new TextVariable(variableName, x, y, charsPerLine);
            log.info(textVariable.toString());
            textVariables.add(textVariable);
        }

        //reemplazo de variables de contenido con los respectivos valores
        reemplazarVariables(contenidosJson, textVariables);
    }

    private void reemplazarVariables(String contenidosJson, List<TextVariable> textVariables) {
        ObjectMapper objectMapper = new ObjectMapper();
        String contenidosJsonData = contenidosJson.trim().substring(1, contenidosJson.trim().length() - 1);
        log.info(contenidosJsonData);
        String svgContentNoFirstLine = svgContent.substring(svgContent.indexOf('\n') + 1);
        try {
            Map<String, String> contenidos = objectMapper.readValue(contenidosJsonData, new TypeReference<>() {
            });
            for (TextVariable textVariable : textVariables) {
                if (svgContentNoFirstLine.contains("@" + textVariable.name + "@")) {
                    for (Map.Entry<String, String> contenido : contenidos.entrySet()) {
                        if (contenido.getKey().equals(textVariable.name)) {
                            String contenidoTags = contenido.getValue();
                            contenidoTags = getContenidoTags(textVariable, contenidoTags);
                            log.info("Reemplazando " + textVariable.name + " por " + contenido.getValue());
                            svgContentNoFirstLine = svgContentNoFirstLine.replace("@" + textVariable.name + "@", contenidoTags);
                        }
                    }
                } else {
                    log.info("No se encontró la variable " + textVariable.name);
                }
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        log.warning(svgContentNoFirstLine);
    }

    static class TextVariable {
        String name;
        int xOffset;
        int yOffset;
        int nCharactersPerLine;
        public TextVariable(String name, int xOffset, int yOffset, int nCharactersPerLine) {
            this.name = name;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.nCharactersPerLine = nCharactersPerLine;
        }

        @Override
        public String toString() {
            return "TextVariable{" +
                   "name='" + name + '\'' +
                   ", xOffset=" + xOffset +
                   ", yOffset=" + yOffset +
                   ", nCharactersPerLine=" + nCharactersPerLine +
                   '}';
        }
    }
}
