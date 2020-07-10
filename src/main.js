import Vue from 'vue'
import App from './App.vue'
import { router } from './router'
import { store } from './store/index.js'
import axios from 'axios'
import vuetify from './plugins/vuetify'
import {ValidationProvider} from 'vee-validate'
import {ValidationObserver} from 'vee-validate'

//위지윅 에디터
import 'tui-editor/dist/tui-editor.css'
import 'tui-editor/dist/tui-editor-contents.css'
import 'codemirror/lib/codemirror.css'
import { Editor } from '@toast-ui/vue-editor'
import { Viewer } from '@toast-ui/vue-editor'

Vue.config.productionTip = false
Vue.prototype.$http = axios
Vue.component('ValidationProvider', ValidationProvider)
Vue.component('ValidationObserver', ValidationObserver)
Vue.component('editor', Editor)
Vue.component('viewer', Viewer)

axios.defaults.baseURL = 'http://10.53.68.186:80/';
axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*"
axios.defaults.headers.common['Access-Control-Allow-Headers'] = 'Authorization, Content-Type'

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
