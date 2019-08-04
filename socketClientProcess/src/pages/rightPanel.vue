<template>
  <f7-page name="rightPanel">
  <f7-navbar title="Right Panel"></f7-navbar>
    <f7-list>
      <f7-list-item @click="redirectTo('/')" link="#" title="Inicio">
        <f7-icon text-color="white" slot="media" ios="f7:home" aurora="f7:home" md="material:home"></f7-icon>
      </f7-list-item>
      <f7-list-item @click="redirectTo('/configPerfil/')" link="#" title="Configuracion de perfil" >
        <f7-icon text-color="lightblue" slot="media" ios="f7:person" aurora="f7:person" md="material:person"></f7-icon>
      </f7-list-item>
      <f7-list-item @click="redirectTo('/configApp/')" link="#" title="Configuracion App">
        <f7-icon text-color="lightblue" slot="media" ios="f7:settings_appl" aurora="f7:settings_appl" md="material:settings_appl"></f7-icon>
      </f7-list-item>
      <f7-list-item  accordion-item title="Lista de clientes process">
        <f7-icon text-color="green" slot="media" ios="f7:phonelink" aurora="f7:phonelink" md="material:phonelink"></f7-icon>
        <f7-accordion-content :style="generateColor('rgb(11, 11, 11)')">
            <f7-list-item  accordion-item title="Linux">
              <f7-icon text-color="green" slot="media" ios="f7:laptop" aurora="f7:laptop" md="material:laptop"></f7-icon>
              <f7-accordion-content bg-color="black">
                  <f7-list-item link="#" @click="redirectTo('/infoClient/')" title="Ver informacion">
                    <f7-icon text-color="green" slot="media" ios="f7:layers_alt_fill" aurora="f7:layers_alt_fill" md="material:layers_alt_fill"></f7-icon>
                  </f7-list-item>
                  <f7-list-item @click="redirectTo('/configClient/')" link="#" title="Configuracion">
                    <f7-icon text-color="lightblue" slot="media" ios="f7:settings" aurora="f7:settings" md="material:settings"></f7-icon>
                  </f7-list-item> 
                  <f7-list-item link="#" title="Desconectar">
                    <f7-icon text-color="red" slot="media" ios="f7:close" aurora="f7:close" md="material:close"></f7-icon>
                  </f7-list-item> 
                  <f7-list-item link="#" title="Acciones">
                    <f7-icon text-color="yellow" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                  </f7-list-item>                         
              </f7-accordion-content>
            </f7-list-item>
            <f7-list-item  accordion-item title="Samsung A7">
              <f7-icon text-color="green" slot="media" ios="f7:phone_android" aurora="f7:phone_android" md="material:phone_android"></f7-icon>
              <f7-accordion-content bg-color="black">
                  <f7-list-item link="#" title="Ver informacion">
                    <f7-icon text-color="green" slot="media" ios="f7:layers_alt_fill" aurora="f7:layers_alt_fill" md="material:layers_alt_fill"></f7-icon>
                  </f7-list-item>
                  <f7-list-item link="#" title="Configuracion">
                    <f7-icon text-color="lightblue" slot="media" ios="f7:settings" aurora="f7:settings" md="material:settings"></f7-icon>
                  </f7-list-item> 
                  <f7-list-item link="#" title="Desconectar">
                    <f7-icon text-color="red" slot="media" ios="f7:close" aurora="f7:close" md="material:close"></f7-icon>
                  </f7-list-item> 
                  <f7-list-item link="#" title="Acciones">
                    <f7-icon text-color="yellow" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                  </f7-list-item>                         
              </f7-accordion-content>
            </f7-list-item>
        </f7-accordion-content>
      </f7-list-item>
      <f7-list-item  accordion-item title="Lista de clientes wap">
        <f7-icon text-color="green" slot="media" ios="f7:phone_android" aurora="f7:phone_android" md="material:phone_android"></f7-icon>
        <f7-accordion-content :style="generateColor('rgb(11, 11, 11)')">
          <f7-list-item  accordion-item title="Samsung A7">
            <f7-icon text-color="green" slot="media" ios="f7:phone_android" aurora="f7:phone_android" md="material:phone_android"></f7-icon>
            <f7-accordion-content bg-color="black">
                <f7-list-item link="#" title="Ver informacion">
                  <f7-icon text-color="green" slot="media" ios="f7:layers_alt_fill" aurora="f7:layers_alt_fill" md="material:layers_alt_fill"></f7-icon>
                </f7-list-item>
                <f7-list-item link="#" title="Configuracion">
                  <f7-icon text-color="lightblue" slot="media" ios="f7:settings" aurora="f7:settings" md="material:settings"></f7-icon>
                </f7-list-item> 
                <f7-list-item link="#" title="Desconectar">
                  <f7-icon text-color="red" slot="media" ios="f7:close" aurora="f7:close" md="material:close"></f7-icon>
                </f7-list-item> 
                <f7-list-item link="#" title="Acciones">
                  <f7-icon text-color="yellow" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
                </f7-list-item>                         
            </f7-accordion-content>
          </f7-list-item>
        </f7-accordion-content>
      </f7-list-item>                                   
    </f7-list>
  </f7-page>
</template>
<script>
  import routes from '../js/routes.js';
	
  export default {
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
      }            
    }
  };
</script>