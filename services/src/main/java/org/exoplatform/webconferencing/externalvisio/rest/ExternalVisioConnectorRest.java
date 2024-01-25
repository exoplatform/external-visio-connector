/*
 * Copyright (C) 2024 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.webconferencing.externalvisio.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/v1/externalVisio")
@Tag(name = "/v1/externalVisio", description = "Manages external visio connector")
public class ExternalVisioConnectorRest implements ResourceContainer {

  private static final Log                    LOG = ExoLogger.getLogger(ExternalVisioConnectorRest.class);

  private final ExternalVisioConnectorService externalVisioConnectorService;

  public ExternalVisioConnectorRest(ExternalVisioConnectorService externalVisioConnectorService) {
    this.externalVisioConnectorService = externalVisioConnectorService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("administrators")
  @Operation(summary = "Creates a new External visio connector", description = "Creates a new External visio connector", method = "POST")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "400", description = "Invalid query input"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response createExternalVisioConnector(@RequestBody(description = "ExternalVisioConnector object to create", required = true)
  ExternalVisioConnector externalVisioConnector) {
    if (externalVisioConnector == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("externalVisioConnector object is mandatory").build();
    }
    try {
      ExternalVisioConnector createdExternalVisioConnector =
                                                           externalVisioConnectorService.createExternalVisioConnector(EntityBuilder.toEntity(externalVisioConnector));
      return Response.ok().entity(createdExternalVisioConnector).build();
    } catch (Exception e) {
      LOG.warn("Error creating a ExternalVisioConnector", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("users")
  @Path("/connectors")
  @Operation(summary = "Retrieves the list of external visio connectors", description = "Retrieves the list of external visio connectors for an authenticated user", method = "GET")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response getExternalVisioConnectors(@Parameter(description = "filter ExternalVisioConnector by status", required = true)
  @QueryParam("enabled")
  Boolean enabled) {
    try {
      List<ExternalVisioConnector> externalVisioConnectors = externalVisioConnectorService.getExternalVisioConnectors(enabled);
      return Response.ok(externalVisioConnectors).build();
    } catch (Exception e) {
      LOG.warn("Error retrieving list of external visio connectors", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }
}
