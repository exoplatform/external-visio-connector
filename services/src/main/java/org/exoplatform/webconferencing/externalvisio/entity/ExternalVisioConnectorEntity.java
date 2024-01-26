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
package org.exoplatform.webconferencing.externalvisio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.exoplatform.commons.api.persistence.ExoEntity;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ExternalVisioConnector")
@ExoEntity
@Table(name = "EXTERNAL_VISIO_CONNECTOR")
@NamedQuery(name = "ExternalVisioConnector.getExternalVisioConnectors", query = "SELECT connector FROM ExternalVisioConnector connector ORDER BY connector.name ASC")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalVisioConnectorEntity implements Serializable {

  private static final long serialVersionUID = -5427975208480223009L;

  @Id
  @SequenceGenerator(name = "SEQ_External_Visio_Connector_ID", sequenceName = "SEQ_External_Visio_Connector_ID", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_External_Visio_Connector_ID")
  @Column(name = "ID", nullable = false)
  private Long              id;

  @Column(name = "NAME", nullable = false)
  private String            name;

  @Column(name = "ACTIVE_FOR_USERS")
  private boolean           activeForUsers;

  @Column(name = "ACTIVE_FOR_SPACES")
  private boolean           activeForSpaces;

}
