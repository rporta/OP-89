<template >
  <f7-page >

    <f7-navbar title="Captura" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>

    <img @click="addEventProcessUrl($event)" class="captureProcessUrl" :src="this.$f7.data.captura">
  </f7-page>
</template>
<style >
.captureProcessUrl{
  position: relative;  
  display: block;
  top: calc(50% - 213px) !important;
  width: 240px;
  height: 426px;
  margin-left: auto;
  margin-right: auto;
}
</style>
<script>
  import Dom7 from 'dom7';
  import routes from '../js/routes.js';
  export default {
    data() {
      return {
        socketId: this.pSocketId        
      };
    },
    methods:{
      redirectTo(path){
        this.$f7.view.main.router.navigate(path);
        this.$f7.panel.close();
      },
      generateColor(color){
        return {
          "background-color": color  + "!important"
        };
      },      
      addEventProcessUrl(e){

        // Ramiro Portas

        // La resolucion original de captura es 1920x1080 con relacion de aspecto 16:9
        // La resolucion de captura en la vista es 426x240 con relacion de aspecto 16:9

        // current resolution event 426x240       | 16:9
        var x = e.layerX; 
        var y = e.layerY;

        // calculate resolution event 1920x1080   | 16:9

        var calculeX = x  * 1080 / 240;
        var calculeY = y * 1920 / 426;

        // calculate round resolution event

        var calculateRoundResolutionX = Math.round(calculeX);
        var calculateRoundResolutionY = Math.round(calculeY);

        // Envio alerta al usuario para que tenga la posibilidad de agregar un dato 

        var self = this;

        this.$f7.dialog.prompt("Ingrese un valor", "Evento { x : " + calculateRoundResolutionX +", y : " + calculateRoundResolutionY +" }", 
          (data)=>{
            // ok ..
            self.$f7.data.listaDeEventos.push({
              x: calculateRoundResolutionX,
              y: calculateRoundResolutionY,
              data : data
            });

          }, (data)=>{
            // cancel ..
            self.$f7.data.listaDeEventos.push({
              x: calculateRoundResolutionX,
              y: calculateRoundResolutionY,
              data : null
            });
          }, 
          null);
      }
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
      });
    }
  };
</script>