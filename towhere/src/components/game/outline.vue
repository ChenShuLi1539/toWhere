<template>
<div>
    <el-container class="gameOutline" v-show="selected==='gameRoom'">
    <el-header height='30px' class="header"></el-header>
    <el-main class="mainArea">
        <div class="gametable">
            <div class="chooseRoleArea" v-show="choosingRole">
                <div class="chooseRoleArea-header">请选择你的游戏角色</div>
                <div class="chooseRoleArea-main" >
                    <div class="roles" v-for="(each,index) in roles" v-bind:key="index">
                        {{each.name}}
                    </div>
                </div>
            </div>
        </div>
        <div class="aside"></div>
    </el-main>
    <el-footer height='120px' class="footer">
        <div class="buffArea"></div>
        <div class="cardArea"></div>
        <div class="roleArea"></div>
    </el-footer>
    </el-container>

    <div class="waitingRoom" v-show="selected==='waitingRoom'">
        <div class="waitingRoomArea">
            <div v-for="each in roommate" v-bind:key="each.id" class="playerMessage">
                <div class="playerName">
                    <strong>{{each.name}}</strong><strong v-if="each.owner">（房主）</strong>
                </div>
            </div>
        </div>
        <el-button  type="primary" round v-show="this.owner" class="gameStartButton" @click="gameStart()">开始游戏</el-button>
    </div>
</div>

</template>

<script>
export default {
  name: 'outline',
  data () {
    return {
      selected: 'waitingRoom',
      myId: '',
      socket: null,
      roommate: [],
      owner: false,
      choosingRole: true,
      roles: []
    }
  },
  methods: {
    initWebsocket: function () {
      const _self = this
      if (typeof (WebSocket) === 'undefined') {
        console.log('您的浏览器不支持Websocket')
      } else {
        console.log('您的浏览器支持Websocket')
        console.log(this.$store.state.WebSocketBaseURL)
        const socketUrl = this.$store.state.WebSocketBaseURL + '/game/' + this.myId
        // 连接到后端的url，需要后端进行配合
        if (this.socket !== null) {
          this.socket.close()
          this.socket = null
        }
        this.socket = new WebSocket(socketUrl)
        this.socket.onopen = function () {
          console.log('游戏websocket建立成功')
          _self.socket.send(JSON.stringify({
            type: 'waiting',
            id: _self.$store.getters.decrypt(_self.$cookies.get('id')),
            name: _self.$store.getters.decrypt(_self.$cookies.get('name'))
          }))
        }
        this.socket.onmessage = function (resp) {
          console.log(JSON.parse(resp.data))
          const data = JSON.parse(resp.data)
          switch (data.type) {
            case 'waiting':
              _self.roommate = JSON.parse(data.data)
              for (const e in _self.roommate) {
                if (_self.roommate[e].id + '' === _self.myId && _self.roommate[e].owner) {
                  _self.owner = true
                }
              } break
            case 'gameStart':
              _self.selected = 'gameRoom'
              break
            case 'chooseRole':
              for (const e in data) {
                if (e === _self.myId) { _self.roles = JSON.parse(data[e]) }
              }
              console.log(_self.roles)
              break
            default:
              break
          }
        }
        this.socket.onclose = function () {
          console.log('游戏websocket关闭')
        }
        this.socket.onerror = function () {
          console.log('游戏websocket发生了错误')
        }
      }
    },
    gameStart: function () {
      this.socket.send(JSON.stringify({
        type: 'gameStart'
      }))
    }
  },
  mounted () {
    this.myId = this.$store.getters.decrypt(this.$cookies.get('id'))
    this.owner = false
    this.initWebsocket()
  }
}
</script>

<style scoped>
.header {
    border: black 1px solid;
}
.gameOutline {
    width: 1526px;
    height: 730px;
    border: red 1px solid;
    margin: 0;
}
.gametable {
    border: solid 1px blue;
    flex: 4;
}
.chooseRoleArea {
    width: 600px;
    height: 400px;
    margin: 60px 0 0 400px;
    background-color: #FFC78E;
}
.chooseRoleArea-header {
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
    text-align: center;
    height: 100px;
    line-height: 100px;
}
.chooseRoleArea-main {
    display: flex;
    width: 100%;
}
.roles {
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
    text-align: center;
    line-height: 300px;
    flex: 1;
}
.roles:hover {
    border: solid 2px black;
}
.mainArea {
    display: flex;
    padding: 0;
}
.aside {
    border: solid 1px blue;
    flex: 1;
}
.footer {
    display: flex;
    padding: 0;
}
.buffArea {
    border: solid 1px yellow;
    flex: 1;
}
.cardArea {
    border: solid 1px yellow;
    flex: 3;
}
.roleArea {
    border: solid 1px yellow;
    flex: 1;
}
.waitingRoomArea {
    width: 1280px;
    height: 600px;
    border: solid 1px black;
    margin: 40px auto;
    display: flex;
}
.playerMessage {
    width: 300px;
    height: 250px;
    border: solid 1px black;
    border-radius: 10px;
    margin: auto;
}
.playerName {
    font-size: 13px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
}
.gameStartButton {
    margin-left: 1300px;
}
</style>
