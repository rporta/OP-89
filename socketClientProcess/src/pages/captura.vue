<template >
  <f7-page >

    <f7-navbar title="Captura" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>

    <img @click="addEventProcessUrl($event)" class="captureProcessUrl" src="static/testCapture/testCapture.jpg">
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
    methods:{
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

        this.getF7().dialog.prompt("Ingrese un valor", "Evento { x : " + calculateRoundResolutionX +", y : " + calculateRoundResolutionY +" }", 
          (data)=>{
            // ok ..
            self.getF7().data.listaDeEventos.push({
              x: calculateRoundResolutionX,
              y: calculateRoundResolutionY,
              data : data
            });

          }, (data)=>{
            // cancel ..
            self.getF7().data.listaDeEventos.push({
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
  };
</script>