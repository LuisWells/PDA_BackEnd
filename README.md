[![Java CI with Maven](https://github.com/duberlyguarnizofuentesrivera/DesignArtifacts/actions/workflows/maven.yml/badge.svg)](https://github.com/duberlyguarnizofuentesrivera/DesignArtifacts/actions/workflows/maven.yml)
# Design Artifacts

### A plugin-able spring boot application for designing management and planning design artifacts
This is the backend of PDA (Planning Design Artifacts), a service that allows you to create APAv7-compatible known graphs for use in publications. User only has to select the graph type and write the content, and server returns an SGV representation of the graph that can be downloaded as JPG image. This module includes a secured REST API service.
## Dependencies

- Java 17
- Spring boot 2.7.4
- OpenAPI
- Spring Data JPA (Hibernate)
- Postgresql

  Configuration as well as new designs for artifacts rely on JSON files.
