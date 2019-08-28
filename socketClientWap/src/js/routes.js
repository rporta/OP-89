import HomePage from '../pages/home.vue';
import AboutPage from '../pages/about.vue';
import ConfigApp from '../pages/configApp.vue';
import ConfigPerfil from '../pages/configPerfil.vue';
import ConfigClient from '../pages/configClient.vue';
import InfoClient from '../pages/infoClient.vue';
import RightPanel from '../pages/rightPanel.vue';
import SocketClient from '../pages/socketClient.vue';
import SocketClientPopup from '../pages/socketClientPopup.vue';
import ConfigProcessUrl from '../pages/configProcessUrl.vue';
import SendProcessUrl from '../pages/sendProcessUrl.vue';
import GetProcessUrl from '../pages/getProcessUrl.vue';
import ConfigDevice from '../pages/configDevice.vue';
import ConfigPermisos from '../pages/configPermisos.vue';
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
    path: '/configClient/:pSocketId',
    component: ConfigClient,
  },

  {
    path: '/infoClient/:pSocketId',
    component: InfoClient,
  },

  {
    path: '/rightPanel/',
    component: RightPanel,
  },

  {
    path: '/socketClient/:pSocketId',
    component: SocketClient,
  },

  {
    path: '/socketClientPopup/',
    component: SocketClientPopup,
  },

  {
    path: '/configProcessUrl/:pSocketId',
    component: ConfigProcessUrl,
  },

  {
    path: '/sendProcessUrl/:pSocketId',
    component: SendProcessUrl,
  },

  {
    path: '/getProcessUrl/:pSocketId',
    component: GetProcessUrl,
  },

  {
    path: '/configDevice/',
    component: ConfigDevice,
  },


  {
    path: '/configPermisos/',
    component: ConfigPermisos,
  },

  {
    path: '(.*)',
    component: NotFoundPage,
  },
];

export default routes;