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
package org.exoplatform.webconferencing.externalvisio.dao;

import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;

import java.util.Collections;
import java.util.List;

public class ExternalVisioConnectorDAO extends GenericDAOJPAImpl<ExternalVisioConnectorEntity, Long> {

  public List<ExternalVisioConnectorEntity> getExternalVisioConnectors(boolean enabled) {
    TypedQuery<ExternalVisioConnectorEntity> query =
                                                   getEntityManager().createNamedQuery("ExternalVisioConnector.getExternalVisioConnectors",
                                                                                       ExternalVisioConnectorEntity.class)
                                                                     .setParameter("enabled", enabled);

    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return Collections.emptyList();
    }
  }

  public List<ExternalVisioConnectorEntity> getActiveExternalVisioConnectors(boolean forSpaces) {
    String queryName = "ExternalVisioConnector.getActiveExternalVisioConnectorsFor";
    if (!forSpaces) {
      queryName += "Users";
    } else {
      queryName += "Spaces";
    }
    TypedQuery<ExternalVisioConnectorEntity> query = getEntityManager().createNamedQuery(queryName,
                                                                                         ExternalVisioConnectorEntity.class);
    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return Collections.emptyList();
    }
  }
}
