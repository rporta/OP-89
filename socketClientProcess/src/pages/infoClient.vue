<template>
  <f7-page name="infoClient">
    <f7-navbar :title="'informacion Client :' + ' # ' + socketId" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block strong>
      <p># {{socketId}} </p>
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
  };
</script>