<template>
<f7-app :params="f7params" >
  <!-- Status bar overlay for fullscreen mode-->
  <f7-statusbar></f7-statusbar>

  <!-- Right panel with reveal effect-->
  <f7-panel right reveal theme-dark>
    <f7-view>
      <f7-page>
        <f7-navbar title="Right Panel"></f7-navbar>
        <f7-block>Right panel content goes here</f7-block>
      </f7-page>
    </f7-view>

  </f7-panel>


  <!-- Your main view, should have "view-main" class -->
  <f7-view main class="safe-areas" url="/"></f7-view>

  <!-- Popup -->
  <f7-popup id="my-popup">
    <f7-view>
      <f7-page>
        <f7-navbar title="Socket Client">
          <f7-nav-right>
            <f7-link popup-close>Close</f7-link>
          </f7-nav-right>
        </f7-navbar>


        <f7-messagebar
          :placeholder="placeholder"
          ref="messagebar"
          :attachments-visible="attachmentsVisible"
          :sheet-visible="sheetVisible"
        >
          <f7-link
            icon-ios="f7:camera_fill"
            icon-aurora="f7:camera_fill"
            icon-md="material:camera_alt"
            slot="inner-start"
            @click="sheetVisible = !sheetVisible"
          ></f7-link>
          <f7-link
            icon-ios="f7:arrow_up_fill"
            icon-aurora="f7:arrow_up_fill"
            icon-md="material:send"
            slot="inner-end"
            @click="sendMessage"
          ></f7-link>
          <f7-messagebar-attachments>
            <f7-messagebar-attachment
              v-for="(image, index) in attachments"
              :key="index"
              :image="image"
              @attachment:delete="deleteAttachment(image)"
            ></f7-messagebar-attachment>
          </f7-messagebar-attachments>
          <f7-messagebar-sheet>
            <f7-messagebar-sheet-image
              v-for="(image, index) in images"
              :key="index"
              :image="image"
              :checked="attachments.indexOf(image) >= 0"
              @change="handleAttachment"
            ></f7-messagebar-sheet-image>
          </f7-messagebar-sheet>
        </f7-messagebar>

        <f7-messages ref="messages">
          <f7-messages-title><b>Sunday, Feb 9,</b> 12:58</f7-messages-title>
          <f7-message
            v-for="(message, index) in messagesData"
            :key="index"
            :type="message.type"
            :image="message.image"
            :name="message.name"
            :avatar="message.avatar"
            :first="isFirstMessage(message, index)"
            :last="isLastMessage(message, index)"
            :tail="isTailMessage(message, index)"
          >
            <span slot="text" v-if="message.text" v-html="message.text"></span>
          </f7-message>
          <f7-message v-if="typingMessage"
            type="received"
            :typing="true"
            :first="true"
            :last="true"
            :tail="true"
            :header="`${typingMessage.name} is typing`"
            :avatar="typingMessage.avatar"
          ></f7-message>
        </f7-messages>


      </f7-page>      
    </f7-view>
  </f7-popup>

</f7-app>
</template>
<script>
  import cordovaApp from '../js/cordova-app.js';
  import routes from '../js/routes.js';

  export default {
    data() {
      return {
        // Framework7 Parameters
        f7params: {
          id: 'io.framework7.myapp', // App bundle ID
          name: 'socketClientProcess', // App name
          theme: 'auto', // Automatic theme detection
          // App root data
          data: function () {
            return {
              user: {
                firstName: 'John',
                lastName: 'Doe',
              },

            };
          },

          // App routes
          routes: routes,



          // Input settings
          input: {
            scrollIntoViewOnFocus: this.$device.cordova && !this.$device.electron,
            scrollIntoViewCentered: this.$device.cordova && !this.$device.electron,
          },
          // Cordova Statusbar settings
          statusbar: {
            overlay: this.$device.cordova && this.$device.ios || 'auto',
            iosOverlaysWebView: true,
            androidOverlaysWebView: false,
          },
        },

        // Login screen data
        username: '',
        password: '',
      }
    },
    methods: {
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }
        // Call F7 APIs here
      });
    }
  }
</script>