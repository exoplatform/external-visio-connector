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
package org.exoplatform.webconferencing.externalvisio.service;

import org.exoplatform.commons.exception.ObjectNotFoundException;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnectors;

import java.util.List;

public interface ExternalVisioConnectorService {

  ExternalVisioConnector createExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity);

  List<ExternalVisioConnector> getExternalVisioConnectors(boolean enabled);

  void saveExternalVisioConnectors(ExternalVisioConnectors externalVisioConnectors);

  ExternalVisioConnector updateExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) throws ObjectNotFoundException;

  void deleteExternalVisioConnector(long id) throws ObjectNotFoundException;

  List<ExternalVisioConnector> getActiveExternalVisioConnectorsForSpace();

  List<ExternalVisioConnector> getActiveExternalVisioConnectorsForUser();

  List<ExternalVisioConnector> getConfiguredExternalVisioConnectors(Identity identity);
}
