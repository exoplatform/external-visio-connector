<!--
Copyright (C) 2024 eXo Platform SAS.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
-->
<template>
  <div>
    <exo-drawer
      id="externalVisioConnectorAddDrawer"
      ref="externalVisioConnectorAddDrawer"
      right
      allow-expand
      @expand-updated="expanded = $event"
      @closed="close">
      <template slot="title">
        <div class="d-flex">
          <span> {{ title }} </span>
        </div>
      </template>
      <template slot="content">
        <v-form
          v-model="isValidInputs">
          <v-card-text class="d-flex pb-2">
            <v-label>
              <span class="text-color font-weight-bold text-start text-truncate-2">
                {{ $t('externalVisio.label.nameConnector.title') }}            
              </span>
              <p class="caption">{{ $t('externalVisio.label.nameConnector.description') }}  </p>
            </v-label>
          </v-card-text>
          <v-card-text class="d-flex py-0">
            <v-text-field
              v-model="externalVisioName"
              class="pt-0"
              type="text"
              required="required"
              :placeholder="$t('externalVisio.nameConnector.placeholder')"
              outlined
              dense />
          </v-card-text>
          <v-card-text class="d-flex flex-row pt-8">
            <v-label>
              <span class="text-color font-weight-bold">
                {{ $t('externalVisio.label.moduleConnector.title') }}            
              </span>
              <p class="caption"> {{ $t('externalVisio.label.moduleConnector.description') }} </p>
            </v-label>
          </v-card-text>
          <v-card-text class="d-flex flex-row">
            <div class="pt-1">
              <v-label>
                <span class="text-color">
                  {{ $t('externalVisio.label.userModuleConnector.title') }}
                </span>
                <p class="caption">{{ $t('externalVisio.label.userModuleConnector.description') }}</p>
              </v-label>
            </div> 
            <v-spacer />
            <v-switch      
              v-model="isUserConnector"
              :aria-label="this.$t(`externalVisio.tooltip.userModuleConnector.${this.switchUserAriaLabel}`)"
              class="mt-0 pt-0" />
          </v-card-text>
          <v-card-text class="d-flex flex-row">
            <div class="pt-1">
              <v-label>
                <span class="text-color">
                  {{ $t('externalVisio.label.spaceModuleConnector.title') }}
                </span>
                <p class="caption">{{ $t('externalVisio.label.spaceModuleConnector.description') }}</p>
              </v-label>
            </div>
            <v-spacer />
            <v-switch      
              v-model="isSpaceConnector"
              :aria-label="this.$t(`externalVisio.tooltip.spaceModuleConnector.${this.switchSpaceAriaLabel}`)"
              class="mt-0 pt-0" />
          </v-card-text>
        </v-form>
      </template>
      <template slot="footer">
        <div class="d-flex justify-end">
          <v-btn
            class="btn ms-2"
            @click="close">
            {{ $t('externalVisio.label.btn.cancel') }}
          </v-btn>
          <v-btn
            :disabled="disabled"
            :loading="loading"
            @click="saveExternalVisioConnector"
            class="btn btn-primary ms-2">
            {{ $t('externalVisio.label.btn.Save') }}
          </v-btn>
        </div>
      </template>
    </exo-drawer>
  </div>
</template>
<script>
export default {
  data () {
    return {
      originalVisioConnector: null,
      externalVisioName: '',
      isUserConnector: true,
      isSpaceConnector: true,
      isValidInputs: true,
      editMode: false,
      connectorId: null,
      connectorOrder: null
    };
  },
  created() {
    this.$root.$on('open-external-visio-add-drawer', this.open);
    this.$root.$on('open-external-visio-edit-drawer', (connector) => {
      this.editMode = true;
      this.open(connector);
    });
  },
  computed: {
    disabled() {
      return this.editMode && this.modified || !this.externalVisioName;
    },
    modified() {
      return JSON.stringify(this.originalVisioConnector) === JSON.stringify({activeForUsers: this.isUserConnector, activeForSpaces: this.isSpaceConnector, name: this.externalVisioName});
    },
    switchUserAriaLabel() {
      return this.isUserConnector && 'disable' || 'enable';
    },
    switchSpaceAriaLabel() {
      return this.isSpaceConnector && 'disable' || 'enable';
    },
    title() {
      return this.editMode ? this.$t('externalVisio.drawer.editConnector.title') : this.$t('externalVisio.drawer.addConnector.title');
    }
  },
  methods: {
    open(connector) {
      if (connector) {
        this.connectorId = connector.id;
        this.externalVisioName = connector.name;
        this.isUserConnector = connector.activeForUsers;
        this.isSpaceConnector = connector.activeForSpaces;
        this.connectorOrder = connector.order;
        this.originalVisioConnector = {
          activeForUsers: this.isUserConnector, 
          activeForSpaces: this.isSpaceConnector, 
          name: this.externalVisioName
        };
      }
      this.$nextTick().then(() => this.$refs.externalVisioConnectorAddDrawer.open());
    },
    close() {
      this.editMode = false;
      this.externalVisioName = null;
      this.isUserConnector= true;
      this.isSpaceConnector= true;
      this.originalVisioConnector= null;
      this.$refs.externalVisioConnectorAddDrawer.close();
    },
    saveExternalVisioConnector() {
      const externalConnector = {
        name: this.externalVisioName,
        activeForUsers: this.isUserConnector,
        activeForSpaces: this.isSpaceConnector,
        enabled: this.isUserConnector || this.isSpaceConnector
      };
      if (this.editMode) {
        externalConnector.id = this.connectorId;
        externalConnector.order = this.connectorOrder;
      }
      this.$root.$emit('save-external-visio-connector', externalConnector, this.editMode);
      this.close();
    }
  }
};
</script>
