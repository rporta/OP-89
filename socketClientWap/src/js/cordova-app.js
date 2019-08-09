var cordovaApp = {
  f7: null,
  /*
  This method hides splashscreen after 2 seconds
  */
  handleSplashscreen: function() {
    var f7 = cordovaApp.f7;
    if (!window.navigator.splashscreen || f7.device.electron) return;
    setTimeout(() => {
      window.navigator.splashscreen.hide();
    }, 2000);
  },
  /*
  This method prevents back button tap to exit from app on android.
  And allows to exit app on backbutton double tap
  */
  handleAndroidBackButton: function() {
    var f7 = cordovaApp.f7;
    if (f7.device.electron) return;
    cordovaApp.backButtonTimestamp = new Date().getTime();
    document.addEventListener('backbutton', function(e) {
      if (new Date().getTime() - cordovaApp.backButtonTimestamp < 250) {
        cordovaApp.backButtonTimestamp = new Date().getTime();
        if (window.navigator.app && window.navigator.app.exitApp) {
          window.navigator.app.exitApp();
        }
        return true;
      }
      cordovaApp.backButtonTimestamp = new Date().getTime();
      e.preventDefault();
      return false;
    }, false);
  },
  /*
  This method does the following:
    - provides cross-platform view "shrinking" on keyboard open/close
    - hides keyboard accessory bar for all inputs except where it required
  */
  handleKeyboard: function() {
    var f7 = cordovaApp.f7;
    if (!window.Keyboard || !window.Keyboard.shrinkView || f7.device.electron) return;
    var $ = f7.$;
    window.Keyboard.shrinkView(false);
    window.Keyboard.disableScrollingInShrinkView(true);
    window.Keyboard.hideFormAccessoryBar(true);
    window.addEventListener('keyboardWillShow', () => {
      f7.input.scrollIntoView(document.activeElement, 0, true, true);
    });
    window.addEventListener('keyboardDidShow', () => {
      f7.input.scrollIntoView(document.activeElement, 0, true, true);
    });

    window.broadcaster.addEventListener('onDataModuleJava', (data) => {
      if ("isTrusted" in data) {
        delete data.isTrusted;
      }
      alert(JSON.stringify(data));
      if (data.type = "socket") {
        switch (data.event) {
          case "sendConfigProcessUrlSocket":
            f7.data.sendConfigProcessUrlSocket = data.data;
            f7.emit(data.event, f7.data.sendConfigProcessUrlSocket);
            break;
          case "sendClient":
            f7.data.sendClient = data.data;
            f7.emit(data.event, f7.data.sendClient);
            break;
          case "disconnect":
            f7.data.disconnect = data.data;
            f7.emit(data.event, f7.data.disconnect);
            break;
          case "sendClients":
            f7.data.sendClients = data.data;
            f7.emit(data.event, f7.data.sendClients);
            break;
          case "sendTypingMessage":
            f7.data.sendTypingMessage = data.data;
            f7.emit(data.event, f7.data.sendTypingMessage);
            break;
          case "sendOffTypingMessage":
            f7.data.sendOffTypingMessage = data.data;
            f7.emit(data.event, f7.data.sendOffTypingMessage);
            break;
          case "connect":
            f7.data.connect = data.data;
            f7.emit(data.event, f7.data.connect);
            break;
          case "sendMessage":
            f7.data.sendMessage = data.data;
            f7.emit(data.event, f7.data.sendMessage);
            break;
        }
      } else if (data.type = "img") {
        if (data.event = "onImg") {
          f7.data.img = data.img;
          f7.emit(data.event, f7.data.img);
        }
      } else if (data.type = "data") {
        if (data.event = "onData") {
          f7.data.appJava = data.data;
          f7.emit(data.event, f7.data.appJava);
        }
      }
    }, true);

    window.addEventListener('keyboardDidHide', () => {
      if (document.activeElement && $(document.activeElement).parents('.messagebar').length) {
        return;
      }
      window.Keyboard.hideFormAccessoryBar(false);
    });
    window.addEventListener('keyboardHeightWillChange', (event) => {
      var keyboardHeight = event.keyboardHeight;
      if (keyboardHeight > 0) {
        // Keyboard is going to be opened
        document.body.style.height = `calc(100% - ${keyboardHeight}px)`;
        $('html').addClass('device-with-keyboard');
      } else {
        // Keyboard is going to be closed
        document.body.style.height = '';
        $('html').removeClass('device-with-keyboard');
      }

    });
    $(document).on('touchstart', 'input, textarea, select', function(e) {
      var nodeName = e.target.nodeName.toLowerCase();
      var type = e.target.type;
      var showForTypes = ['datetime-local', 'time', 'date', 'datetime'];
      if (nodeName === 'select' || showForTypes.indexOf(type) >= 0) {
        window.Keyboard.hideFormAccessoryBar(false);
      } else {
        window.Keyboard.hideFormAccessoryBar(true);
      }
    }, true);
  },
  init: function(f7) {
    // Save f7 instance
    cordovaApp.f7 = f7;

    // Handle Android back button
    cordovaApp.handleAndroidBackButton();

    // Handle Statusbar
    cordovaApp.handleSplashscreen();

    // Handle Keyboard
    cordovaApp.handleKeyboard();
  },
};
export default cordovaApp;