import VisioConnectorMeetButton from './components/VisioConnectorMeetButton.vue';

Vue.component('VisioConnectorMeetButton', VisioConnectorMeetButton);

const vuetify = new Vuetify(eXo.env.portal.vuetifyPreset);

// getting language of user
const lang = eXo?.env.portal.language || 'en';
const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/locale.portlet.externalvisio.ExternalVisio-${lang}.json`;

export function init(settings) {
  // getting locale ressources
  const parentContainer = document.querySelector('.leftHeaderDrawer');
  if (parentContainer) {
    parentContainer.addEventListener('click', e => {
      if (e.target.classList.contains('backButton')
        && e.target.parentElement.classList.contains('leftHeaderDrawer')) {
        const container = document.querySelector('.single-btn-container');
        const button = document.querySelector('.visioConnectorCallAction');
        if (container && button) {
          container.removeChild(button);
        }
      }
    });
  }
  return exoi18n.loadLanguageAsync(lang, url).then((i18n) => {
    // init Vue app when locale ressources are ready
    const comp = new Vue({
      data() {
        return {
          callSettings: settings,
        };
      },
      render: (h) =>
        h(VisioConnectorMeetButton, {
          props: {
            callSettings: settings,
          }
        }),
      i18n,
      vuetify,
    });
    return comp;
  });
}