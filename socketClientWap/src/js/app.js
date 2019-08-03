// Import Vue
import Vue from 'vue';

// Import Framework7
import Framework7 from './framework7-custom.js';

// Import Framework7-Vue Plugin
import Framework7Vue from 'framework7-vue/framework7-vue.esm.bundle.js';

// Import Framework7 Styles
import '../css/framework7-custom.less';

// Import Icons and App Custom Styles
import '../css/icons.css';
import '../css/app.less';

// Import Implements Routes
import './socket.js';

// Import App Component
import App from '../components/app.vue';


// Init Framework7-Vue Plugin
Framework7.use(Framework7Vue);

// Init App
app = new Vue({
	el: '#app',
	render: (h) => h(App),

	// Register App Component
	components: {
		app: App
	},
	methods: {
		getF7() {
			return this.$children[0].getF7();
		}
	}
});