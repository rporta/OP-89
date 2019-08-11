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
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      },
      resetDefaultConfig(){
        var configProcessUrl = this.$refs.configProcessUrl.$el;
        var getDataForm = this.$f7.form.convertToData(configProcessUrl);
        const configDefaultString = JSON.stringify(this.configDefault);
        const configDefaultJSON = JSON.parse(configDefaultString); 
        this.$f7.form.fillFromData(configProcessUrl, configDefaultJSON.processURL);
        this.config = configDefaultJSON;
      },
      resolverClickSocket(){
        console.log("resolverClickSocket");
      },
      resolverClickSms(){
        console.log("resolverClickSms");
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {

        }

        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;

        socket.on("getCapture", function(data){
          self.debug.list.push({
            title: "getCapture",
            data: JSON.stringify(data, getCircularReplacer())
          });
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