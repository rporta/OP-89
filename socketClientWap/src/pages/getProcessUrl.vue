<template>
  <f7-page name="getProcessUrl">
    <f7-navbar :title="'Get proceso URL :' + ' # ' + socketId" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <div v-for="(d, i) in debug.list">
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
        config : this.$f7.data.config,
        configDefault : this.$f7.data.configDefault,
        socketId: this.pSocketId,      
        debug: {
          list: []
        }        
      };
    },
    props:{
      pSocketId:{
        type: String,
        required : false,
        default: "",
      }
    },     
    methods: {      

    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }


        setTimeout(function() {
          f7.dialog.preloader('Cargando Url..');

          setTimeout(function() {
            f7.dialog.close();
            setTimeout(function() {
              var sendData = {
                data : JSON.stringify(f7.data.processUrl)
              };
              window.broadcaster.fireNativeEvent("LoadProcessUrl", sendData);
            }, 500);

          }, 3000);

        }, 2000);

        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;

        // Aca tenemos que avisarle a android, cambie el flujo web por la URL que se envia 
        
        this.debug.list.push({
          title : "mounted",
          data : true
        });

        this.debug.list.push({
          title : "processUrl",
          data : f7.data.processUrl
        });

      }); 
    },    
    computed: {
    }
  };
</script>