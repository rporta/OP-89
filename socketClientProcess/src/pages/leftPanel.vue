<template>
  <f7-page name="rightPanel">
    <f7-navbar title="Menu proceso URL"></f7-navbar>
    <f7-list>
      <f7-list-item @click="redirectTo('/terminal/1')" link="#" title="Terminal">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
      <f7-list-item @click="redirectTo('/captura/1')" link="#" title="Captura">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
      <f7-list-item @click="redirectTo('/listaDeEventos/1')" link="#" title="Lista de eventos">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
      <f7-list-item @click="redirectTo('/vistaDesktop/1')" link="#" title="Vista desktop">
        <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
      </f7-list-item>
    </f7-list>
  </f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  import config from '../config/config.json';
  
  export default {
    data() {
      return {
        clients: [],
        Process: false,
        Wap: false
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
      disconnectSocket(id){
        console.log("disconnectSocket", id);
        socket.emit("getDisconnect", {
          socketId : id
        });
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }
        // Set Dom7 style, events

        // Set socket on
        var self = this;

        socket.on('disconnect', function (){
          self.Wap = false;
          self.Process = false;
          self.clients = [];           
        });

        socket.on('sendClients', function(data) {
          for(let d in data){
            var currentData = data[d];
            if(socket.id == currentData.id){
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

        setInterval(function() {
          socket.emit("getClients", {

          });          
        }, 1000);

      }); 
    }    
  };
</script>