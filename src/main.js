import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import {ValidationProvider} from 'vee-validate'
import {ValidationObserver} from 'vee-validate'

Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.component('ValidationProvider', ValidationProvider)
Vue.component('ValidationObserver', ValidationObserver)

axios.defaults.baseURL = 'http://10.53.68.186:3000/';
axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*"
axios.defaults.headers.common['Access-Control-Allow-Headers'] = 'Authorization, Content-Type'

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
