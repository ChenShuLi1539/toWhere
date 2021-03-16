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
            <div class="playerMessage-2players" v-if="players.length===2" @click="chooseThisPlayer((parseInt(myIndex)+1)%players.length)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)+1)%players.length].id}">
                <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                </div>
                <div class="playerMessage-role">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                <div class="playerMessage-buff"></div>
            </div>

            <div  class="playerMessage-morePlayers-top" v-if="players.length>2">
              <div class="playerMessage" v-for="(each,index) in players" v-bind:key="index"  @click="chooseThisPlayer(index)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[index].id}">
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
              <div class="playerMessage-morePlayers-mid-left"  @click="chooseThisPlayer((parseInt(myIndex)+1))%players.length" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)+1)%players.length].id}">
                  <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                  </div>
                  <div class="playerMessage-role">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                  <div class="playerMessage-buff"></div>
              </div>

              <div class="playerMessage-morePlayers-mid-right"  @click="chooseThisPlayer((parseInt(myIndex)-1)%players.length)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)-1)%players.length].id}">
                  <div class="playerMessage-header">
                    <div class="playerMessage-header-left">{{players[(parseInt(myIndex)-1)%players.length].name}}</div>
                    <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)-1)%players.length].mood}}</div>
                  </div>
                  <div class="playerMessage-role">{{players[(parseInt(myIndex)-1)%players.length].role.name}}</div>
                  <div class="playerMessage-buff"></div>
              </div>
            </div>

            <div class="selectProjectArea" v-show="selectedStudyCard">
                <div class="willBeChosenProject" v-for="(each,index) in selectedProject" v-bind:key="index" v-bind:class="{haveBeenSelected: selectedProject[index] === chosenProject}" @click="chooseThisProject(index)">
                    {{each}}
                </div>
            </div>

            <div class="selectLevelArea" v-show="selectedStudyCard">
                <div class="willBeChosenLevel" v-for="(each,index) in levels" v-bind:key="index" v-bind:class="{haveBeenSelected: index === chosenLevel}" @click="chooseThisLevel(index)">
                    {{each}}
                </div>
            </div>

            <div class="buttonArea">
                <el-button type="primary" round class="useCardButton-true" v-if="selectedCard" @click="useCard()">使用</el-button>
                <el-button type="danger" round class="useCardButton-false" v-if="selectedCard" @click="clearUseButton()">取消</el-button>
            </div>
            <el-button type="primary" round class="turnoverButton" v-if="isturnStart" @click="turnOver()">回合结束</el-button>
        </div>
        <div class="aside"></div>
    </el-main>
    <el-footer height='200px' class="footer">
        <div class="buffArea"></div>
        <div class="cardArea" v-if="AllChosen">
          <div class="card" v-for="(each,index) in players[myIndex].cards" v-bind:key="index" tabindex="0" @click="choosingCard(index)" :title="players[myIndex].cards[index].description">
            {{each.name}}
          </div>
        </div>
        <div class="roleArea"  v-if="AllChosen" @click="chooseThisPlayer(myIndex)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[myIndex].id}">
          <div class="roleArea-header">
            <div class="roleArea-header-left">{{players[myIndex].name}}</div>
            <div class="roleArea-header-right">❤×{{this.players[this.myIndex].mood}}</div>
          </div>
          <div class="roleArea-footer">{{this.players[this.myIndex].role.name}}</div>
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

    <div class="gameoverBackground" v-if="isGameover">
      <el-table
      :data="finalData"
      style="width: 60%;
            margin: 50px auto">
        <el-table-column
          prop="role.name"
          label="角色"
          width="180">
        </el-table-column>
        <el-table-column
          prop="projectScore"
          label="学习项目分数"
          width="180">
        </el-table-column>
        <el-table-column
          prop="moodScore"
          label="心情值分数"
          width="180">
        </el-table-column>
        <el-table-column
          prop="cardScore"
          label="使用牌分数">
        </el-table-column>
        <el-table-column
          prop="treasureScore"
          label="名品分数">
        </el-table-column>
        <el-table-column
          prop="totalScore"
          label="总分">
        </el-table-column>
      </el-table>
       <el-button type="primary" round class="restartButton" @click="restart()">再来一局</el-button>
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
      isturnStart: false,
      roles: [],
      players: [],
      myIndex: '',

      // 以下数值是出牌时的参数
      chosenCard: -1,
      selectedProject: [],
      levels: ['F', 'E', 'D', 'C', 'B', 'A'],
      selectedCard: false,
      selectedStudyCard: false,
      chosenPlayerId: 0,
      chosenLevel: -1,
      chosenProject: '',

      isGameover: false,
      finalData: []
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
              break
            case 'AllChosen':
              _self.$message.success('游戏开始')
              _self.AllChosen = true
              break
            case 'turnStartStage':
              _self.$message(_self.selectPlayerById(data.id).role.name + '开始ta的回合')
              if (data.id + '' === _self.myId + '') _self.isturnStart = true
              if (data.id === _self.myId) {
                _self.socket.send(JSON.stringify({
                  type: 'drawCardStage',
                  id: parseInt(_self.myId)
                }))
              }
              break
            case 'disCardStage':
              if (data.id === _self.myId) {
                _self.socket.send(JSON.stringify({
                  type: 'turnOverStage',
                  id: parseInt(_self.myId)
                }))
              }

              break
            case 'turnOverStage':
              if (data.id === _self.myId) {
                _self.socket.send(JSON.stringify({
                  type: 'turnStartStage',
                  id: _self.players[(parseInt(_self.myIndex) + 1) % _self.players.length].id
                }))
              }

              break
            case 'studySuccess':
              _self.$message.success(data.text)
              break
            case 'studyFail':
              _self.$message.error(data.text)
              break
            case 'gameover':
              _self.finalData = JSON.parse(data.data)
              _self.isGameover = true
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
        if (this.players[e].id + '' === id + '') {
          return this.players[e]
        }
      }
      return null
    },
    chooseThisProject: function (index) {
      this.chosenProject = this.selectedProject[index]
    },
    chooseThisLevel: function (index) {
      this.chosenLevel = index
      let canUse = false
      switch (index) {
        case 0:
          if (this.players[this.myIndex].mood > 0.5) {
            canUse = true
          }
          break
        case 1:
          if (this.players[this.myIndex].mood > 1.5) {
            canUse = true
          }
          break
        case 2:
          if (this.players[this.myIndex].mood > 2.7) {
            canUse = true
          }
          break
        case 3:
          if (this.players[this.myIndex].mood > 3.5) {
            canUse = true
          }
          break
        case 4:
          if (this.players[this.myIndex].mood > 4.4) {
            canUse = true
          }
          break
        case 5:
          if (this.players[this.myIndex].mood > 5.3) {
            canUse = true
          }
          break
        default:
          break
      }
      if (!canUse) {
        this.chosenLevel = -1
        this.$message.error('你当前的心情值不足以学习此等级')
      }
    },
    chooseThisPlayer: function (index) {
      console.log(index)
      this.chosenPlayerId = this.players[index].id
    },
    choosingCard: function (index) {
      let canUse = true
      this.chosenCard = index
      this.selectedCard = false
      this.selectedStudyCard = false
      this.chosenPlayerId = 0
      this.chosenProject = ''
      switch (this.players[this.myIndex].cards[index].name) {
        case '恶作剧':
          if (this.players[this.myIndex].role.natures[2].level < 5) { canUse = false }
          break
        case '整蛊':
          if (this.players[this.myIndex].role.natures[2].level < 8) { canUse = false }
          break
        case '振奋':
          if (this.players[this.myIndex].role.natures[1].level < 2) { canUse = false }
          break
        case '共渡难关':
          if (this.players[this.myIndex].role.natures[1].level < 7) { canUse = false }
          break
        case '灵感':
          if (this.players[this.myIndex].role.natures[0].level < 5) { canUse = false }
          break
        case '无独有偶':
          if (this.players[this.myIndex].role.natures[0].level < 7) { canUse = false }
          break
        case '底力爆发':
          if (this.players[this.myIndex].role.natures[4].level < 3) { canUse = false }
          break
        case '柳暗花明':
          if (this.players[this.myIndex].role.natures[4].level < 9) { canUse = false }
          break
        case '深呼吸':
          if (this.players[this.myIndex].role.natures[3].level < 6) { canUse = false }
          break
        case '赠人玫瑰':
          if (this.players[this.myIndex].role.natures[3].level < 8) { canUse = false }
          break
        case '留一手':
          if (this.players[this.myIndex].role.natures[5].level < 4) { canUse = false }
          break
        case '潜心修学':
          if (this.players[this.myIndex].role.natures[5].level < 9) { canUse = false }
          break
        default:
          break
      }
      if (canUse) {
        this.selectedCard = true
        if (this.players[this.myIndex].cards[index].type === 0) {
          this.selectedStudyCard = true
          switch (this.players[this.myIndex].cards[index].name) {
            case '学习体育':
              this.selectedProject = ['球类', '田径', '水上项目', '武术']
              break
            case '学习音乐':
              this.selectedProject = ['乐器', '声乐']
              break
            case '学习文学':
              this.selectedProject = ['小说', '散文', '报道']
              break
            case '学习美术':
              this.selectedProject = ['绘画', '服饰', '建筑']
              break
            case '学习自然':
              this.selectedProject = ['动物培育', '园艺', '天文', '地理']
              break
            case '学习生活':
              this.selectedProject = ['八卦', '手工', '厨艺', '游戏']
              break
            default:
              break
          }
        }
      } else {
        this.$message.error('你未达到这张牌的使用条件')
      }
    },
    clearUseButton: function () {
      this.selectedCard = false
      this.selectedStudyCard = false
      this.chosenPlayerId = 0
    },
    useCard: function () {
      let canUse = true
      if (this.chosenCard < 0) {
        canUse = false
        this.$message.error('选择卡牌时出错')
      }

      if (this.players[this.myIndex].cards[this.chosenCard].type === 0) {
        if (this.chosenPlayerId === 0) {
          canUse = false
          this.$message.error('未选择正确的使用玩家')
        }
        if (this.chosenPlayerId + '' === this.myId + '') {
          canUse = false
          this.$message.error('不能指定自己为学习对象')
        }
        if (this.chosenProject === '') {
          canUse = false
          this.$message.error('未选择正确的项目')
        }
        switch (this.chosenLevel) {
          case 0:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 0.1) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          case 1:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 0.6) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          case 2:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 1.1) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          case 3:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 2.1) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          case 4:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 3.1) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          case 5:
            if (this.selectPlayerById(this.chosenPlayerId).mood < 4.1) {
              canUse = false
              this.$message.error('对方当前的心情值不足以学习此等级')
            }
            break
          default:
            canUse = false
            this.$message.error('未选择等级')
            break
        }
      }
      if (this.players[this.myIndex].cards[this.chosenCard].type === 3) {
        if (this.players[this.myIndex].cards[this.chosenCard].name === '共渡难关' || this.players[this.myIndex].cards[this.chosenCard].name === '赠人玫瑰') {
          if (this.chosenPlayerId === 0) {
            canUse = false
            this.$message.error('未选择正确的使用玩家')
          }
          if (this.chosenPlayerId + '' === this.myId + '') {
            canUse = false
            this.$message.error('不能指定自己为目标')
          }
        } else if (this.players[this.myIndex].cards[this.chosenCard].name === '无独有偶' || this.players[this.myIndex].cards[this.chosenCard].name === '底力爆发' || this.players[this.myIndex].cards[this.chosenCard].name === '潜心修学') {

        } else {
          if (this.chosenPlayerId === 0) {
            canUse = false
            this.$message.error('未选择正确的使用玩家')
          }
        }
      }
      if (canUse) {
        switch (this.players[this.myIndex].cards[this.chosenCard].type) {
          case 0:
            this.socket.send(JSON.stringify({
              type: '学习牌',
              id: this.myId,
              id2: this.chosenPlayerId,
              cardName: (this.players[this.myIndex].cards[this.chosenCard].name),
              name: this.chosenProject,
              level: this.chosenLevel
            }))
            break
          case 3:
            this.socket.send(JSON.stringify({
              type: '计策牌',
              id: this.myId,
              id2: this.chosenPlayerId,
              name: this.players[this.myIndex].cards[this.chosenCard].name
            }))
            break
          case 4:
            this.socket.send(JSON.stringify({
              type: '机缘牌',
              id: this.myId,
              name: (this.players[this.myIndex].cards[this.chosenCard].name)
            }))
            break
          default:
            break
        }
        this.clearUseButton()
      }
    },
    turnOver: function () {
      this.isturnStart = false
      this.clearUseButton()
      this.socket.send(JSON.stringify({
        type: 'disCardStage',
        id: parseInt(this.myId)
      }))
    },
    restart: function () {
      location.reload()
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
    height: 180px;
    width: 140px;
    border: 1px solid black;
    border-radius: 5px;
    margin: 20px auto 180px 600px;
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
    line-height: 100px;
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
    height: 180px;
    width: 140px;
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
    height: 180px;
    width: 140px;
    border: 1px solid black;
    border-radius: 5px;
}
.playerMessage-morePlayers-mid-right {
    margin: 10px 40px auto auto;
    height: 180px;
    width: 140px;
    border: 1px solid black;
    border-radius: 5px;
}
.selectProjectArea {
    height: 20px;
    width: 600px;
    display: flex;
    margin: 0 auto 0 300px;
    border: 1px solid black;
}
.willBeChosenProject {
   height: 100%;
   text-align: center;
   margin: auto;
}
.willBeChosenProject:hover {
   border: 1px solid black;
   cursor: pointer;
}
.selectLevelArea {
    height: 20px;
    width: 600px;
    display: flex;
    margin: 10px auto 0 300px;
    border: 1px solid black;
}
.willBeChosenLevel {
   height: 100%;
   text-align: center;
   margin: auto;
}
.willBeChosenLevel:hover {
   border: 1px solid black;
   cursor: pointer;
}
.haveBeenSelected {
  color: red;
}
.haveBeenSelectedRole {
  border:2px solid red;
}
.buttonArea {
  width: 600px;
  margin: 10px auto 0 300px;
  display: flex;
}
.useCardButton-true {
  margin: auto auto auto 20px;
}
.useCardButton-false {
  margin: auto 20px auto auto;
}
.turnoverButton {
  position: absolute;
  top: 480px;
  left: 1050px;
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
    background-color: white;

}
.card:hover {
  margin: -20px 5px 40px;
}
.card:focus {
  border: 4px solid #46A3FF;
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
.gameoverBackground {
    position: absolute;
    left: 0;
    top: 0;
    width: 1526px;
    height: 730px;
    background-color: rgba(255 ,255,255, 0.9);
}
.restartButton {
  margin: 20px auto auto 600px;
}
</style>
