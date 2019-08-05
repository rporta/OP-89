<template>
  <f7-page name="home">
    <!-- Top Navbar -->
    <f7-navbar :sliding="false" :title="'socketClient' + config.type">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <!-- Page content-->
    <f7-block strong>
      <f7-row>
        <f7-col width="100">
          <f7-button fill raised popup-open="#my-popup">Socket Client Open</f7-button>
        </f7-col>
      </f7-row>
    </f7-block>
  </f7-page>
</template>
<script>
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json';   
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault
      };
    }
  };
</script>