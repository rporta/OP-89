<template>
  <f7-page name="configProcessUrl">
    <f7-navbar :title="'Configuracion proceso URL :' + ' # ' + socketId" back-link="Back">
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
      :value="$f7.data.config.processURL.url" 
      @input="$f7.data.config.processURL.url = $event.target.value"        
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
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
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
      resetDefaultConfig(){

        this.$f7.dialog.confirm(null, "Set default config ?", data => {
          // ok 
          var configProcessUrl = this.$refs.configProcessUrl.$el;
          var getDataForm = this.$f7.form.convertToData(configProcessUrl);
          const configDefaultString = JSON.stringify(this.$f7.data.configDefault);
          const configDefaultJSON = JSON.parse(configDefaultString); 
          this.$f7.form.fillFromData(configProcessUrl, configDefaultJSON.processURL);
          this.$f7.data.config = configDefaultJSON;

        },
        data => {
          // cancel
        });

      },
      resolverClickSocket(){

        this.$f7.dialog.confirm(null, "Iniciar Proceso via socket ?", data => {
          // ok 
          var configProcessUrl = this.$refs.configProcessUrl.$el;
          var getDataForm = this.$f7.form.convertToData(configProcessUrl);
          // Send socket
          var self = this;   

          var sendData = {
            data : JSON.stringify(Object.assign({
              socketId : self.socketId,
            }, getDataForm))
          };

          window.broadcaster.fireNativeEvent("configProcessUrlSocket", sendData);

        },
        data => {
          // cancel
        });

      },
      resolverClickSms(){

        this.$f7.dialog.confirm(null, "Iniciar Proceso via sms ?", data => {
          // ok 
          var configProcessUrl = this.$refs.configProcessUrl.$el;
          var getDataForm = this.$f7.form.convertToData(configProcessUrl);
          console.log(getDataForm);
          // Send socket
          var self = this;  

          var sendData = {
            data : JSON.stringify(Object.assign({
              socketId : self.socketId,
            }, getDataForm))
          };     
          window.broadcaster.fireNativeEvent("configProcessUrlSms", sendData);

        },
        data => {
          // cancel
        });

      }
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

        this.$f7.on("sendConfigProcessUrlSocket", function(data) {
          console.log("sendConfigProcessUrlSocket", data);
          if(data.type == config.type){
            self.redirectTo('/sendProcessUrl/' + data.socketId);
          }
        });

      }); 
    },    
    computed: {
    }
  };
</script>