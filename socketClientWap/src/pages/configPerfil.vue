<template>
  <f7-page name="configPerfil">
    <f7-navbar title="Configuracion de perfil" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <br>
    <br>
    <img :src="$f7.data.config.perfil.avatar" style="border-radius: 50%;height: 250px;display: block;margin: auto;border: solid 5px white;">
    <f7-block-title>General </f7-block-title>
    <f7-list no-hairlines-md form form-store-data ref="configPerfil">
      <f7-list-input
      ref="Name"
      label="Name"
      name="name"
      floating-label
      type="text"
      placeholder="Your name"
      clear-button
      :value="$f7.data.config.perfil.name" 
      @input="$f7.data.config.perfil.name = $event.target.value"
      >
      <f7-icon text-color="lightblue" slot="media" ios="f7:person" aurora="f7:person" md="material:person"></f7-icon>
    </f7-list-input>
    <f7-list-input
    ref="Avatar"
    label="Avatar"
    name="avatar"
    floating-label
    type="text"
    placeholder="Your URL avatar"
    clear-button
    :value="$f7.data.config.perfil.avatar" 
    @input="$f7.data.config.perfil.avatar = $event.target.value"
    >
    <f7-icon text-color="lightblue" slot="media" ios="f7:person" aurora="f7:person" md="material:person"></f7-icon>
  </f7-list-input>      
</f7-list>
<f7-block-title v-if="isLoadPerfil">Load Perfil </f7-block-title>
<f7-block v-if="isLoadPerfil">
  <f7-row>
    <f7-col>
      <f7-button fill color="green">by Google</f7-button>
    </f7-col>
    <f7-col>
      <f7-button fill color="blue">by facebook</f7-button>
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
        isLoadPerfil : false
      };
    },
    methods: {
      resetDefaultConfig(){
        var formConfigPerfil = this.$refs.configPerfil.$el;
        var getDataForm = this.$f7.form.convertToData(formConfigPerfil);
        const configDefaultString = JSON.stringify(this.$f7.data.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(formConfigPerfil, configDefaultJSON.perfil);
        this.$f7.data.config = configDefaultJSON;
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
      }); 
    } 
  };
</script>