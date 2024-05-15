

<template>
  <div>
    <v-tooltip
      bottom
      :disabled="!displayTooltip">
      <template #activator="{ on, attrs }">
        <v-btn
          id="btnVisioConnectorButton"
          ref="btnVisioConnectorButton"
          class="visioConnectorCallAction"
          @click.stop.prevent="startCall"
          v-bind="attrs"
          v-on="on"
          icon>
          <v-icon
            size="20">
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
      @click="startCall">{{ $t('externalVisio.label.btn.StartCall') }}</span>
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
      singleBtn: false
    };
  },
  computed: {
    parentClasses: function() {
      return this.callSettings.context.parentClasses;
    },
    displayTooltip: function() {
      return this.parentClasses.includes('call-button-mini') && !this.parentClasses.includes('v-list-item--link') ;
    },
    displayConnectorName() {
      return !this.singleBtn;
    },
    nameConnector() {
      return this.callSettings.nameConnector;
    }
  },
  methods: {
    startCall: function() {
      this.callSettings.onCallOpen();
    },
    setSingleBtn(string) {
      this.singleBtn = string;
    },
  }
};
</script>

<style>
  #peopleCompactCardBottomDrawer .v-application--is-ltr .visioConnectorCallAction {
        margin-left: 8px;
        margin-right: 4px;
  }

  #peopleCompactCardBottomDrawer .v-application--is-rtl .visioConnectorCallAction {
    margin-left: 4px;
    margin-right: 8px;
  }
  #peopleCompactCardBottomDrawer .single-btn-container {
    font-size: 0.8125rem;
  }
</style>
