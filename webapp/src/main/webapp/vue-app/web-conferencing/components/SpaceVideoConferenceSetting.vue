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
  <v-card class="pa-6 card-border-radius overflow-hidden" flat>
    <v-list-item class="px-0 mb-4">
      <v-list-item-content class="py-0">
        <v-list-item-title class="my-0">
          <h4 class="font-weight-bold mt-0">
            {{ $t('videoConference.space.settings.title') }}
          </h4>
        </v-list-item-title>
        <v-list-item-title class="pt-2">
          {{ $t('videoConference.event.settings.title') }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ $t('videoConference.event.settings.description') }}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-list-item-action class="pt-6">
        <v-switch
          v-model="active"
          :aria-label="this.$t(`videoConference.switch.label.${this.switchAriaLabel}`)" />
      </v-list-item-action>
    </v-list-item>

    <v-list-item class="px-0">
      <v-list-item-content>
        <v-list-item-title class="subtitle-1">
          {{ $t('videoConference.space.settings.list.title') }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ $t('videoConference.space.settings.list.description') }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-list-item class="px-0">
      <v-list-item-content>
        <v-list-item-title>
          {{ $t('videoConference.space.settings.Jitsi.title') }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ $t('videoConference.space.settings.Jitsi.description') }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-list-item
      class="px-0"
      v-for="videoConference in videoConferences"
      :key="videoConference">
      <v-list-item-content>
        <v-list-item-title class="subtitle-1">
          {{ videoConference.name }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ $t('videoConference.space.settings.externalVisio.descrition') }}
        </v-list-item-subtitle>
      </v-list-item-content>
      <v-list-item-action class="pt-0 ma-0 mb-6">
        <v-btn 
          :title="$t('externalVisio.settings.editConnector')"
          primary
          icon
          @click="$root.$emit('open-video-conference-link-drawer', videoConference)">
          <i class="uiIconEdit"></i>
        </v-btn>
      </v-list-item-action>
    </v-list-item>
    <video-conference-link-drawer />
  </v-card>
</template>
<script>
export default {
  data: () =>({
    videoConferences: [],
    active: true
  }),
  created() {
    this.getVideoConferences();
  },
  computed: {
    switchAriaLabel() {
      return this.active && 'disable' || 'enable';
    },
  },
  methods: {
    getVideoConferences() {
      this.$externalVisioConnectorService.getExternalVisioConnectors(true)
        .then((videoConferences) => {
          this.videoConferences = videoConferences.filter(video => video.activeForSpaces) ;
        });
    },
  }

};
</script>

