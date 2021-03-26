import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import VueCookies from 'vue-cookies'

Vue.use(ElementUI)
Vue.use(VueCookies)

Vue.config.productionTip = false

// axios.defaults.withCredentials = true
// // axios.defaults.crossDomain=true
// Vue.config.productionTip = false
// Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
