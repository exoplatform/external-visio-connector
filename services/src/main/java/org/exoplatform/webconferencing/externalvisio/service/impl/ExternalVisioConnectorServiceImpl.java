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
package org.exoplatform.webconferencing.externalvisio.service.impl;

import org.exoplatform.webconferencing.externalvisio.dao.ExternalVisioConnectorDAO;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

import java.util.List;

public class ExternalVisioConnectorServiceImpl implements ExternalVisioConnectorService {

  private final ExternalVisioConnectorDAO externalVisioConnectorDAO;

  public ExternalVisioConnectorServiceImpl(ExternalVisioConnectorDAO externalVisioConnectorDAO) {
    this.externalVisioConnectorDAO = externalVisioConnectorDAO;

  }

  @Override
  public ExternalVisioConnector createExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) {
    return EntityBuilder.fromEntity(externalVisioConnectorDAO.create(externalVisioConnectorEntity));

  }

  @Override
  public List<ExternalVisioConnector> getExternalVisioConnectors(boolean enabled) {
    List<ExternalVisioConnectorEntity> visioConnectorEntityList = externalVisioConnectorDAO.getExternalVisioConnectors(enabled);
    return visioConnectorEntityList.stream().map(EntityBuilder::fromEntity).toList();
  }
}
