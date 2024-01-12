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
import './initComponents.js';


if (extensionRegistry) {
  const components = extensionRegistry.loadComponents('externalVisio');
  if (components && components.length > 0) {
    components.forEach(cmp => {
      Vue.component(cmp.componentName, cmp.componentOptions);
    });
  }
}

Vue.use(Vuetify);
const vuetify = new Vuetify(eXo.env.portal.vuetifyPreset);

const appId = 'ExternalVisioAdmin';

//getting language of the PLF
const lang = eXo?.env.portal.language || 'en';
const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/locale.portlet.externalvisio.ExternalVisio-${lang}.json`;


extensionRegistry.registerComponent('additional-visio-actions', 'external-visio-additional-actions', {
  id: 'external-visio',
  name: 'ExternalVisio',
  vueComponent: Vue.options.components['external-visio'],
  rank: 20,
});

exoi18n.loadLanguageAsync(lang, url).then(i18n => {
  // init Vue app when locale resources are ready
  Vue.createApp({
    template: `<external-visio id="${appId}" />`,
    vuetify,
    i18n
  }, `#${appId}`, 'External Visio Connector');
});

