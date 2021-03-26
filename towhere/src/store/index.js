import Vue from 'vue'
import Vuex from 'vuex'
import CryptoJS from 'crypto-js'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    key: 'GOF',
    iv: 'gof',
    eventBus: new Vue(),
    HTTPBaseURL: 'http://192.168.0.106:8085',
    // WebSocketBaseURL: 'wss://www.lanwenjun.ltd:8085'
    // HTTPBaseURL: 'http://192.168.43.48:8085',
    // WebSocketBaseURL: 'ws://192.168.43.48:8085'
    // HTTPBaseURL: 'http://localhost:8085',
    WebSocketBaseURL: 'ws://192.168.0.106:8085'
  },
  getters: {
    encrypt: (state) => (text) => {
      return CryptoJS.AES.encrypt(text, state.key, {
        iv: state.iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
      }).toString()
    },
    decrypt: (state) => (text) => {
      if (text) {
        return CryptoJS.AES.decrypt(text, state.key, {
          iv: state.iv,
          mode: CryptoJS.mode.CBC,
          padding: CryptoJS.pad.Pkcs7
        }).toString(CryptoJS.enc.Utf8)
      } else {
        return null
      }
    },
    checkCookie (state, getters) {
      return function a (self, expectId) {
        console.log('检查')
        const isLogin = getters.decrypt(self.$cookies.get('login'))
        let pushPath = '/login'
        if (isLogin) {
          const id = getters.decrypt(self.$cookies.get('id'))
          if (id !== expectId) {
            pushPath = '/home'
            if (id === 'S') {
              pushPath += '/student'
            } else if (id === 'T') {
              pushPath += '/teacher'
            } else if (id === 'MX') {
              pushPath += '/administerX'
            } else if (id === 'MY') {
              pushPath += '/administerY'
            } else {
              pushPath = '/login'
            }
            self.$router.push(pushPath)
          }
        } else {
          self.$router.push(pushPath)
        }
      }
    }
  },
  mutations: {},
  actions: {},
  modules: {}
})
