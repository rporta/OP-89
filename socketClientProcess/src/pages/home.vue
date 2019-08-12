<template>
  <f7-page name="home">
    <!-- Top Navbar -->
    <f7-navbar :sliding="false" :title="'socketClient' + $f7.data.config.type">
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
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';  
  export default {
    data() {
      return {

      };
    },
    methods: {

    },    
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }
        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;
      }); 
    } 
  };
</script>