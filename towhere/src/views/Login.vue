<template>
  <div class="login">
    <login-body>

    </login-body>
    <img :src="require('@/assets/登录页面背景.png')">
  </div>
</template>

<script>
import LoginBody from '@/components/login/loginBody'
export default {
  name: 'Login',
  components: { LoginBody }
}
</script>

<style scoped>
.login {
  height: 686px;
  width: 100%;
}

.login img {
  height: 100%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  opacity: 0.9;
}
</style>
