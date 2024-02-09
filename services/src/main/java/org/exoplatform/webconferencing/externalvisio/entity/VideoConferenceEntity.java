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

@Entity(name = "VideoConference")
@ExoEntity
@Table(name = "VIDEO_CONFERENCE")
@NamedQuery(name = "VideoConference.getVideoConferences", query = "SELECT new org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference(v.id, v.identity, v.url, e.id, e.name) FROM VideoConference v \n" +
        "RIGHT JOIN v.externalVisioConnector e ON e.id = v.id AND v.identity = :identity WHERE e.activeForSpaces = true")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VideoConferenceEntity implements Serializable {

  private static final long            serialVersionUID = 2575879074179965736L;

  @Id
  @SequenceGenerator(name = "SEQ_VIDEO_CONFERENCE_ID", sequenceName = "SEQ_VIDEO_CONFERENCE_ID", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_VIDEO_CONFERENCE_ID")
  @Column(name = "ID", nullable = false)
  private Long                         id;

  @Column(name = "URL", nullable = false)
  private String                       url;

  @Column(name = "IDENTITY_ID", nullable = false)
  private long               identity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "VISIO_CONNECTOR_ID")
  private ExternalVisioConnectorEntity externalVisioConnector;

}
