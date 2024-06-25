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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.exoplatform.commons.exception.ObjectNotFoundException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.webconferencing.ActiveCallProvider;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnectors;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/externalVisio")
@Tag(name = "/v1/externalVisio", description = "Manages external visio connector")
public class ExternalVisioConnectorRest implements ResourceContainer {

  private static final Log                    LOG = ExoLogger.getLogger(ExternalVisioConnectorRest.class);

  private final ExternalVisioConnectorService externalVisioConnectorService;

  private final IdentityManager               identityManager;

  private final SpaceService                  spaceService;

  public ExternalVisioConnectorRest(ExternalVisioConnectorService externalVisioConnectorService,
                                    IdentityManager identityManager,
                                    SpaceService spaceService) {
    this.externalVisioConnectorService = externalVisioConnectorService;
    this.identityManager = identityManager;
    this.spaceService = spaceService;
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
  @Schema(defaultValue = "true")
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

  @PUT
  @RolesAllowed("administrators")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Saves a externalVisioConnectors", description = "Saves a externalVisioConnectors", method = "PUT")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response saveExternalVisioConnectors(@RequestBody(description = "ExternalVisioConnectors to create", required = true)
  ExternalVisioConnectors externalVisioConnectors) {

    if (externalVisioConnectors == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("externalVisioConnectors object is mandatory").build();
    }
    try {
      externalVisioConnectorService.saveExternalVisioConnectors(externalVisioConnectors);
      return Response.ok().build();
    } catch (Exception e) {
      LOG.warn("Error saving list of external visio connectors", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("administrators")
  @Operation(summary = "Updates an ExternalVisioConnector", description = "Updates an ExternalVisioConnector", method = "PUT")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "400", description = "Invalid query input"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response updateExternalVisioConnector(@Parameter(description = "External visio id", required = true)
  @PathParam("id")
  long externalVisioId, @RequestBody(description = "externalVisioConnector object to update", required = true)
  ExternalVisioConnector externalVisioConnector) {
    if (externalVisioConnector == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("externalVisioConnector object is mandatory").build();
    }
    if (externalVisioId <= 0) {
      return Response.status(Response.Status.BAD_REQUEST).entity("ExternalVisio technical identifier must be positive").build();
    }
    try {
      ExternalVisioConnector updatedExternalVisioConnector =
                                                           externalVisioConnectorService.updateExternalVisioConnector(EntityBuilder.toEntity(externalVisioConnector));
      return Response.ok(updatedExternalVisioConnector).build();
    } catch (Exception e) {
      LOG.warn("Error updating an ExternalVisioConnector", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("administrators")
  @Operation(summary = "Deletes an ExternalVisioConnector", description = "Deletes an ExternalVisioConnector", method = "PUT")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "400", description = "Invalid query input"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response deleteExternalVisioConnector(@Parameter(description = "External visio id", required = true)
                                               @PathParam("id")
                                               long externalVisioId) {
    if (externalVisioId <= 0) {
      return Response.status(Response.Status.BAD_REQUEST).entity("ExternalVisio technical identifier must be positive").build();
    }
    try {
      externalVisioConnectorService.deleteExternalVisioConnector(externalVisioId);
      return Response.ok().build();
    } catch (Exception e) {
      LOG.warn("Error deleting an ExternalVisioConnector", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  @RolesAllowed("users")
  @Path("{identityId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Retrieves the list of configured external visio connectors", method = "GET", description = "Retrieves the list of configured external visio connectors")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "400", description = "Invalid query input"),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response getConfiguredExternalVisioConnectors(@Parameter(description = "identityId", required = true)
  @PathParam("identityId")
  String identityId) {
    String authenticatedUser = ConversationState.getCurrent().getIdentity().getUserId();
    Space space = spaceService.getSpaceByPrettyName(identityId);
    Identity identity = null;
    if (space != null) {
      identity = identityManager.getOrCreateSpaceIdentity(identityId);
      if (!spaceService.isMember(space, authenticatedUser) && !spaceService.isSuperManager(authenticatedUser)) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
      }
    } else {
      identity = identityManager.getOrCreateUserIdentity(identityId);
    }
    try {
      List<ExternalVisioConnector> externalVisioConnectors = new ArrayList<>();
      if (identity!=null) {
        externalVisioConnectors =
            externalVisioConnectorService.getConfiguredExternalVisioConnectors(identity);
      }
      return Response.ok(externalVisioConnectors).build();
    } catch (Exception e) {
      LOG.warn("Error retrieving list of configured external visio connectors", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }
}
