package org.exoplatform.webconferencing.externalvisio.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.exoplatform.services.security.Identity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoConference {

  private Long   id;

  private Long   identity;

  private String url;

  private Long   connectorId;

  private String connectorName;
}
