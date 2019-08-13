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

        // Call F7 APIs here

        // Set Dom7 style, events

        // Set socket on
        var self = this;

        this.$f7.on("getCapture", function(data){
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