package org.exoplatform.webconferencing.externalvisio.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalVisioConnectors {

  private boolean                      enabled;

  private List<ExternalVisioConnector> visioConnectors;
}
