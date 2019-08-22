<template >
  <f7-page >
    <f7-navbar v-if="!JSON.parse(navbarDescktop)" title="Captura" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>

    <f7-navbar bg-color="teal" v-if="JSON.parse(navbarDescktop)" title="Captura">
    </f7-navbar>

    <img :style="!JSON.parse(navbarDescktop) ? 'top: calc(50% - 213px) !important;': null "  :width="captura.width" :height="captura.height"  @click="addEventProcessUrl($event)" class="captureProcessUrl" :src="this.$f7.data.captura.img">
  </f7-page>
</template>
<style >
.captureProcessUrl{
  position: relative;  
  display: block;
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
        socketId: this.pSocketId,
        captura: {
          width: 0,
          height: 0
        },
        navbarDescktop : this.pNavbarDescktop
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


        var x = e.layerX; 
        var y = e.layerY;

        var calculeX = x  * this.$f7.data.captura.width / this.captura.width;
        var calculeY = y * this.$f7.data.captura.height / this.captura.height;

        // calcule round resolution event

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
      },
      pNavbarDescktop:{
        type: String,
        required : false,
        default: false,
      }      
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }
        var self = this;

        self.captura.width = f7.data.captura.width / 4 ;
        self.captura.height = f7.data.captura.height / 4 ;


      });
    }
  };
</script>