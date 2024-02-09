package org.exoplatform.webconferencing.externalvisio.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.VideoConferenceService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/videoConference")
@Tag(name = "/v1/videoConference", description = "Manages video Conference")
public class VideoConferenceRest implements ResourceContainer {

  private static final Log             LOG = ExoLogger.getLogger(VideoConferenceRest.class);

  private final VideoConferenceService videoConferenceService;

  public VideoConferenceRest(VideoConferenceService videoConferenceService) {
    this.videoConferenceService = videoConferenceService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("users")
  @Operation(summary = "Saves a new Video conference", description = "Creates a new Video conference", method = "POST")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "400", description = "Invalid query input"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response saveVideoConference(@RequestBody(description = "VideoConference object to create", required = true)
  VideoConference videoConference) {
    if (videoConference == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("videoConference object is mandatory").build();
    }
    try {
      VideoConference savedVideoConference =
                                             videoConferenceService.saveVideoConference(EntityBuilder.toEntity(videoConference));
      return Response.ok().entity(savedVideoConference).build();
    } catch (Exception e) {
      LOG.warn("Error creating a VideoConference", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("users")
  @Operation(summary = "Retrieves the list of video conference", description = "Retrieves the list of video conference", method = "GET")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request fulfilled"),
      @ApiResponse(responseCode = "500", description = "Internal server error"), })
  public Response getVideoConferences(@Parameter(description = "Identity technical identifier to include in results")
  @QueryParam("identityId")
  long identityId) {
    try {
      List<VideoConference> videoConferences = videoConferenceService.getVideoConferences(identityId);
      return Response.ok(videoConferences).build();
    } catch (Exception e) {
      LOG.warn("Error retrieving list of video conferences", e);
      return Response.serverError().entity(e.getMessage()).build();
    }
  }
}
