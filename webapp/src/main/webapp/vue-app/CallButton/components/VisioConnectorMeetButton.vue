

<template>
  <div class="width-auto d-flex flex-row">
    <v-tooltip
      bottom
      :disabled="!displayTooltip">
      <template #activator="{ on, attrs }">
        <v-btn
          id="btnVisioConnectorButton"
          class="visioConnectorCallAction"
          @click.stop.prevent="startCall"
          v-bind="attrs"
          v-on="on"
          icon>
          <v-icon
            size="16">
            fas fa-video
          </v-icon>
        </v-btn>
      </template>
      <span v-if="displayTooltip">{{ $t('externalVisio.label.btn.StartCall') }}</span>
    </v-tooltip>
    <span
      class="text-truncate text-break text-wrap pt-2"
      v-if="displayConnectorName"
      @click.stop.prevent="startCall">{{ nameConnector }}</span>
    <span
      v-else-if="!displayTooltip"
      @click.stop.prevent="startCall">{{ $t('externalVisio.label.btn.StartCall') }}</span>
  </div>
</template>

<script>
export default {
  props: {
    callSettings: {
      type: Object,
      required: true
    }
  },

  data: function() {
    return {
      settings: this.callSettings,
    };
  },
  computed: {
    parentClasses: function() {
      return this.callSettings.context.parentClasses;
    },
    displayTooltip: function() {
      return this.parentClasses.includes('call-button-mini');
    },
    displayConnectorName() {
      return document.querySelector('.single-btn-container') === null;
    },
    nameConnector() {
      return this.callSettings.nameConnector;
    }
  },
  methods: {
    startCall: function() {
      this.callSettings.onCallOpen();
    },
  }
};
</script>