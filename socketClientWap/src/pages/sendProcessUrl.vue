<template>
  <f7-page name="configProcessUrl">
    <f7-navbar title="Configuracion proceso URL" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>


    <f7-block-title>Set URL </f7-block-title>
    <f7-list no-hairlines-md form form-store-data ref="configProcessUrl">
      <f7-list-input
        label="URL"
        floating-label
        type="text"
        name="url"
        placeholder="Your URL"
        clear-button
        :value="config.processURL.url" 
        @input="config.processURL.url = $event.target.value"        
      >
                      <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
      </f7-list-input>   
    </f7-list>
    <f7-block-title>Iniciar Proceso via </f7-block-title>
    <f7-block>
      <f7-row>
        <f7-col>
          <f7-button @click="resolverClickSocket()" fill color="green">SOCKET</f7-button>
        </f7-col>
        <f7-col>
          <f7-button @click="resolverClickSms()" fill color="green">SMS</f7-button>
        </f7-col>        
      </f7-row>      
    </f7-block>  

    <f7-block-title>Set default config </f7-block-title>
    <f7-block>
      <f7-row>
        <f7-col>
          <f7-button @click="resetDefaultConfig()" fill color="red">reset</f7-button>
        </f7-col>       
      </f7-row>      
    </f7-block>     


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
        socketId: this.pSocketId          
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

      }); 
    },    
    computed: {
    }
  };
</script>