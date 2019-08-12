<template>
  <f7-page>
    <f7-navbar title="Terminal" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>
    <f7-block text-color="green">
      <pre style="word-wrap: break-word;overflow-wrap: break-word;">{{test}}</pre>
    </f7-block>    
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
        test: this.$f7.data.config     
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
      });    
    }    
  };
</script>