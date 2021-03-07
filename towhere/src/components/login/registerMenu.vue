<template>
  <div class="login-body-valid">
        <div class="login-body-right-head">注册新账号</div>
        <div class="login-body-right-bar"></div>
        <div class="login-body-right-context">
          <div class="login-input">
            <label class="login-label">编号:</label>
            <el-input
              placeholder="请输入学号/教师号"
              v-model="id">
            </el-input>
          </div>
          <div class="login-input">
            <label class="login-label">用户名:</label>
            <el-input
              placeholder="请输入用户名"
              v-model="username">
            </el-input>
          </div>
          <div class="login-input">
            <label class="login-label">邮箱:</label>
            <el-input
              placeholder="请输入邮箱"
              v-model="email">
            </el-input>
          </div>
          <div class="login-input">
            <label class="login-label">密 码：</label>
            <el-input
              placeholder="请输入密码"
              show-password
              v-model="password">
            </el-input>
          </div>
          <div class="login-input">
            <label class="login-label">确认密码:</label>
            <el-input
              placeholder="请确认密码"
              show-password
              v-model="passwordAgain"
              @keyup.enter.native="buttonClicked">
            </el-input>
          </div>
          <div>
            <el-radio v-model="radio" :label="1" border>我是学生</el-radio>
            <el-radio v-model="radio" :label="2" border>我是老师</el-radio>
          </div>
          <el-button type="primary" @click="buttonClicked()">注册</el-button>
        </div>
        <div class="login-body-right-switch">
          <el-link type="primary" @click="switchMode()">已有账号？点此登录</el-link>
        </div>
      </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'registerMenu',
  data: function () {
    return {
      id: '',
      username: '',
      email: '',
      password: '',
      passwordAgain: '',
      radio: 0,
      status: ''
    }
  },
  methods: {
    checkInput: function () {
      let message
      if (this.id === '') {
        message = '请输入学号/教师号！'
      } else if (this.username === '') {
        message = '请输入用户名！'
      } else if (this.email === '') {
        message = '请输入邮箱！'
      } else if (!/\w+@\w+\.\w+/.test(this.email)) {
        message = '邮箱格式有误！'
      } else if (this.password === '') {
        message = '请输入密码！'
      } else if (this.passwordAgain === '') {
        message = '请输入确认密码！'
      } else if (this.radio === 0) {
        message = '请选择身份！'
      } else if (this.password !== this.passwordAgain) {
        message = '两次输入的密码不一致！'
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
        // 前后端交互
        const name = this.username
        const password = this.password
        const email = this.email
        const id = this.id
        let role
        if (this.radio === 1) {
          role = '学生'
        } else {
          role = '老师'
        }
        axios
          .post(this.$store.state.HTTPBaseURL + '/loginRegist/regist/', { name, password, email, id, role })
          .then(resp => {
            const { data, status } = resp
            this.status = status
            if (status === 200) {
              // console.log(data)
              if (data === '注册成功') {
                this.$message.success('注册成功，请登录')
                this.$store.state.eventBus.$emit('switchMode')
              } else {
                this.$message.error(data)
              }
            } else {
              this.$message.error('无法注册，服务器错误')
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.login-body-valid {
  margin: 30px auto auto auto;
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
  margin-top: 10px;
  height: 300px;
}

.login-label {
  font-size: 16px;
  font-weight: 400;
  width: 90px;
  line-height: normal;
  white-space: nowrap;
  margin: 30px 20px 22px 20px;
  color: gray;
  text-align: left;
}

.login-input {
  display: flex;
}

/deep/ .el-input__inner {
  position: relative;
  top: 20px;
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
  top: 20px;
  width: 96%;
  height: 50px;
  font-size: 18px;
  letter-spacing: 20px;
  border-radius: 10px;
}

/deep/ .el-radio {
  margin-top: 10px;
  width: 160px;
}

.login-body-right-switch {
  margin-top: 186px;
  float: right;
}

</style>
