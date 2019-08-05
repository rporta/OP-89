import HomePage from '../pages/home.vue';
import AboutPage from '../pages/about.vue';
import ConfigApp from '../pages/configApp.vue';
import ConfigPerfil from '../pages/configPerfil.vue';
import ConfigClient from '../pages/configClient.vue';
import InfoClient from '../pages/infoClient.vue';
import RightPanel from '../pages/rightPanel.vue';
import SocketClient from '../pages/socketClient.vue';
import SocketClientPopup from '../pages/socketClientPopup.vue';
import ProcessUrl from '../pages/processUrl.vue';
import NotFoundPage from '../pages/404.vue';

var routes = [{
    path: '/',
    component: HomePage,
  },

  {
    path: '/about/',
    component: AboutPage,
  },

  {
    path: '/configApp/',
    component: ConfigApp,
  },

  {
    path: '/configPerfil/',
    component: ConfigPerfil,
  },

  {
    path: '/configClient/',
    component: ConfigClient,
  },

  {
    path: '/infoClient/',
    component: InfoClient,
  },

  {
    path: '/rightPanel/',
    component: RightPanel,
  },

  {
    path: '/socketClient/',
    component: SocketClient,
  },

  {
    path: '/socketClientPopup/',
    component: SocketClientPopup,
  },

  {
    path: '/processUrl/',
    component: ProcessUrl,
  },

  {
    path: '(.*)',
    component: NotFoundPage,
  },
];

export default routes;