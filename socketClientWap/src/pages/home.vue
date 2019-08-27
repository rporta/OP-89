<template>
  <f7-page name="home">
    <!-- Top Navbar -->
    <f7-navbar :sliding="false" :title="'socketClient' + $f7.data.config.type">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <!-- Page content-->
    <div v-if="isDebug" v-for="(d, i) in debug.list">
      <f7-block-title>{{i}} : {{d.title}}</f7-block-title>
      <f7-block text-color="green">
        <pre style="word-wrap: break-word;overflow-wrap: break-word;">{{d.data}}</pre>
      </f7-block>
    </div>
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';     
  export default {
    data() {
      return {
        debug: {
          list: []
        },
        isDebug : true
      };
    },
    methods: {

    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {


        }
        // Set Dom7 style, events

        this.debug.list.push({
          title : "mounted",
          data : true
        });

        var self = this;

        this.debug.list.push({
          title : "window",
          data : Object.keys(window)
        });

        this.debug.list.push({
          title : "window.plugins",
          data : Object.keys(window.plugins)
        });

        this.debug.list.push({
          title : "window.cordova",
          data : Object.keys(window.cordova)
        });

        this.debug.list.push({
          title : "window.cordova.plugins",
          data : Object.keys(window.cordova.plugins)
        });
      }); 
    }        
  };
</script>