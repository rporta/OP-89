<template>
  <f7-page>
    <f7-navbar title="vistaDesktop" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-row>
      <f7-col>
        <f7-page-content class="c1rama">
          <f7-view url="/captura/1/1" name="vistaDesktop-c1" :animate="false"
          ></f7-view>
        </f7-page-content>
      </f7-col>
      <f7-col>
        <f7-page-content class="c2rama">
          <f7-view url="/listaDeEventos/1/1" name="vistaDesktop-c2" :animate="false"
          ></f7-view>
        </f7-page-content>
      </f7-col>
    </f7-row>
    <f7-row>
      <f7-col>
        <f7-page-content class="r2rama">
          <f7-view url="/terminal/1/1" name="vistaDesktop-c3" :animate="false"
          ></f7-view>         
        </f7-page-content>
      </f7-col>
    </f7-row>
  </f7-page>
</template>
<style>
.c1rama {
  /*  padding: 0px;  */
  box-sizing: border-box;
  position: absolute;
  left: 0;
  top: 0;
  width: 50%;
  height: 60%;
  transform: none;
  background-color: var(--f7-page-bg-color);
  z-index: 1;
}
.c2rama {
  /*padding: 0px;  */
  box-sizing: border-box;
  position: absolute;
  left: 50%;
  top: 0;
  width: 50%;
  height: 60%;
  transform: none;
  background-color: var(--f7-page-bg-color);
  z-index: 1;
}
.r2rama {
  padding: 0px;  
  box-sizing: border-box;
  position: absolute;
  left: 0;
  top: 60%;
  width: 100%;
  height: 40%;
  transform: none;
  background-color: var(--f7-page-bg-color);
  z-index: 1;
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
    props:{
      pSocketId:{
        type: String,
        required : false,
        default: "",
      }
    }, 
    methods: {
      redirectTo(path){
        this.$f7.view.main.router.navigate(path);
        this.$f7.panel.close();
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