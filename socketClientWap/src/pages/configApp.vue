<template>
  <f7-page name="configApp">
    <f7-navbar title="Configuracion App" back-link="Back">
      <f7-chip :text="socketTitle" :color="socketColor"></f7-chip>
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block-title>General : </f7-block-title>
    <f7-list>
      <f7-list-item :title="'Id : # ' + socketId">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
      <f7-list-item :title="'Type : ' + config.type">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
    </f7-list>
    <f7-block-title>Config socket</f7-block-title>
    <f7-list no-hairlines-md form form-store-data ref="configSocket">
      <f7-list-input
        label="Port"
        floating-label
        type="text"
        name="port"
        placeholder="Your Port"
        clear-button
        :value="getF7().data.config.api.port" 
        @input="getF7().data.config.api.port = $event.target.value"        
      >
                      <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
      </f7-list-input>
      <f7-list-input
        label="Host"
        floating-label
        type="text"
        name="host"
        placeholder="Your URL Host"
        clear-button
        :value="getF7().data.config.api.host" 
        @input="getF7().data.config.api.host = $event.target.value"   
      >
                      <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
      </f7-list-input>      
    </f7-list>
    <f7-block-title>Socket</f7-block-title>
    <f7-block>
      <f7-row>
        <f7-col>
          <f7-button @click="socketConnect()" fill color="green">connect</f7-button>
        </f7-col>
        <f7-col>
          <f7-button @click="socketDisconnect()" fill color="red">disconnect</f7-button>
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
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json';  
  // import Dom7 from 'Dom7';
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault,
        socketTitle: "Socket Offline",
        socketColor: "red",
        socketId: ""
      };
    },
    methods: {
      resetDefaultConfig(){
        var formConfigPerfil = this.$refs.configSocket.$el;
        var getDataForm = this.$f7.form.convertToData(formConfigPerfil);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(formConfigPerfil, configDefaultJSON.perfil);
        this.config = configDefaultJSON;
      },
      socketConnect(){
        socket.io.uri = "http://" + this.getF7().data.config.api.host + ":" + this.getF7().data.config.api.port;        
        socket.connect();
        var self = this;
        setTimeout(function() {
          self.socketId = socket.id;
        }, 500);
      },
      socketDisconnect(){
        socket.disconnect();
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Set Dom7 style, events


        // Set data 
        this.socketTitle = socket.connected ? "Socket Online" : "Socket Offline";
        this.socketColor = socket.connected ? "green" : "red";
        this.socketId = socket.id;

        var self = this;

        setInterval(function() {          
          // Set data by setInterval
          self.socketTitle = socket.connected ? "Socket Online" : "Socket Offline";
          self.socketColor = socket.connected ? "green" : "red";
        }, 500);

      }); 
    }
  };
</script>