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

import org.apache.commons.collections.CollectionUtils;
import org.exoplatform.commons.ObjectAlreadyExistsException;
import org.exoplatform.commons.exception.ObjectNotFoundException;
import org.exoplatform.social.core.profileproperty.ProfilePropertyService;
import org.exoplatform.social.core.profileproperty.model.ProfilePropertySetting;
import org.exoplatform.webconferencing.externalvisio.dao.ExternalVisioConnectorDAO;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnectors;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

import java.util.Collections;
import java.util.List;

public class ExternalVisioConnectorServiceImpl implements ExternalVisioConnectorService {

  private final ExternalVisioConnectorDAO externalVisioConnectorDAO;
  
  private final ProfilePropertyService profilePropertyService;

  public ExternalVisioConnectorServiceImpl(ExternalVisioConnectorDAO externalVisioConnectorDAO,
                                           ProfilePropertyService profilePropertyService) {
    this.externalVisioConnectorDAO = externalVisioConnectorDAO;
    this.profilePropertyService = profilePropertyService;

  }

  @Override
  public ExternalVisioConnector createExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) throws ObjectAlreadyExistsException {
    if (externalVisioConnectorEntity == null) {
      throw new IllegalArgumentException("externalVisioConnectorEntity is mandatory");
    }
    if (externalVisioConnectorEntity.isActiveForUsers()) {
      createPropertySetting(externalVisioConnectorEntity);
    }
    return EntityBuilder.fromEntity(externalVisioConnectorDAO.create(externalVisioConnectorEntity));

  }

  @Override
  public List<ExternalVisioConnector> getExternalVisioConnectors(boolean enabled) {
    List<ExternalVisioConnectorEntity> visioConnectorEntityList = externalVisioConnectorDAO.getExternalVisioConnectors(enabled);
    return visioConnectorEntityList.stream().map(EntityBuilder::fromEntity).toList();
  }

  @Override
  public void saveExternalVisioConnectors(ExternalVisioConnectors externalVisioConnectors) {
    List<ExternalVisioConnector> existingVisioConnectors = getExternalVisioConnectors(externalVisioConnectors.isEnabled());
    if (CollectionUtils.isEmpty(existingVisioConnectors)) {
      existingVisioConnectors = Collections.emptyList();
    }
    List<ExternalVisioConnector> visioConnectorList = externalVisioConnectors.getVisioConnectors();

    if (CollectionUtils.isEmpty(visioConnectorList)) {
      visioConnectorList = Collections.emptyList();
    }
    processUpdatedExternalVisioConnectors(visioConnectorList, existingVisioConnectors);
  }

  private void processUpdatedExternalVisioConnectors(List<ExternalVisioConnector> visioConnectorList,
                                                     List<ExternalVisioConnector> existingVisioConnectors) {
    visioConnectorList.stream()
                      .filter(l -> existingVisioConnectors.stream().anyMatch(l2 -> l.getId() == l2.getId()))
                      .forEach(connector -> {
                        externalVisioConnectorDAO.update(EntityBuilder.toEntity(connector));
                      });
  }

  public ExternalVisioConnector updateExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) throws ObjectNotFoundException,
                                                                                                                        ObjectAlreadyExistsException {
    if (externalVisioConnectorEntity == null) {
      throw new IllegalArgumentException("externalVisioConnectorEntity is mandatory");
    }

    ExternalVisioConnectorEntity oldExternalVisioConnectorEntity =
                                                                 externalVisioConnectorDAO.find(externalVisioConnectorEntity.getId());
    if (oldExternalVisioConnectorEntity == null) {
      throw new ObjectNotFoundException("externalVisioConnector is not exist");
    }
    if (oldExternalVisioConnectorEntity.equals(externalVisioConnectorEntity)) {
      throw new IllegalArgumentException("there are no changes to save");
    }
    ProfilePropertySetting profilePropertySetting =
                                                  profilePropertyService.getProfileSettingByName(oldExternalVisioConnectorEntity.getName());

    if (profilePropertySetting != null) {
      profilePropertySetting.setPropertyName(externalVisioConnectorEntity.getName());
      profilePropertySetting.setActive(externalVisioConnectorEntity.isActiveForUsers());
      profilePropertyService.updatePropertySetting(profilePropertySetting);
    } else if (externalVisioConnectorEntity.isActiveForUsers()) {
      createPropertySetting(externalVisioConnectorEntity);
    }
    return EntityBuilder.fromEntity(externalVisioConnectorDAO.update(externalVisioConnectorEntity));
  }

  @Override
  public List<ExternalVisioConnector> getActiveExternalVisioConnectorsForSpace() {
    List<ExternalVisioConnectorEntity> activeVisioConnectorEntityList = externalVisioConnectorDAO.getActiveExternalVisioConnectorsForSpace();
    return activeVisioConnectorEntityList.stream().map(EntityBuilder::fromEntity).toList();
  }

  public void createPropertySetting(ExternalVisioConnectorEntity externalVisioConnectorEntity) throws ObjectAlreadyExistsException {
    ProfilePropertySetting profilePropertySetting = new ProfilePropertySetting();
    profilePropertySetting.setActive(true);
    profilePropertySetting.setEditable(true);
    profilePropertySetting.setVisible(false);
    profilePropertySetting.setPropertyName(externalVisioConnectorEntity.getName());
    profilePropertySetting.setGroupSynchronized(false);
    profilePropertySetting.setRequired(false);
    profilePropertySetting.setMultiValued(false);
    profilePropertyService.createPropertySetting(profilePropertySetting);
  }
}
