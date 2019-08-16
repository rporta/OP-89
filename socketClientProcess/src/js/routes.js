import HomePage from '../pages/home.vue';
import AboutPage from '../pages/about.vue';
import ConfigApp from '../pages/configApp.vue';
import ConfigPerfil from '../pages/configPerfil.vue';
import ConfigClient from '../pages/configClient.vue';
import InfoClient from '../pages/infoClient.vue';
import RightPanel from '../pages/rightPanel.vue';
import LeftPanel from '../pages/leftPanel.vue';
import SocketClient from '../pages/socketClient.vue';
import SocketClientPopup from '../pages/socketClientPopup.vue';
import ConfigProcessUrl from '../pages/configProcessUrl.vue';
import SendProcessUrl from '../pages/sendProcessUrl.vue';
import GetProcessUrl from '../pages/getProcessUrl.vue';
import Terminal from '../pages/terminal.vue';
import Captura from '../pages/captura.vue';
import ListaDeEventos from '../pages/listaDeEventos.vue';
import VistaDesktop from '../pages/vistaDesktop.vue';
import NotFoundPage from '../pages/404.vue';

var routes = [

  {
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
    path: '/leftPanel/',
    component: LeftPanel,
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
    path: '/terminal/:pSocketId/:pNavbarDescktop',
    component: Terminal,
  },

  {
    path: '/captura/:pSocketId/:pNavbarDescktop',
    component: Captura,
  },

  {
    path: '/listaDeEventos/:pSocketId/:pNavbarDescktop',
    component: ListaDeEventos,
  },

  {
    path: '/vistaDesktop/:pSocketId',
    component: VistaDesktop,
  },

  {
    path: '(.*)',
    component: NotFoundPage,
  },
];

export default routes;