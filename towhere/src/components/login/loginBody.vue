<template>
  <div class="login-body">
    <div class="login-body-left" :style="{backgroundImage:'url(' + img + ')'}">
      <div class="login-body-left-text">{{welcomeText}}<br>向何方（toWhere）</div>
    </div>
    <div class="login-body-right">
      <component v-bind:is="currentMenu"></component>

    </div>
  </div>
</template>

<script>
import LoginMenu from '@/components/login/loginMenu'
import RegisterMenu from '@/components/login/registerMenu'
export default {
  name: 'loginBody',
  components: { RegisterMenu, LoginMenu },
  data: function () {
    return {
      img: require('@/assets/蓝色纹理.jpg'),
      currentMenu: LoginMenu
    }
  },
  computed: {
    welcomeText: function () {
      if (this.currentMenu === LoginMenu) {
        return '欢迎登录'
      } else {
        return '欢迎注册'
      }
    }
  },
  mounted () {
    this.$store.state.eventBus.$on('switchMode', () => {
      if (this.currentMenu === LoginMenu) {
        this.currentMenu = RegisterMenu
      } else {
        this.currentMenu = LoginMenu
      }
    })
  }
}
</script>

<style scoped>
.login-body {
  height: 600px;
  width: 920px;
  margin: 60px auto;
  display: flex;
}

.login-body-left {
  height: 100%;
  width: 380px;
  border-radius: 10px 0 0 10px;
}

.login-body-left img {
  height: 100%;
  width: 100%;
}

.login-body-left-text {
  padding: 200px 50px 50px 100px;
  font-family: Helvetica, 'Hiragino Sans GB', 'Microsoft Yahei', '微软雅黑', Arial, sans-serif;
  font-size: 28px;
  color: #fff;
  line-height: 50px;
}

.login-body-right {
  height: 100%;
  width: 540px;
  background-color: white;
  border-radius: 0 10px 10px 0;
}

</style>
