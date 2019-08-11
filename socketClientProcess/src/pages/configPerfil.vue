<template>
  <f7-page name="configPerfil">
    <f7-navbar title="Configuracion de perfil" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <br>
    <br>
    <img :src="getF7().data.perfil.avatar" style="border-radius: 50%;height: 250px;display: block;margin: auto;border: solid 5px white;">
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
      :value="getF7().data.perfil.name" 
      @input="getF7().data.perfil.name = $event.target.value"
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
    :value="getF7().data.perfil.avatar" 
    @input="getF7().data.perfil.avatar = $event.target.value"
    >
    <f7-icon text-color="lightblue" slot="media" ios="f7:person" aurora="f7:person" md="material:person"></f7-icon>
  </f7-list-input>      
</f7-list>
<f7-block-title>Load Perfil </f7-block-title>
<f7-block>
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
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json';  
  import Dom7 from 'dom7';
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault
      };
    },
    methods: {
      resetDefaultConfig(){
        var formConfigPerfil = this.$refs.configPerfil.$el;
        var getDataForm = this.$f7.form.convertToData(formConfigPerfil);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(formConfigPerfil, configDefaultJSON.perfil);
        this.config = configDefaultJSON;
      },
      getF7(){
        return this.$f7;
      },
    },
    mounted() {
      this.$f7ready((f7) => {
        // Set Dom7 style, events
      }); 
    } 
  };
</script>