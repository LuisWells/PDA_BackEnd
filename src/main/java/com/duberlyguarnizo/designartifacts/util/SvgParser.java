package com.duberlyguarnizo.designartifacts.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log
public class SvgParser {
    private final String svgContent;

    public SvgParser(String svgContent) {
        this.svgContent = svgContent;
    }

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

    public void parse(String contenidosJson) {


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
        final String name;
        final int xOffset;
        final int yOffset;
        final int nCharactersPerLine;

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
