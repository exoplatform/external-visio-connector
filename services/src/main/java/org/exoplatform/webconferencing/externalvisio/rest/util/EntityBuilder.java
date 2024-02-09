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
package org.exoplatform.webconferencing.externalvisio.rest.util;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.entity.VideoConferenceEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

public class EntityBuilder {

  public static ExternalVisioConnector fromEntity(ExternalVisioConnectorEntity externalVisioConnectorEntity) {
    if (externalVisioConnectorEntity == null) {
      return null;
    }
    return new ExternalVisioConnector(externalVisioConnectorEntity.getId(),
                                      externalVisioConnectorEntity.getName(),
                                      externalVisioConnectorEntity.isActiveForUsers(),
                                      externalVisioConnectorEntity.isActiveForSpaces(),
                                      externalVisioConnectorEntity.isEnabled(),
                                      externalVisioConnectorEntity.getOrder());
  }

  public static ExternalVisioConnectorEntity toEntity(ExternalVisioConnector externalVisioConnector) {
    if (externalVisioConnector == null) {
      return null;
    }
    return new ExternalVisioConnectorEntity(externalVisioConnector.getId(),
                                            externalVisioConnector.getName(),
                                            externalVisioConnector.isActiveForUsers(),
                                            externalVisioConnector.isActiveForSpaces(),
                                            externalVisioConnector.isEnabled(),
                                            externalVisioConnector.getOrder());

  }

  public static VideoConference fromEntity(VideoConferenceEntity videoConferenceEntity) {
    if (videoConferenceEntity == null) {
      return null;
    }
    return new VideoConference(videoConferenceEntity.getId(),
            videoConferenceEntity.getIdentity(),
            videoConferenceEntity.getUrl(),
            videoConferenceEntity.getExternalVisioConnector().getId(),
            videoConferenceEntity.getExternalVisioConnector().getName());
  }

  public static VideoConferenceEntity toEntity(VideoConference videoConference) {
    if (videoConference == null) {
      return null;
    }
    ExternalVisioConnectorService externalVisioConnectorService = CommonsUtils.getService(ExternalVisioConnectorService.class);

    ExternalVisioConnectorEntity externalVisioConnector = externalVisioConnectorService.getExternalVisioConnectorById(videoConference.getConnectorId());
    return new VideoConferenceEntity(videoConference.getId(),
            videoConference.getUrl(),
            videoConference.getIdentity(),
            externalVisioConnector);

  }

}
