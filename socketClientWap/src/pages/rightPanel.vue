<template>
  <f7-page name="rightPanel">
    <f7-navbar title="Menu"></f7-navbar>
    <f7-list>
      <f7-list-item @click="$f7.redirectTo('/')" link="#" title="Inicio">
        <f7-icon text-color="white" slot="media" ios="f7:home" aurora="f7:home" md="material:home"></f7-icon>
      </f7-list-item>

      <f7-list-item  accordion-item title="Configuraciones">
        <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
        <f7-accordion-content :style="generateColor('rgb(17, 17, 17)')">
          <f7-list-item @click="$f7.redirectTo('/configPermisos/')" link="#" title="Permisos" >
            <f7-icon style="margin-left: 20px;" text-color="lightblue" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
          </f7-list-item>
          <f7-list-item @click="$f7.redirectTo('/configDevice/')" link="#" title="Dispositivo" >
            <f7-icon style="margin-left: 20px;" text-color="lightblue" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
          </f7-list-item>
          <f7-list-item @click="$f7.redirectTo('/configPerfil/')" link="#" title="Perfil" >
            <f7-icon style="margin-left: 20px;" text-color="lightblue" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
          </f7-list-item>
          <f7-list-item v-if="isConfigApp" @click="$f7.redirectTo('/configApp/')" link="#" title="Configuracion App">
            <f7-icon style="margin-left: 20px;" text-color="lightblue" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
          </f7-list-item>
        </f7-accordion-content>
      </f7-list-item>
      <f7-list-item  accordion-item title="Lista de clientes process">
        <f7-icon :text-color="Process ? 'green' : 'red'" slot="media" ios="f7:phonelink" aurora="f7:phonelink" md="material:phonelink"></f7-icon>
        <f7-accordion-content :style="generateColor('rgb(17, 17, 17)')" >
          <f7-list-item accordion-item v-show="client.type == 'Process' " :title="client.driver.os + ' : #' +client.id.slice(0, 5)" v-for="client in clients" :key="client.id + 'Process'">
            <f7-icon style="margin-left: 20px;" text-color="green" slot="media" ios="f7:laptop" aurora="f7:laptop" md="material:laptop"></f7-icon>
            <f7-accordion-content :style="generateColor('rgb(10, 10, 10)')">
              <f7-list-item v-if="isInfoClient" link="#" @click="$f7.redirectTo('/infoClient/' + client.id)" title="Ver informacion">
                <f7-icon text-color="green" slot="media" ios="f7:layers_alt_fill" aurora="f7:layers_alt_fill" md="material:layers_alt_fill"></f7-icon>
              </f7-list-item>
              <f7-list-item v-if="isConfigClient" @click="$f7.redirectTo('/configClient/' + client.id)" link="#" title="Configuracion">
                <f7-icon text-color="lightblue" slot="media" ios="f7:settings" aurora="f7:settings" md="material:settings"></f7-icon>
              </f7-list-item> 
              <f7-list-item v-if="isDisconnectSocket" @click="disconnectSocket(client.id)" link="#" title="Desconectar">
                <f7-icon text-color="red" slot="media" ios="f7:close" aurora="f7:close" md="material:close"></f7-icon>
              </f7-list-item> 
              <f7-list-item  accordion-item link="#" title="Acciones">
                <f7-icon style="margin-left: 40px;" text-color="yellow" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                <f7-accordion-content bg-color="black">
                  <f7-list-item @click="$f7.redirectTo('/socketClient/' + client.id)" link="#" title="Conversar">
                    <f7-icon style="margin-left: 40px;" text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                  </f7-list-item>
                </f7-accordion-content>
              </f7-list-item>
            </f7-accordion-content>
          </f7-list-item>
        </f7-accordion-content>
      </f7-list-item>
      <f7-list-item  accordion-item title="Lista de clientes wap" >
        <f7-icon :text-color="Wap ? 'green' : 'red'" slot="media" ios="f7:phone_android" aurora="f7:phone_android" md="material:phone_android"></f7-icon>
        <f7-accordion-content :style="generateColor('rgb(17, 17, 17)')" >
          <f7-list-item accordion-item v-show="client.type == 'Wap' " :title="client.driver.os + ' : #' +client.id.slice(0, 5)" v-for="client in clients" :key="client.id  + 'Wap'">
            <f7-icon style="margin-left: 20px;" text-color="green" slot="media" ios="f7:phone_android" aurora="f7:phone_android" md="material:phone_android"></f7-icon>
            <f7-accordion-content :style="generateColor('rgb(10, 10, 10)')">
              <f7-list-item v-if="isInfoClient" link="#" @click="$f7.redirectTo('/infoClient/'+ client.id )" title="Ver informacion">
                <f7-icon text-color="green" slot="media" ios="f7:layers_alt_fill" aurora="f7:layers_alt_fill" md="material:layers_alt_fill"></f7-icon>
              </f7-list-item>
              <f7-list-item v-if="isConfigClient" @click="$f7.redirectTo('/configClient/' + client.id)" link="#" title="Configuracion">
                <f7-icon text-color="lightblue" slot="media" ios="f7:settings" aurora="f7:settings" md="material:settings"></f7-icon>
              </f7-list-item> 
              <f7-list-item v-if="isDisconnectSocket" @click="disconnectSocket(client.id)" link="#" title="Desconectar">
                <f7-icon text-color="red" slot="media" ios="f7:close" aurora="f7:close" md="material:close"></f7-icon>
              </f7-list-item> 
              <f7-list-item  accordion-item link="#" title="Acciones">
                <f7-icon style="margin-left: 40px;" text-color="yellow" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                <f7-accordion-content bg-color="black">
                  <f7-list-item @click="$f7.redirectTo('/socketClient/' + client.id)" link="#" title="Conversar">
                    <f7-icon style="margin-left: 40px;" text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                  </f7-list-item>
                  <f7-list-item @click="$f7.redirectTo('/configProcessUrl/' + client.id)" link="#" title="Iniciar proceso URL">
                    <f7-icon style="margin-left: 40px;" text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                  </f7-list-item> 
                </f7-accordion-content>
              </f7-list-item>                        
            </f7-accordion-content>
          </f7-list-item>
        </f7-accordion-content>
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
        clients: [],
        Process: false,
        Wap: false,
        isInfoClient : false,
        isConfigClient : false,
        isDisconnectSocket : false,
        isConfigApp: false
      }
    },
    methods: {
      generateColor(color){
        return {
          "background-color": color  + "!important"
        };
      },
      disconnectSocket(id){
        console.log("disconnectSocket", id);
        
        var sendData = {
          data : JSON.stringify({
            socketId : id
          })
        };

        window.broadcaster.fireNativeEvent("getDisconnect", sendData);
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }
        // Set Dom7 style, events

        // Set socket on
        var self = this;

        this.$f7.on('disconnect', function (){
          self.Wap = false;
          self.Process = false;
          self.clients = [];           
        });

        this.$f7.on('sendClients', function(data) {
          for(let d in data){
            var currentData = data[d];
            if(f7.data.socket.id == currentData.id){
              data.splice(d, 1);
            }
          }
          var isData = (data.length > 0 ? true : false);
          if(!isData){
            self.Wap = false;
            self.Process = false;
            self.clients = [];
          }
          for(let d in data){
            var currentData = data[d];
            if(currentData.type == "Wap"){
              self.Wap = true;
              break;
            }else{
              self.Wap = false;
            }
          }
          for(let d in data){
            var currentData = data[d];
            if(currentData.type == "Process"){
              self.Process = true;
              break;
            }else{
              self.Process = false;
            }
          }
          const clientsToString = JSON.stringify(self.clients);
          const clientsToArray =  JSON.parse(clientsToString);
          self.clients = clientsToArray;
          self.clients = data;

        });
        window.broadcaster.fireNativeEvent("getClients", {});
        setInterval(function() {
          window.broadcaster.fireNativeEvent("getClients", {});
        }, self.$f7.data.config.setInterval.getClients);

      }); 
    }    
  };
</script>