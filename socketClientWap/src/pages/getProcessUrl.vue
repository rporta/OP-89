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
        <pre style="white-space: pre-wrap;white-space: -moz-pre-wrap;white-space: -pre-wrap;white-space: -o-pre-wrap;word-wrap: break-word;">{{d.data}}</pre>
      </f7-block>
    </div>
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json'; 
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault,
        socketId: this.pSocketId,          
        debug: {
          list: [
            {
              title : "debug-test",
              data : "data-test"
            }
          ]
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
      getF7(){
        return this.$f7;
      },
      redirectTo(path){
        this.getF7().view.main.router.navigate(path);
        this.getF7().panel.close();
      },      
      generateColor(color){
        return {
          "background-color": color  + "!important"
        };
      },
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      },
      resetDefaultConfig(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(configProcessUrl, configDefaultJSON.processURL);
        this.config = configDefaultJSON;
      },
      resolverClickSocket(){
        console.log("resolverClickSocket");
      },
      resolverClickSms(){
        console.log("resolverClickSms");
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }

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
          data : self.getF7().data.processUrl
        });

        this.debug.list.push({
          title : "cordovaApp",
          data : JSON.stringify(cordovaApp)
        });

        this.debug.list.push({
          title : "f7.device.cordova",
          data : JSON.stringify(f7.device.cordova)
        });

      }); 
    },    
    computed: {
    }
  };
</script>