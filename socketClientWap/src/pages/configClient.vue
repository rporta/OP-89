<template>
  <f7-page name="configClient">
    <f7-navbar :title="'Configuracion Client :' + ' # ' + socketId" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block-title>Config socket </f7-block-title>
    <f7-list no-hairlines-md form form-store-data ref="configSocket">
      <f7-list-input
        label="Port"
        floating-label
        type="text"
        name="port"
        placeholder="Your Port"
        clear-button
        :value="config.api.port" 
        @input="config.api.port = $event.target.value"        
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
        :value="config.api.host" 
        @input="config.api.host = $event.target.value"   
      >
                      <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
      </f7-list-input>      
    </f7-list>
    <f7-block-title>Socket </f7-block-title>
    <f7-block>
      <f7-row>
        <f7-col>
          <f7-button fill color="red">disconnect</f7-button>
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
      resetDefaultConfig(){
        var formConfigPerfil = this.$refs.configSocket.$el;
        var getDataForm = this.$f7.form.convertToData(formConfigPerfil);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(formConfigPerfil, configDefaultJSON.perfil);
        this.config = configDefaultJSON;
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Set Dom7 style, events
      }); 
    }
  };
</script>