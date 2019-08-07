<template>
  <f7-page name="socketClient">
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
            ref="camera"
            icon-ios="f7:camera_fill"
            icon-aurora="f7:camera_fill"
            icon-md="material:camera_alt"
            slot="inner-start"
            @click="clickCamera()"
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

        <f7-messages ref="messages" >
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
        // Set messages-f7
        attachments: [],
        sheetVisible: false,
        typingMessage: null,
        messagesData: [
        ],
        images: [
          'https://cdn.framework7.io/placeholder/cats-300x300-1.jpg',
          'https://cdn.framework7.io/placeholder/cats-200x300-2.jpg',
          'https://cdn.framework7.io/placeholder/cats-400x300-3.jpg',
          'https://cdn.framework7.io/placeholder/cats-300x150-4.jpg',
          'https://cdn.framework7.io/placeholder/cats-150x300-5.jpg',
          'https://cdn.framework7.io/placeholder/cats-300x300-6.jpg',
          'https://cdn.framework7.io/placeholder/cats-300x300-7.jpg',
          'https://cdn.framework7.io/placeholder/cats-200x300-8.jpg',
          'https://cdn.framework7.io/placeholder/cats-400x300-9.jpg',
          'https://cdn.framework7.io/placeholder/cats-300x150-10.jpg',
        ],
        responseInProgress: false,
        // Set default config
        config : config,   
        configDefault : configDefault        
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
      generateColor(color){
        return {
          "background-color": color  + "!important"
        };
      },
      alertLoginData() {
        this.$f7.dialog.alert('Username: ' + this.username + '<br>Password: ' + this.password);
      },
      isFirstMessage(message, index) {
        const self = this;
        const previousMessage = self.messagesData[index - 1];
        if (message.isTitle) return false;
        if (!previousMessage || previousMessage.type !== message.type || previousMessage.name !== message.name) return true;
        return false;
      },
      isLastMessage(message, index) {
        const self = this;
        const nextMessage = self.messagesData[index + 1];
        if (message.isTitle) return false;
        if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
        return false;
      },
      isTailMessage(message, index) {
        const self = this;
        const nextMessage = self.messagesData[index + 1];
        if (message.isTitle) return false;
        if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
        return false;
      },
      deleteAttachment(image) {
        const self = this;
        const index = self.attachments.indexOf(image);
        self.attachments.splice(index, 1)[0]; // eslint-disable-line
      },
      handleAttachment(e) {
        const self = this;
        const index = self.$$(e.target).parents('label.checkbox').index();
        const image = self.images[index];
        if (e.target.checked) {
          // Add to attachments
          self.attachments.unshift(image);
        } else {
          // Remove from attachments
          self.attachments.splice(self.attachments.indexOf(image), 1);
        }
      },
      keymonitor(e){
        var self = this;
        setTimeout(function() {
          var text = Dom7(self.messagebar.$textareaEl).val().replace(/\n/g, '<br>').trim();
          var isAddcomment = (self.attachments.length > 0 ? true : false);
          var isOffKeymonitor = (text == "" ? true : false);

          if(isOffKeymonitor && !isAddcomment){
            self.offKeymonitor(e);
          }else{
            // Send socket 
            socket.emit("typingMessage", self.getF7().data.perfil);
          }
        }, 100);
      },
      offKeymonitor(e){
        // Send socket         
        setTimeout(function() {
          socket.emit("offTypingMessage", {});          
        }, 500);
      },
      sendMessage() {
        const self = this;
        const text = self.messagebar.getValue().replace(/\n/g, '<br>').trim();
        const messagesToSend = [];
        self.attachments.forEach((attachment) => {
          messagesToSend.push({
            image: attachment,
          });
        });
        if (text.trim().length) {
          messagesToSend.push({
            text,
          });
        }
        if (messagesToSend.length === 0) {
          return;
        }
        // Reset attachments
        self.attachments = [];
        // Hide sheet
        self.sheetVisible = false;
        // Clear area
        self.messagebar.clear();
        // Focus area
        if (text.length) self.messagebar.focus();
        // Send message
        self.messagesData.push(...messagesToSend);
        // Send socket
        socket.emit("message", messagesToSend);
      },
      clickCamera(){
        this.sheetVisible = !this.sheetVisible;
      },
      getInfoDevice(){
        var device = this.getF7().device;
        var data = {};
        for(let key in device){
          if(key != "pixelRatio"){          
            var currentValue = device[key];
            if(typeof currentValue === "boolean"){
              if(currentValue){
                data[key] = currentValue;
              }
            }else{
              data[key] = currentValue;
            }
          }
        }
        return data;
      }
    },
    mounted() {
      this.$f7ready((f7) => {
        // Init cordova APIs (see cordova-app.js)
        if (f7.device.cordova) {
          cordovaApp.init(f7);
        }

        // Call F7 APIs here
        this.messagebar = this.$refs.messagebar.f7Messagebar;
        this.messages = this.$refs.messages.f7Messages;   
        this.camera = this.$refs.camera;   

        // Set Dom7 style, events
        Dom7(this.messages.$pageContentEl).attr({
          style: "height : calc(100% - 48px) !important;"
        });
        Dom7(this.messagebar.$textareaEl).keydown(this.keymonitor);

        // Set socket on
        var self = this;

        socket.on('connect', function() {
            var initData = {
                id: socket.id,
                type: self.config.type,
                wifi: "on",
                driver:  self.getInfoDevice()
            };
            socket.emit("init", initData);
        });
        socket.on("sendTypingMessage", function(data){
          self.responseInProgress = true;
          self.typingMessage = data;      
        });
        socket.on("sendOffTypingMessage", function(data){
          self.responseInProgress = false;
          self.typingMessage = null;      
        });        
        socket.on("sendMessage", function(data){
          var tempTypingMessage = self.typingMessage;
          self.typingMessage = null;
          self.responseInProgress = false;        
          for(let m in data){
            var currentData = data[m];
            currentData.type = 'received';
            currentData.name = tempTypingMessage.name;
            currentData.avatar = tempTypingMessage.avatar;
            self.messagesData.push(currentData);
          }
        });
      }); 
    },    
    computed: {
      attachmentsVisible() {
        const self = this;
        return self.attachments.length > 0;
      },
      placeholder() {
        const self = this;

        var isAddcomment = (self.attachments.length > 0 ? true : false);

        this.keymonitor();

        return isAddcomment ? 'Add comment or Send' : 'Message';
      },
    }
  };
</script>