<template>
<div>
    <el-container class="gameOutline" v-if="selected==='gameRoom'">
    <el-header height='30px' class="header"></el-header>
    <el-main class="mainArea">
        <div class="gametable">
            <div class="chooseRoleArea" v-show="choosingRole">
                <div class="chooseRoleArea-header">请选择你的游戏角色</div>
                <div class="chooseRoleArea-main" >
                    <div class="roles" v-for="(each,index) in roles" v-bind:key="index" @click="chooseRole(index)">
                        {{each.name}}
                    </div>
                </div>
            </div>
            <div class="playerMessage-2players" v-if="players.length===2">
                <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                </div>
                <div class="playerMessage-role">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                <div class="playerMessage-buff"></div>
            </div>

            <div  class="playerMessage-morePlayers-top" v-if="players.length>2">
              <div class="playerMessage" v-for="(each,index) in players" v-bind:key="index" >
                <div v-if="index!==myIndex&&index!==parseInt(myIndex+1)%players.length&&index!==parseInt(myIndex-1)%players.length">
                  <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[index].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[index].mood}}</div>
                  </div>
                  <div class="playerMessage-role">{{players[index].role.name}}</div>
                  <div class="playerMessage-buff"></div>
                </div>
              </div>
            </div>

            <div class="playerMessage-morePlayers-mid" v-if="players.length>2">
              <div class="playerMessage-morePlayers-mid-left">
                  <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                  </div>
                  <div class="playerMessage-role">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                  <div class="playerMessage-buff"></div>
              </div>

              <div class="playerMessage-morePlayers-mid-right">
                  <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)-1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)-1)%players.length].mood}}</div>
                  </div>
                  <div class="playerMessage-role">{{players[(parseInt(myIndex)-1)%players.length].role.name}}</div>
                  <div class="playerMessage-buff"></div>
              </div>
            </div>
        </div>
        <div class="aside"></div>
    </el-main>
    <el-footer height='200px' class="footer">
        <div class="buffArea"></div>
        <div class="cardArea" v-if="AllChosen">
          <div class="card" v-for="(each,index) in players[myIndex].cards" v-bind:key="index">
            {{each.name}}
          </div>
        </div>
        <div class="roleArea">
          <div class="roleArea-header">
            <div class="roleArea-header-left" v-if="AllChosen">{{players[myIndex].name}}</div>
            <div class="roleArea-header-right" v-if="AllChosen">❤×{{this.players[this.myIndex].mood}}</div>
          </div>
          <div class="roleArea-footer" v-if="AllChosen">{{this.players[this.myIndex].role.name}}</div>
        </div>
    </el-footer>
    </el-container>

    <div class="waitingRoom" v-if="selected==='waitingRoom'">
        <div class="waitingRoomArea">
            <div v-for="each in roommate" v-bind:key="each.id" class="playerMessage">
                <div class="playerName">
                    <strong>{{each.name}}</strong><strong v-if="each.owner">（房主）</strong>
                </div>
            </div>
        </div>
        <el-button  type="primary" round v-show="this.owner&&this.roommate.length>1" class="gameStartButton" @click="gameStart()">开始游戏</el-button>
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
      AllChosen: false,
      roles: [],
      players: [],
      myIndex: ''
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
              break
            case 'playersData':
              _self.players = JSON.parse(data.data)
              for (const e in _self.players) {
                if (_self.players[e].id + '' === _self.myId) { _self.myIndex = e }
              }
              console.log(typeof (_self.myIndex))
              break
            case 'AllChosen':
              _self.$message.success('游戏开始')
              _self.AllChosen = true
              break
            case 'turnStartStage':
              _self.$message(_self.selectPlayerById(data.id).role.name + '开始ta的回合')
              if (data.id === _self.myId) {
                _self.socket.send(JSON.stringify({
                  type: 'drawCardStage',
                  id: parseInt(_self.myId)
                }))
              }
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
    },
    chooseRole: function (index) {
      this.choosingRole = false
      this.socket.send(JSON.stringify({
        type: 'chooseRole',
        id: this.myId,
        name: this.roles[index].name
      }))
    },
    selectPlayerById: function (id) {
      for (const e in this.players) {
        if (this.players[e].id + '' === id) {
          return this.players[e]
        }
      }
      return null
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
    flex: 5;
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
.playerMessage-2players {
    height: 220px;
    width: 180px;
    border: 1px solid black;
    border-radius: 5px;
    margin: 20px auto auto 600px;
}
.playerMessage-header {
    display: flex;
}
.playerMessage-header-left {
    margin: 10px auto auto 10px;
}
.playerMessage-header-right {
    margin: 10px 10px auto auto;
    color: #FF2D2D;
}
.playerMessage-role {
    line-height: 140px;
    text-align: center;
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
}
.playerMessage-morePlayers-top {
    width: 1000px;
    height: 240px;
    display: flex;
}
.playerMessage {
    margin: 10px auto;
    height: 220px;
    width: 180px;
    border: 1px solid black;
    border-radius: 5px;
}
.playerMessage-morePlayers-mid {
    width: 100%;
    height: 240px;
    display: flex;
}
.playerMessage-morePlayers-mid-left {
    margin: 10px auto auto 40px;
    height: 220px;
    width: 180px;
    border: 1px solid black;
    border-radius: 5px;
}
.playerMessage-morePlayers-mid-right {
    margin: 10px 40px auto auto;
    height: 220px;
    width: 180px;
    border: 1px solid black;
    border-radius: 5px;
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
    flex: 4;
    display: flex;
}
.card {
  width: 130px;
  height: 180px;
  margin: auto 5px;
  border: 1px solid black;
  border-radius: 10px;
  text-align: center;
  line-height: 180px;
  font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;

}
.card:hover {
  margin: -20px 5px 40px;
}
.roleArea {
    border: solid 1px yellow;
    flex: 1;
}
.roleArea-header {
    display: flex;
}
.roleArea-header-left {
    margin: 10px auto auto 10px;
}
.roleArea-header-right {
    margin: 10px 10px auto auto;
    color: #FF2D2D;
}
.roleArea-footer {
    line-height: 160px;
    text-align: center;
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
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
