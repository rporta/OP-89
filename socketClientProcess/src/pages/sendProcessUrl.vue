<template>
  <f7-page name="sendProcessUrl">
    <f7-navbar :title="'Send proceso URL :' + ' # ' + socketId" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <div v-for="(d, i) in debug.list">
      <f7-block-title>{{i}} : {{d.title}}</f7-block-title>
      <f7-block text-color="green">
        <pre style="word-wrap: break-word;overflow-wrap: break-word;">{{d.data}}</pre>
      </f7-block>
    </div>
  </f7-page>
</template>
<style>
pre {
  white-space: pre-wrap;       /* css-3 */
  white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */
  white-space: -pre-wrap;      /* Opera 4-6 */
  white-space: -o-pre-wrap;    /* Opera 7 */
  word-wrap: break-word;       /* Internet Explorer 5.5+ */
}  
</style>
<script>
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
        socketId: this.pSocketId,
        debug: {
          list: []
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
    methods: {

    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }
        f7.dialog.preloader('Esperando Captura ...');


        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;

        socket.on("getPageStarted", function(data){
          console.log("getPageStarted");
          f7.dialog.close();
          f7.dialog.preloader('Se recargo una nueva pagina, esperando Captura ...');          
        });

        socket.on("getPin", function(data){
          f7.data.terminal.push({
            date: f7.getDateLog(),
            log: data.pin,
            color: "yellow"
          });
        }

        socket.on("getCapture", function(data){

          var img = new Image();
          img.onload = function() {
            console.log("img.onload", this.width, this.height);
            f7.data.captura.width = this.width;
            f7.data.captura.height = this.height;
          }
          console.log(f7.data.captura);

          img.src = data.img;

          console.log("getCapture");
          f7.data.captura.img = data.img;

          // load in terminal 
          f7.data.terminal.push({
            date: f7.getDateLog(),
            log: data.url,
            color: "yellow"
          });
          // redirectTo ...
          setTimeout(function() {
            f7.dialog.close();
            f7.redirectTo(/vistaDesktop/ + data.socketId);
          }, 500);
        });

        // this.debug.list.push({
        //   title : "windows",
        //   data : JSON.stringify(windows, getCircularReplacer())
        // });

        // this.debug.list.push({
        //   title : "cordovaApp",
        //   data : JSON.stringify(cordovaApp, getCircularReplacer())
        // });    

      }); 
      },    
      computed: {
      }
    };
  </script>