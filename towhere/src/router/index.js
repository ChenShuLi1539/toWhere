import Vue from 'vue'
import VueRouter from 'vue-router'
import autoRouter from 'vue-router-auto'
import Home from '@/views/Home'

Vue.use(VueRouter)

const routes = autoRouter({
  rc: require.context('@/views', true, /\.vue$/),

  // 重定向到哪个路由
  redirect: '/Login'

})

console.log(routes)

routes.push({
  path: '*', component: Home
})

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
