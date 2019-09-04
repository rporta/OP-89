<template>
  <f7-page>
    <f7-navbar v-if="!JSON.parse(navbarDescktop)" title="Terminal" back-link="Back">
      <f7-nav-right>
        <f7-link icon-ios="f7:menu" icon-aurora="f7:menu" icon-md="material:menu" panel-open="right"></f7-link>
      </f7-nav-right>
    </f7-navbar>

    <f7-navbar bg-color="teal" v-if="JSON.parse(navbarDescktop)" title="Terminal" >
    </f7-navbar>
    <template v-for="(t, i) in $f7.data.terminal">
      <f7-block :text-color="!t.color ? 'green' : t.color">
        <pre style="word-wrap: break-word;overflow-wrap: break-word;">{{i + 1}} : {{JSON.stringify(t)}}</pre>
      </f7-block>
    </template>
    <br>
    <br>
    <f7-toolbar bottom-md style="background-color: #1a1a1ad4;">
      <f7-link ></f7-link>
      <f7-link @click="truncateTerminal()"><f7-icon text-color="red" ios="f7:delete" aurora="f7:delete" md="material:delete"></f7-icon></f7-link>
      <f7-link ></f7-link>
    </f7-toolbar>  
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
        navbarDescktop : this.pNavbarDescktop
      };
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
    methods: {
      truncateTerminal(){

        this.$f7.dialog.confirm(null, "Truncar terminal ?", data => {
          // ok 
          this.$f7.data.terminal = [];

        },
        data => {
          // cancel
        });

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