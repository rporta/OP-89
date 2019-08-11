<template>
  <f7-page>
    <f7-navbar title="Lista de eventos" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block strong>
      <p>Sorry</p>
      <p>Requested content not found.</p>
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