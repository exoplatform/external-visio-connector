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
package org.exoplatform.webconferencing.externalvisio;

import org.exoplatform.webconferencing.CallProvider;
import org.exoplatform.webconferencing.CallProviderException;
import org.exoplatform.webconferencing.UserInfo;
import org.exoplatform.container.configuration.ConfigurationException;

public class ExternalVisioProvider extends CallProvider {

  /**
   * The Constant EXTERNAL_VISIO_TYPE.
   */
  public static final String EXTERNAL_VISIO_TYPE  = "externalVisio";

  /**
   * The Constant EXTERNAL_VISIO_TITLE.
   */
  public static final String EXTERNAL_VISIO_TITLE = "ExternalVisio";

  /**
   * Instantiates a new web conferencing provider.
   *
   * @param params the params
   * @throws ConfigurationException the configuration exception
   */
  public ExternalVisioProvider(org.exoplatform.container.xml.InitParams params) throws ConfigurationException {
    super(params);
  }

  @Override
  public String getTitle() {
    return EXTERNAL_VISIO_TITLE;
  }

  @Override
  public String getVersion() {
    return null;
  }

  @Override
  public String getType() {
    return EXTERNAL_VISIO_TYPE;
  }

  @Override
  public String[] getSupportedTypes() {
    return new String[] { getType() };
  }

  @Override
  public UserInfo.IMInfo getIMInfo(String imId) throws CallProviderException {
    return null;
  }
}
