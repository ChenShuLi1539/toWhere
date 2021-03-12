<template>
  <div class="login-body-valid">
    <div class="login-body-right-head">账号密码登录</div>
    <div class="login-body-right-bar"></div>
    <div class="login-body-right-context">
      <div class="login-input">
        <label class="login-label">账号：</label>
        <el-input
          placeholder="请输入账号"
          v-model="account">
        </el-input>
      </div>
      <div class="login-input">
        <label class="login-label">密码：</label>
        <el-input
          placeholder="请输入密码"
          show-password
          v-model="password"
          @keyup.enter.native="buttonClicked">
        </el-input>
      </div>
      <el-button type="primary" @click="buttonClicked()">登录</el-button>
    </div>
    <div class="login-body-right-switch">
      <el-link type="primary" @click="switchMode()">没有账号？点击注册</el-link>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'loginMenu',
  data: function () {
    return {
      account: '',
      password: '',
      identity: ''
    }
  },
  methods: {
    checkInput: function () {
      let message
      if (this.account === '') {
        message = '账号不能为空'
      } else if (this.password === '') {
        message = '密码不能为空！'
      } else {
        return true
      }
      this.$message.error(message)
      return false
    },
    switchMode: function () {
      this.$store.state.eventBus.$emit('switchMode')
    },
    buttonClicked: function () {
      // console.log('使用用户名：' + this.username + '\n密码：' + this.password)
      if (this.checkInput()) {
        const account = this.account
        const password = this.password
        axios
          .post(this.$store.state.HTTPBaseURL + '/account/login/', { account, password })
          .then(resp => {
            const { data, status } = resp
            console.log(data)
            console.log(status)
            // 如果正确，保存cookie
            if (status === 200) {
              let status
              if (data !== '') {
                status = true
              } else {
                status = false
                this.$message.error('账号或密码错误！')
              }
              if (status) {
                this.$cookies.set('account', this.$store.getters.encrypt(data.account))
                this.$cookies.set('name', this.$store.getters.encrypt(data.name))
                this.$cookies.set('id', this.$store.getters.encrypt(data.id + ''))
                // 跳转到主页。根据身份不同跳转到不同的主页，主页create时检查cookie身份，若不对应也要跳转
                // if (this.identity === 'S') this.$router.push('home/student')
                // else if (this.identity === 'T') this.$router.push('home/teacher')
                // else if (this.identity === 'MX') this.$router.push('home/administerX')
                // else if (this.identity === 'MY') this.$router.push('home/administerY')
                // else this.$message.error('跳转出错！')
                this.$router.push('game/Main')
              }
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.login-body-valid {
  margin: 100px auto auto auto;
  width: 80%;
  text-align: center;
}

.login-body-right-head {
  color: rgb(55, 171, 255);
  font-size: 22px;
  padding-bottom: 10px;
}

.login-body-right-bar {
  width: 200px;
  height: 3px;
  margin: 0 auto 0 auto;
  background-color: rgb(55, 171, 255);
}

.login-body-right-context {
  margin-top: 30px;
  height: 300px;
}

.login-label {
  font-size: 16px;
  font-weight: 400;
  line-height: normal;
  white-space: nowrap;
  margin: 46px 25px 39px 20px;
  color: gray;
  text-align: left;
}

.login-input {
  display: flex;
}

/deep/ .el-input__inner {
  position: relative;
  top: 30px;
  height: 46px;
  border: 1px solid grey;
  outline: none;
  border-radius: 10px;
}

/deep/ .el-input__inner:focus {
  border: 1px solid white;
  box-shadow: 0 0 0 2px rgb(55, 171, 255);
}

/deep/ .el-button {
  position: relative;
  left: 12px;
  width: 96%;
  height: 50px;
  font-size: 18px;
  letter-spacing: 20px;
  border-radius: 10px;
}

.login-body-right-switch {
  float: right;
}

</style>
