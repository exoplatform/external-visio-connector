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
  <v-app id="ExternalVideoAdmin">
    <h4 class="text-color mb-0">{{ $t('externalVideo.admin.settings.title') }}</h4>
    <span class="caption text-light-color">{{ $t('externalVideo.admin.settings.description') }}</span>
    <external-video-toolbar />
    <v-data-table
      :headers="headers"
      :items="externalVideoConnector"
      :items-per-page="itemsPerPage"
      :hide-default-footer="hideFooter"
      :footer-props="{
        itemsPerPageText: `${$t('externalVideo.settings.itemsPerPage')}:`,
      }"
      :loading-text="$t('externalVideo.settings.loadingResults')"
      :no-results-text="$t('externalVideo.settings.noResultsFound')"
      :no-data-text="$t('externalVideo.settings.noExternalVideo')"
      disable-sort>
      <template slot="item" slot-scope="props">
        <tr>
          <td>
            <div :title="props.item.label" class="text-truncate font-weight-bold">
              {{ $t(props.item.label) }}
            </div>
          </td>
          <td>
            <div class="pull-right">
              <v-icon size="18" class="primary--text">
                {{ 'fa-arrow-down' }}
              </v-icon>
              <v-icon size="18" class="primary--text">
                {{ 'fa-arrow-up' }}
              </v-icon>
            </div>
          </td>
          <td>
            <div class="align-center">
              <v-btn 
                primary
                icon>
                <i class="uiIconEdit"></i>
              </v-btn>
            </div>
          </td>
        </tr>
      </template>
    </v-data-table>
  </v-app>
</template>
<script>
export default {
  data: () => ({
    headers: [],
    externalVideoConnector: [],
    itemsPerPage: 10,
    hideFooter: false,
  }),
  created() {
    this.headers = [
      { text: 'Name', align: 'left' },
      { text: 'Order', align: 'right' , width: '60px'},
      { text: 'Edit', align: 'center' , width: '60px'}
    ];
  }
};

</script>
