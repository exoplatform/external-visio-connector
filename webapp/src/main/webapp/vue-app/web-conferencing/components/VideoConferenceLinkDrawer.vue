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
      id="videoConferenceLinkDrawer"
      ref="videoConferenceLinkDrawer"
      right
      allow-expand
      @expand-updated="expanded = $event"
      @closed="close">
      <template slot="title">
        <div class="d-flex">
          <v-icon
            size="16"
            class="clickable"
            :aria-label="$t('externalVisio.close.label')"
            @click="close()">
            fas fa-arrow-left
          </v-icon>
          <span> Zoom video conference </span>
        </div>
      </template>
      <template slot="content">
        <v-form>
          <v-card-text class="d-flex pb-2">
            <v-label>
              <span class="text-color font-weight-bold text-start text-truncate-2">
                Link of the video conference              
              </span>
              <p class="caption">Paste the link provided by your video conference  </p>
            </v-label>
          </v-card-text>
          <v-card-text class="d-flex py-0">
            <v-text-field
              v-model="videoConferenceLink"
              class="pt-0"
              type="text"
              required="required"
              :placeholder="placeholder"
              outlined
              dense />
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
      placeholder: 'Enter the URL of the video conference',
      videoConferenceLink: ''
    };
  },
  created() {
    this.$root.$on('open-video-conference-link-drawer', this.open);
  },
  methods: {
    open() {
      this.$nextTick().then(() => this.$refs.videoConferenceLinkDrawer.open());
    }
  }
};
</script>