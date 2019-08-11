<template>
  <f7-page>
    <f7-navbar title="Lista de eventos" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>


    <f7-list form ref="listEventProcessUrl" sortable @sortable:sort="onSort">
      <f7-list-item v-for="(currentEvent, index) in this.getF7().data.listaDeEventos"
      >
      <div>
        <input :name="'x_'+ index" type="hidden" :value="currentEvent.x">
        <input :name="'y_'+ index" type="hidden" :value="currentEvent.y">
        <input :name="'data_'+ index" type="hidden" :value="currentEvent.data">
        <f7-checkbox :name="'borrar_'+ index" v-show="borrar"></f7-checkbox>
        <f7-button @click="setX(index, currentEvent)" fill style="display: inline-block; margin: 0px 15px;">x : {{currentEvent.x}}</f7-button>
        <f7-button @click="setY(index, currentEvent)" fill style="display: inline-block; margin: 0px 15px;">y : {{currentEvent.y}}</f7-button>
        <f7-button @click="setData(index, currentEvent)" fill style="display: inline-block; margin: 0px 15px;">data</f7-button>
        <p style="display: inline-block;">{{currentEvent.data}}</p>
      </div>
      <f7-icon text-color="deeporange" slot="media" ios="f7:add" aurora="f7:add" md="material:add"></f7-icon>
    </f7-list-item>
  </f7-list>
  <br>
  <br>

  <f7-toolbar bottom-md style="background-color: #1a1a1ad4;">
    <f7-link @click="modificarPosicion()" sortable-toggle=".sortable" >modificar posicion</f7-link>
    <f7-link @click="truncateList()">limpiar listas</f7-link>
    <f7-link @click="deleteIndex()">borrar</f7-link>
    <f7-link @click="sendListEvent()">enviar</f7-link>
  </f7-toolbar>    
</f7-page>
</template>
<script>
  import Dom7 from 'dom7';
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';
  import config from '../config/config.json';
  import configDefault from '../config/configDefault.json';   
  export default {
    data() {
      return {
        config : config,
        configDefault : configDefault,
        socketId: this.pSocketId,
        borrar: false,     
        isModificarPosicion: false,         
      };
    },
    methods: {
      getF7(){
        return this.$f7;
      },
      redirectTo(path){
        this.getF7().view.main.router.navigate(path);
        this.getF7().panel.close();
      },
      deleteIndex(){
        this.borrar = !this.borrar;
        if(!this.borrar){
          var dataForm = this.getDataForm("listEventProcessUrl");
          var indexDelete = [];
          for(let d in dataForm){
            if(dataForm[d].length == 1){
              if(dataForm[d][0] == "false"){
                indexDelete.push(Number.parseInt(d.replace("borrar_", "")));
              }
            }
          }
          if(indexDelete.length > 0 && this.getF7().data.listaDeEventos.length > 0 ){
            for(let del in indexDelete){
              var currentIndexDelete = indexDelete[del];
              this.getF7().data.listaDeEventos.splice(currentIndexDelete, 1);
            }
          }
        }
      },
      truncateList(){
        this.getF7().data.listaDeEventos = [];
      },
      modificarPosicion(){
        this.isModificarPosicion = !this.isModificarPosicion;

        var tempX = []; 
        var tempY = []; 
        var tempData = []; 

        if(!this.isModificarPosicion){
          var newListEventProcessUrl = [];
          var data = this.getDataForm("listEventProcessUrl");
          for(let name in data){
            var key = name.split("_")[0];
            var valueData = data[name];
            if (["x", "y", "data"].indexOf(key) != -1){
              switch (key) {
                case "x":
                tempX.push(valueData);
                break;
                case "y":
                tempY.push(valueData);
                break;
                case "data":
                tempData.push(valueData);
                break;
              }
            }
          }
          var newData = [];
          for(let index in tempData){
            newData.push({
              x: tempX[index],
              y: tempY[index],
              data: tempData[index]
            });
          }

          const newDataString = JSON.stringify(newData);
          const newDataJSON = JSON.parse(newDataString); 
          console.log(newDataJSON);


          const listaDeEventosString = JSON.stringify(this.getF7().data.listaDeEventos);
          const listaDeEventosStringJSON = JSON.parse(listaDeEventosString); 

          var self = this;
          this.truncateList()
          setTimeout(function() {
            self.getF7().data.listaDeEventos = newDataJSON;
          }, 1);

        }
      },
      sendListEvent(){
        this.getF7().dialog.confirm(null, "Desea enviar lista de procesamiento?", 
          (data)=>{
            // ok ..
          }, (data)=>{
            // cancel ..

          });
      },
      setX(index, currentEvent){
        var self = this;

        this.getF7().dialog.prompt("Ingrese un valor", "Set X " + currentEvent.x + " : ", 
          (data)=>{
            // ok ..
            this.getF7().data.listaDeEventos[index].x = data;

          }, (data)=>{
            // cancel ..

          }, 
          currentEvent.x);
      },
      setY(index, currentEvent){
        var self = this;

        this.getF7().dialog.prompt("Ingrese un valor", "Set Y "+ currentEvent.y + ": ", 
          (data)=>{
            // ok ..
            this.getF7().data.listaDeEventos[index].y = data;
          }, (data)=>{
            // cancel ..

          }, 
          currentEvent.y);
      },
      setData(index, currentEvent){
        var self = this;

        this.getF7().dialog.prompt("Ingrese un valor", "Set data : ", 
          (data)=>{
            // ok ..
            this.getF7().data.listaDeEventos[index].data = data;

          }, (data)=>{
            // cancel ..

          }, 
          currentEvent.data);
      },    
      onSort(e) {

      },
      getDataForm(key){
        var formListEventProcessUrl = this.$refs[key].$el;
        var getDataForm = this.$f7.form.convertToData(formListEventProcessUrl);
        return getDataForm;
        // console.log(getDataForm);
        // const configDefaultString = JSON.stringify(this.configDefault);
        // const configDefaultJSON = JSON.parse(configDefaultString); 
        // this.$f7.form.fillFromData(formListEventProcessUrl, configDefaultJSON.perfil);
        // this.config = configDefaultJSON;
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