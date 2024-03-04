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
  <v-flex id="externalVisioToolbar" class="d-flex flex-row">
    <v-flex class="d-flex flex-grow-0 pt-2">
      <v-btn
        class="btn btn-primary"
        @click="$root.$emit('open-external-visio-add-drawer')">
        <span class="d-none d-sm-inline">
          {{ $t('externalVisio.button.addConnector') }}
        </span>
      </v-btn>
    </v-flex>
    <v-spacer />
    <v-flex class="d-flex flex-grow-0 pt-2">
      <v-scale-transition>
        <select
          v-model="filter"
          :aria-label="$t('externalVisio.filter.label')"
          class="width-auto my-auto me-2 pe-2 subtitle-1 ignore-vuetify-classes d-none d-sm-inline">
          <option value="ENABLED">
            {{ $t('externalVisio.status.enabled') }}
          </option>
          <option value="DISABLED">
            {{ $t('externalVisio.status.disabled') }}
          </option>
        </select>
      </v-scale-transition>
    </v-flex>
  </v-flex>
</template>

<script>
export default {
  data: () => ({
    filter: 'ENABLED',
  }),
  watch: {
    filter() {
      this.$root.$emit('search-external-visio-connectors', this.filter);
    },
  }
};
</script>
