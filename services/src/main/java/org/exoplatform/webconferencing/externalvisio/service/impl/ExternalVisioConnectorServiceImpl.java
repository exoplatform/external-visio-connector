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
import org.exoplatform.commons.api.settings.SettingService;
import org.exoplatform.commons.api.settings.SettingValue;
import org.exoplatform.commons.api.settings.data.Context;
import org.exoplatform.commons.api.settings.data.Scope;
import org.exoplatform.commons.exception.ObjectNotFoundException;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.profileproperty.ProfilePropertyService;
import org.exoplatform.social.core.profileproperty.model.ProfilePropertySetting;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.webconferencing.externalvisio.dao.ExternalVisioConnectorDAO;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnector;
import org.exoplatform.webconferencing.externalvisio.rest.model.ExternalVisioConnectors;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.ExternalVisioConnectorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalVisioConnectorServiceImpl implements ExternalVisioConnectorService {

  private static final Log LOG = ExoLogger.getLogger(ExternalVisioConnectorServiceImpl.class);

  private final ExternalVisioConnectorDAO externalVisioConnectorDAO;
  
  private final ProfilePropertyService profilePropertyService;
  
  private final SettingService            settingService;
  
  private final SpaceService              spaceService;

  public ExternalVisioConnectorServiceImpl(ExternalVisioConnectorDAO externalVisioConnectorDAO,
                                           ProfilePropertyService profilePropertyService,
                                           SettingService settingService,
                                           SpaceService spaceService) {
    this.externalVisioConnectorDAO = externalVisioConnectorDAO;
    this.profilePropertyService = profilePropertyService;
    this.settingService = settingService;
    this.spaceService = spaceService;
  }

  @Override
  public ExternalVisioConnector createExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) {
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

  public ExternalVisioConnector updateExternalVisioConnector(ExternalVisioConnectorEntity externalVisioConnectorEntity) throws ObjectNotFoundException {
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
    List<ExternalVisioConnectorEntity> activeVisioConnectorEntityList = externalVisioConnectorDAO.getActiveExternalVisioConnectors(true);
    return activeVisioConnectorEntityList.stream().map(EntityBuilder::fromEntity).toList();
  }

  @Override
  public List<ExternalVisioConnector> getActiveExternalVisioConnectorsForUser() {
    List<ExternalVisioConnectorEntity> activeVisioConnectorEntityList = externalVisioConnectorDAO.getActiveExternalVisioConnectors(false);
    return activeVisioConnectorEntityList.stream().map(EntityBuilder::fromEntity).toList();
  }

  @Override
  public List<ExternalVisioConnector> getConfiguredExternalVisioConnectors(Identity identity) {
    List<ExternalVisioConnectorEntity> externalVisioConnectors = identity.isSpace()
        || identity.isUser() ? externalVisioConnectorDAO.getActiveExternalVisioConnectors(identity.isSpace()) : new ArrayList<>();
    return externalVisioConnectors.stream().map(EntityBuilder::fromEntity).map(p -> {
      p.setUrl(getExternalVisioConnectorsUrl(identity, p));
      return p;
    }).filter(p -> p.getUrl() != null).toList();
  }

  public String getExternalVisioConnectorsUrl(Identity identity, ExternalVisioConnector externalVisioConnector) {
    String url = null;
    if (identity.isSpace()) {
      Space space = spaceService.getSpaceByPrettyName(identity.getRemoteId());
      SettingValue<?> settingValue = settingService.get(Context.GLOBAL,
                                                        Scope.SPACE.id(space.getId()),
                                                        String.valueOf(externalVisioConnector.getId()));
      if (settingValue != null) {
        url = String.valueOf(settingValue.getValue());
      }
    } else if (identity.isUser()) {
      url = (String) identity.getProfile().getProperty(externalVisioConnector.getName());
    }
    return url;
  }

  public void createPropertySetting(ExternalVisioConnectorEntity externalVisioConnectorEntity) {
    ProfilePropertySetting profilePropertySetting = new ProfilePropertySetting();
    profilePropertySetting.setActive(true);
    profilePropertySetting.setEditable(true);
    profilePropertySetting.setVisible(false);
    profilePropertySetting.setPropertyName(externalVisioConnectorEntity.getName());
    profilePropertySetting.setGroupSynchronized(false);
    profilePropertySetting.setRequired(false);
    profilePropertySetting.setMultiValued(false);

    try {
      profilePropertyService.createPropertySetting(profilePropertySetting);
    } catch (ObjectAlreadyExistsException e) {
      LOG.warn("Profile property " + profilePropertySetting.getPropertyName() + " already exists");
    }
  }
}
