<template>
  <f7-page name="infoClient">
    <f7-navbar title="Informacion Cliente " back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block-title>General : </f7-block-title>
    <f7-list>
      <f7-list-item :title="'Id : #' + infoClient.id">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
      <f7-list-item :title="'Type : ' + infoClient.type">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
    </f7-list>
    <f7-block-title>Driver : </f7-block-title>
    <f7-list>
      <f7-list-item :title="index.charAt(0).toUpperCase() + index.slice(1) + ' : ' + driver" v-for="(driver, index) in infoClient.driver">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
    </f7-list>
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
        socketId: this.pSocketId,
        infoClient : {
          id:"",
          type: "",
          driver: {},
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
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }

        // // Set socket
        var self = this;
        socket.emit("getClient", {
          socketId: self.socketId
        });
        socket.on("sendClient", function(client){
          self.infoClient = client;
        });
      }); 
    }   
  };
</script>