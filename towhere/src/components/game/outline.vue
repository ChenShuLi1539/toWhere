<template>
<div>
    <el-container class="gameOutline" v-if="selected==='gameRoom'">
    <el-header height='30px' class="header"><el-button @click="cantTurnoverBug()">回合结束卡住了点此处</el-button></el-header>
    <el-main class="mainArea">
        <div class="gametable">
            <div class="chooseRoleArea" v-show="choosingRole">
              <div class="chooseRoleArea-header">请选择你的游戏角色</div>
              <div class="chooseRoleArea-main" >
                    <el-tooltip placement="top"   v-for="(each,index7) in roles" v-bind:key="index7">
                      <div slot="content"><strong>{{each.name}}    性别：</strong><strong v-if="each.sex === 0">女</strong><strong v-if="each.sex === 1">男</strong>
                        <div style="display:flex">天性：<div v-for="(e,index) in each.natures" v-bind:key="index" style="margin-right:5px">{{each.natures[index].name}}：{{each.natures[index].level}}  </div></div>
                        <div>人生目标-{{each.target.name}}：{{each.target.description}}</div>
                        <div>技能：</div>
                        <div   v-for="(e,index4) in each.skills" v-bind:key="index4">
                          {{each.skills[index4].name}}：{{each.skills[index4].description}}
                        </div>
                      </div>
                      <div class="roles" @click="chooseRole(index7)">
                          {{each.name}}
                      </div>
                    </el-tooltip>
              </div>
            </div>

              <el-tooltip placement="top"  v-if="players.length===2" >
                  <div slot="content"><strong>{{players[(parseInt(myIndex)+1)%players.length].role.name}}    性别：</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].role.sex === 0">女</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].role.sex === 1">男</strong>
                    <div style="display:flex">天性：<div v-for="(each,index) in players[(parseInt(myIndex)+1)%players.length].role.natures" v-bind:key="index" style="margin-right:5px">{{players[(parseInt(myIndex)+1)%players.length].role.natures[index].name}}：{{players[(parseInt(myIndex)+1)%players.length].role.natures[index].level}}  </div></div>
                    <div>人生目标-{{players[(parseInt(myIndex)+1)%players.length].role.target.name}}：{{players[(parseInt(myIndex)+1)%players.length].role.target.description}}</div>
                    <div  v-for="(each,index2) in players[(parseInt(myIndex)+1)%players.length].bigProjects" v-bind:key="index2" style="display:flex">
                      {{players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].name}}：
                      <div  v-for="(each,index3) in players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects" v-bind:key="index3" style="display:flex;margin-right:5px">
                        {{players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].name}}
                        (<strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery < 10">F</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=10&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<17">E</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=17&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<25">D</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=25&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<35">C</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=35&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<45">B</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=45">A</strong>)
                      </div>
                    </div>
                    <div>技能：</div>
                    <div   v-for="(each,index4) in players[(parseInt(myIndex)+1)%players.length].role.skills" v-bind:key="index4">
                      {{players[(parseInt(myIndex)+1)%players.length].role.skills[index4].name}}：{{players[(parseInt(myIndex)+1)%players.length].role.skills[index4].description}}
                    </div>
                    <div style="display:flex">
                      已制作的名品：<div   v-for="(each,index5) in players[(parseInt(myIndex)+1)%players.length].treasures" v-bind:key="index5" style="margin-right:5px">{{players[(parseInt(myIndex)+1)%players.length].treasures[index5].name}}</div>
                    </div>
                  </div>
                <div class="playerMessage-2players" @click="chooseThisPlayer((parseInt(myIndex)+1)%players.length)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)+1)%players.length].id}">
                  <div class="playerMessage-header">
                      <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                      <div class="playerMessage-header-mid">¥×{{players[(parseInt(myIndex)+1)%players.length].finance}}</div>
                      <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                  </div>
                  <div class="playerMessage-role" :class="{redBackground:players[(parseInt(myIndex)+1)%players.length].redTeam,blueBackground:!this.players[(parseInt(myIndex)+1)%players.length].redTeam}">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                  <div class="playerMessage-buff">
                    <el-tooltip class="item" effect="dark" :content="players[(parseInt(myIndex)+1)%players.length].buffs[index].description" placement="top-start"  v-for="(each,index) in players[(parseInt(myIndex)+1)%players.length].buffs" v-bind:key="index">
                      <div class="playerMessage-buff-buff">{{players[(parseInt(myIndex)+1)%players.length].buffs[index].name}}({{players[(parseInt(myIndex)+1)%players.length].buffs[index].lastTurns}})</div>
                    </el-tooltip>
                  </div>
                </div>
              </el-tooltip>

            <div  class="playerMessage-morePlayers-top" v-if="players.length>2">
              <el-tooltip placement="top" v-for="(each,index0) in players" v-bind:key="index0" >
                  <div slot="content"><strong>{{players[index0].role.name}}    性别：</strong><strong v-if="players[index0].role.sex === 0">女</strong><strong v-if="players[index0].role.sex === 1">男</strong>
                    <div style="display:flex">天性：<div v-for="(each,index) in players[index0].role.natures" v-bind:key="index" style="margin-right:5px">{{players[index0].role.natures[index].name}}：{{players[index0].role.natures[index].level}}  </div></div>
                    <div>人生目标-{{players[index0].role.target.name}}：{{players[index0].role.target.description}}</div>
                    <div  v-for="(each,index2) in players[index0].bigProjects" v-bind:key="index2" style="display:flex">
                      {{players[index0].bigProjects[index2].name}}：
                      <div  v-for="(each,index3) in players[index0].bigProjects[index2].smallProjects" v-bind:key="index3" style="display:flex;margin-right:5px">
                        {{players[index0].bigProjects[index2].smallProjects[index3].name}}
                        (<strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery < 10">F</strong><strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery>=10&&players[index0].bigProjects[index2].smallProjects[index3].mastery<17">E</strong><strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery>=17&&players[index0].bigProjects[index2].smallProjects[index3].mastery<25">D</strong><strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery>=25&&players[index0].bigProjects[index2].smallProjects[index3].mastery<35">C</strong><strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery>=35&&players[index0].bigProjects[index2].smallProjects[index3].mastery<45">B</strong><strong v-if="players[index0].bigProjects[index2].smallProjects[index3].mastery>=45">A</strong>)
                      </div>
                    </div>
                    <div>技能：</div>
                    <div   v-for="(each,index4) in players[index0].role.skills" v-bind:key="index4">
                      {{players[index0].role.skills[index4].name}}：{{players[index0].role.skills[index4].description}}
                    </div>
                    <div style="display:flex">
                      已制作的名品：<div   v-for="(each,index5) in players[index0].treasures" v-bind:key="index5" style="margin-right:5px">{{players[index0].treasures[index5].name}}</div>
                    </div>
                  </div>
                <div class="playerMessage-top"  @click="chooseThisPlayer(index0)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[index0].id}" v-if="index0!==parseInt(myIndex)&&index0!==(parseInt(myIndex)+1)%players.length&&index0!==(parseInt(myIndex)-1+players.length)%players.length">
                    <div class="playerMessage-header">
                      <div class="playerMessage-header-left">{{players[index0].name}}</div>
                      <div class="playerMessage-header-mid">¥×{{players[index0].finance}}</div>
                      <div class="playerMessage-header-right">❤×{{players[index0].mood}}</div>
                    </div>
                    <div class="playerMessage-role" :class="{redBackground:players[index0].redTeam,blueBackground:!this.players[index0].redTeam}">{{players[index0].role.name}}</div>
                    <div class="playerMessage-buff">
                      <el-tooltip class="item" effect="dark" :content="players[index0].buffs[index].description" placement="top-start"  v-for="(each,index) in players[index0].buffs" v-bind:key="index">
                        <div class="playerMessage-buff-buff">{{players[index0].buffs[index].name}}({{players[index0].buffs[index].lastTurns}})</div>
                      </el-tooltip>
                    </div>
                </div>
              </el-tooltip>
            </div>

            <div class="playerMessage-morePlayers-mid" v-if="players.length>2" >
              <el-tooltip placement="top">
                  <div slot="content"><strong>{{players[(parseInt(myIndex)+1)%players.length].role.name}}    性别：</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].role.sex === 0">女</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].role.sex === 1">男</strong>
                    <div style="display:flex">天性：<div v-for="(each,index) in players[(parseInt(myIndex)+1)%players.length].role.natures" v-bind:key="index" style="margin-right:5px">{{players[(parseInt(myIndex)+1)%players.length].role.natures[index].name}}：{{players[(parseInt(myIndex)+1)%players.length].role.natures[index].level}}  </div></div>
                    <div>人生目标-{{players[(parseInt(myIndex)+1)%players.length].role.target.name}}：{{players[(parseInt(myIndex)+1)%players.length].role.target.description}}</div>
                    <div  v-for="(each,index2) in players[(parseInt(myIndex)+1)%players.length].bigProjects" v-bind:key="index2" style="display:flex">
                      {{players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].name}}：
                      <div  v-for="(each,index3) in players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects" v-bind:key="index3" style="display:flex;margin-right:5px">
                        {{players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].name}}
                        (<strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery < 10">F</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=10&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<17">E</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=17&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<25">D</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=25&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<35">C</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=35&&players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery<45">B</strong><strong v-if="players[(parseInt(myIndex)+1)%players.length].bigProjects[index2].smallProjects[index3].mastery>=45">A</strong>)
                      </div>
                    </div>
                    <div>技能：</div>
                    <div   v-for="(each,index4) in players[(parseInt(myIndex)+1)%players.length].role.skills" v-bind:key="index4">
                      {{players[(parseInt(myIndex)+1)%players.length].role.skills[index4].name}}：{{players[(parseInt(myIndex)+1)%players.length].role.skills[index4].description}}
                    </div>
                    <div style="display:flex">
                      已制作的名品：<div   v-for="(each,index5) in players[(parseInt(myIndex)+1)%players.length].treasures" v-bind:key="index5" style="margin-right:5px">{{players[(parseInt(myIndex)+1)%players.length].treasures[index5].name}}</div>
                    </div>
                  </div>
                <div class="playerMessage-morePlayers-mid-left"  @click="chooseThisPlayer((parseInt(myIndex)+1)%players.length)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)+1)%players.length].id}">
                    <div class="playerMessage-header">
                      <div class="playerMessage-header-left">{{players[(parseInt(myIndex)+1)%players.length].name}}</div>
                      <div class="playerMessage-header-mid">¥×{{players[(parseInt(myIndex)+1)%players.length].finance}}</div>
                      <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)+1)%players.length].mood}}</div>
                    </div>
                    <div class="playerMessage-role" :class="{redBackground:players[(parseInt(myIndex)+1)%players.length].redTeam,blueBackground:!this.players[(parseInt(myIndex)+1)%players.length].redTeam}">{{players[(parseInt(myIndex)+1)%players.length].role.name}}</div>
                    <div class="playerMessage-buff">
                      <el-tooltip class="item" effect="dark" :content="players[(parseInt(myIndex)+1)%players.length].buffs[index].description" placement="top-start"  v-for="(each,index) in players[(parseInt(myIndex)+1)%players.length].buffs" v-bind:key="index">
                        <div class="playerMessage-buff-buff">{{players[(parseInt(myIndex)+1)%players.length].buffs[index].name}}({{players[(parseInt(myIndex)+1)%players.length].buffs[index].lastTurns}})</div>
                      </el-tooltip>
                    </div>
                </div>
              </el-tooltip>

              <el-tooltip placement="top">
                  <div slot="content"><strong>{{players[(parseInt(myIndex)-1+players.length)%players.length].role.name}}    性别：</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].role.sex === 0">女</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].role.sex === 1">男</strong>
                    <div style="display:flex">天性：<div v-for="(each,index) in players[(parseInt(myIndex)-1+players.length)%players.length].role.natures" v-bind:key="index" style="margin-right:5px">{{players[(parseInt(myIndex)-1+players.length)%players.length].role.natures[index].name}}：{{players[(parseInt(myIndex)-1+players.length)%players.length].role.natures[index].level}}  </div></div>
                    <div>人生目标-{{players[(parseInt(myIndex)-1+players.length)%players.length].role.target.name}}：{{players[(parseInt(myIndex)-1+players.length)%players.length].role.target.description}}</div>
                    <div  v-for="(each,index2) in players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects" v-bind:key="index2" style="display:flex">
                      {{players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].name}}：
                      <div  v-for="(each,index3) in players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects" v-bind:key="index3" style="display:flex;margin-right:5px">
                        {{players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].name}}
                        (<strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery < 10">F</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery>=10&&players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery<17">E</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery>=17&&players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery<25">D</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery>=25&&players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery<35">C</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery>=35&&players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery<45">B</strong><strong v-if="players[(parseInt(myIndex)-1+players.length)%players.length].bigProjects[index2].smallProjects[index3].mastery>=45">A</strong>)
                      </div>
                    </div>
                    <div>技能：</div>
                    <div   v-for="(each,index4) in players[(parseInt(myIndex)-1+players.length)%players.length].role.skills" v-bind:key="index4">
                      {{players[(parseInt(myIndex)-1+players.length)%players.length].role.skills[index4].name}}：{{players[(parseInt(myIndex)-1+players.length)%players.length].role.skills[index4].description}}
                    </div>
                    <div style="display:flex">
                      已制作的名品：<div   v-for="(each,index5) in players[(parseInt(myIndex)-1+players.length)%players.length].treasures" v-bind:key="index5" style="margin-right:5px">{{players[(parseInt(myIndex)-1+players.length)%players.length].treasures[index5].name}}</div>
                    </div>
                  </div>
                <div class="playerMessage-morePlayers-mid-right"  @click="chooseThisPlayer((parseInt(myIndex)-1+players.length)%players.length)" v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[(parseInt(myIndex)-1+players.length)%players.length].id}">
                    <div class="playerMessage-header">
                      <div class="playerMessage-header-left">{{players[(parseInt(myIndex)-1+players.length)%players.length].name}}</div>
                      <div class="playerMessage-header-mid">¥×{{players[(parseInt(myIndex)-1+players.length)%players.length].finance}}</div>
                      <div class="playerMessage-header-right">❤×{{players[(parseInt(myIndex)-1+players.length)%players.length].mood}}</div>
                    </div>
                    <div class="playerMessage-role" :class="{redBackground:players[(parseInt(myIndex)-1+players.length)%players.length].redTeam,blueBackground:!this.players[(parseInt(myIndex)-1+players.length)%players.length].redTeam}">{{players[(parseInt(myIndex)-1+players.length)%players.length].role.name}}</div>
                    <div class="playerMessage-buff">
                      <el-tooltip class="item" effect="dark" :content="players[(parseInt(myIndex)-1+players.length)%players.length].buffs[index].description" placement="top-start"  v-for="(each,index) in players[(parseInt(myIndex)-1+players.length)%players.length].buffs" v-bind:key="index">
                        <div class="playerMessage-buff-buff">{{players[(parseInt(myIndex)-1+players.length)%players.length].buffs[index].name}}({{players[(parseInt(myIndex)-1+players.length)%players.length].buffs[index].lastTurns}})</div>
                      </el-tooltip>
                    </div>
                </div>
              </el-tooltip>
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

            <div class="selectSkillTips" v-show="selectedSkill">{{tips}}</div>

            <div class="buttonArea">
                <el-button type="primary" round class="useCardButton-true" v-if="selectedCard" @click="useCard()">使用</el-button>
                <el-button type="danger" round class="useCardButton-false" v-if="selectedCard" @click="clearUseButton()">取消</el-button>
                <el-button type="primary" round class="useCardButton-true" v-if="selectedSkill" @click="useSkill()">使用</el-button>
                <el-button type="danger" round class="useCardButton-false" v-if="selectedSkill" @click="clearSkillButton()">取消</el-button>
            </div>
            <el-button type="primary" round class="turnoverButton" v-if="isturnStart" @click="turnOver()">回合结束</el-button>
        </div>
        <div class="aside">
          <div class="discuss-main" v-if="AllChosen">
            <div class="discuss-receive">
              <div class="discuss-item"
                  v-for="item in received"
                  v-bind:key="item.id">
                <div class="discuss-item-name"
                    :style="{color:calColor(item.id)}">
                  <strong>{{item.name}}</strong>
                  <strong class="discuss-item-name"
                          :style="{color:calColor(item.id)}"
                          v-if="item.id === myId">
                    (你)
                  </strong>
                </div>
                <div class="discuss-item-context">{{item.context}}</div>
              </div>
            </div>
            <div class="discuss-input">
              <div style="height: 10px;"></div>
              <el-input
                type="textarea"
                rows="5"
                resize="none"
                placeholder="请输入消息"
                v-model="message">
              </el-input>
            </div>
            <div class="discuss-button">
              <el-button type="primary" @click="sendMessage()">发送</el-button>
            </div>
          </div>
        </div>
    </el-main>
    <el-footer height='200px' class="footer">
        <div class="buffArea" v-if="AllChosen">
          <el-tooltip class="item" effect="dark" :content="players[myIndex].buffs[index].description" placement="top-start"  v-for="(each,index) in players[myIndex].buffs" v-bind:key="index">
            <div class="buff">{{players[myIndex].buffs[index].name}}({{players[myIndex].buffs[index].lastTurns}})</div>
          </el-tooltip>
        </div>
        <div class="cardArea" v-if="AllChosen">
          <el-tooltip class="item" effect="dark" :content="players[myIndex].cards[index].description" placement="top-start"  v-for="(each,index) in players[myIndex].cards" v-bind:key="index">
            <div class="card" tabindex="0" @click="choosingCard(index)" :class="{multipleChoose: canFind(selectedCards,index)}">
              {{each.name}}
            </div>
          </el-tooltip>
        </div>
        <el-tooltip placement="top" v-if="AllChosen"  v-bind:class="{haveBeenSelectedRole: chosenPlayerId === players[myIndex].id}">
          <div slot="content"><strong>{{players[myIndex].role.name}}    性别：</strong><strong v-if="players[myIndex].role.sex === 0">女</strong><strong v-if="players[myIndex].role.sex === 1">男</strong>
                <div style="display:flex">天性：<div v-for="(each,index) in players[myIndex].role.natures" v-bind:key="index" style="margin-right:5px">{{players[myIndex].role.natures[index].name}}：{{players[myIndex].role.natures[index].level}}  </div></div>
                <div>人生目标-{{players[myIndex].role.target.name}}：{{players[myIndex].role.target.description}}</div>
                <div  v-for="(each,index2) in players[myIndex].bigProjects" v-bind:key="index2" style="display:flex">
                  {{players[myIndex].bigProjects[index2].name}}：
                  <div  v-for="(each,index3) in players[myIndex].bigProjects[index2].smallProjects" v-bind:key="index3" style="display:flex;margin-right:5px">
                    {{players[myIndex].bigProjects[index2].smallProjects[index3].name}}
                    (<strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].eagerness === 0">×</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].eagerness === 1">△</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].eagerness === 2">○</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].eagerness === 3">◎</strong>)
                    (<strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery < 10">F</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery>=10&&players[myIndex].bigProjects[index2].smallProjects[index3].mastery<17">E</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery>=17&&players[myIndex].bigProjects[index2].smallProjects[index3].mastery<25">D</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery>=25&&players[myIndex].bigProjects[index2].smallProjects[index3].mastery<35">C</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery>=35&&players[myIndex].bigProjects[index2].smallProjects[index3].mastery<45">B</strong><strong v-if="players[myIndex].bigProjects[index2].smallProjects[index3].mastery>=45">A</strong>)
                    {{players[myIndex].bigProjects[index2].smallProjects[index3].mastery}}/50
                  </div>
                </div>
                <div>技能：</div>
                <div   v-for="(each,index4) in players[myIndex].role.skills" v-bind:key="index4">
                  {{players[myIndex].role.skills[index4].name}}：{{players[myIndex].role.skills[index4].description}}
                </div>
                <div style="display:flex">
                  已制作的名品：<div   v-for="(each,index5) in players[myIndex].treasures" v-bind:key="index5" style="margin-right:5px">{{players[myIndex].treasures[index5].name}}</div>
                </div>
          </div>
          <div class="roleArea" @click="chooseThisPlayer(myIndex)">
            <div class="roleArea-header">
              <div class="roleArea-header-left">{{players[myIndex].name}}</div>
              <div class="roleArea-header-mid">¥×{{this.players[this.myIndex].finance}}</div>
              <div class="roleArea-header-right">❤×{{this.players[this.myIndex].mood}}</div>
            </div>
            <div class="studyTimes">本回合剩余学习次数:{{this.players[this.myIndex].canStudyTimes}}</div>
            <div class="roleArea-mid" :class="{redBackground:this.players[this.myIndex].redTeam,blueBackground:!this.players[this.myIndex].redTeam}">{{this.players[this.myIndex].role.name}}</div>
            <div class="roleArea-footer">
              <div class="roleArea-skill" v-for="(each,index) in players[myIndex].role.skills" v-bind:key="index">
                <el-button class="skill" type="success" round v-if="!players[myIndex].role.skills[index].automatic" @click="choosingSkill(index)">{{players[myIndex].role.skills[index].name}}</el-button>
              </div>
            </div>
          </div>
        </el-tooltip>
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
            margin: 50px auto"
      :row-class-name="tableRowClassName">
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
          prop="financeScore"
          label="财力值分数">
        </el-table-column>
        <el-table-column
          prop="totalScore"
          label="总分">
        </el-table-column>
      </el-table>
      <div class="RedScore">红队得分：{{this.redScore}}</div>
      <div class="BlueScore">蓝队得分：{{this.blueScore}}</div>
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

      // 消息栏
      message: '',
      received: [
        {
          name: '游戏公告',
          id: '-1',
          context: '提示：游戏目前仍属于开发阶段，如发现有bug或者有好的建议可以发送邮件至374100514@qq.com。'
        }
      ],
      myName: '',

      // 以下数值是出牌时的参数
      chosenCard: -1,
      selectedProject: [],
      levels: ['F', 'E', 'D', 'C', 'B', 'A'],
      selectedCard: false,
      selectedStudyCard: false,
      chosenPlayerId: 0,
      chosenLevel: -1,
      chosenProject: '',

      // 使用技能时的参数
      choenSkill: '',
      selectedSkill: false,
      selectedCards: [],
      tips: '',

      redScore: '',
      blueScore: '',
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
            case 'text':
              _self.received.push(data)
              _self.scrollToBottom()
              break
            case 'gameoverMessage':
              _self.redScore = data.data1
              _self.blueScore = data.data2
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
      this.myName = this.roles[index].name
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
    canFind: function (selectedCards, index) {
      for (const each in selectedCards) {
        if (selectedCards[each] === index) { return true }
      }
      return false
    },
    indexOf: function (selectedCards, index) {
      for (const each in selectedCards) {
        if (selectedCards[each] === index) { return each }
      }
      return -1
    },
    choosingCard: function (index) {
      if (this.isturnStart) {
        if (this.selectedSkill) {
          if (this.canFind(this.selectedCards, index)) {
            this.selectedCards.splice(this.indexOf(this.selectedCards, index), 1)
          } else {
            this.selectedCards.push(index)
          }
          console.log(this.selectedCards)
        } else {
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
            case '投资':
              if (this.players[this.myIndex].finance < 2) { canUse = false }
              break
            case '借贷':
              if (this.players[this.myIndex].finance < 2) { canUse = false }
              break
            case '5毛零食':
              if (this.players[this.myIndex].finance < 1) { canUse = false }
              break
            case '制作独立音乐':
              if (this.players[this.myIndex].bigProjects[1].smallProjects[0].mastery < 25) { canUse = false }
              break
            case '制作玩具模型':
              if (this.players[this.myIndex].bigProjects[3].smallProjects[0].mastery < 17 || this.players[this.myIndex].bigProjects[5].smallProjects[1].mastery < 17) { canUse = false }
              break
            default:
              break
          }
          if (canUse) {
            this.selectedCard = true
            if (this.players[this.myIndex].cards[index].type === 0) {
              if (this.players[this.myIndex].canStudyTimes > 0) {
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
              } else {
                this.$message.error('你本回合的学习次数已用尽')
              }
            }
          } else {
            this.$message.error('你未达到这张牌的使用条件')
          }
        }
      } else {
        this.$message.error('现在还不是你的回合')
      }
    },
    clearUseButton: function () {
      this.selectedCard = false
      this.selectedStudyCard = false
      this.chosenPlayerId = 0
    },
    choosingSkill: function (index) {
      if (this.isturnStart) {
        if (!this.haveThisBuff('失心')) {
          this.clearUseButton()
          switch (this.players[this.myIndex].role.skills[index].name) {
            case '熟能生巧':
              if (this.players[this.myIndex].mood > 1.0) {
                this.tips = '请选择一名角色'
                this.selectedSkill = true
                this.chosenSkill = '熟能生巧'
              } else {
                this.$message.error('你当前的心情值不足以发动此技能')
              }
              break
            case '失意':
              this.tips = '请选择一名角色及要丢弃的两张牌'
              this.selectedSkill = true
              this.chosenSkill = '失意'
              break
            case '重整旗鼓':
              this.tips = '请选择一名角色及要丢弃的三张牌'
              this.selectedSkill = true
              this.chosenSkill = '重整旗鼓'
              break
            case '珍视':
              if (this.players[this.myIndex].role.collectionUse) {
                this.tips = '请选择要收藏的一张牌'
                this.selectedSkill = true
                this.chosenSkill = '珍视'
              } else {
                this.$message.error('你本回合已无法再使用此技能')
              }
              break
            case '回忆':
              if (this.players[this.myIndex].mood > 3.0) {
                this.tips = '确定要使用“回忆”吗'
                this.selectedSkill = true
                this.chosenSkill = '回忆'
              } else {
                this.$message.error('你当前的心情值不足以发动此技能')
              }
              break
            case '失心':
              this.tips = '请选择一名角色及要丢弃的一张牌'
              this.selectedSkill = true
              this.chosenSkill = '失心'
              break
            case '紧急救援':
              if (this.players[this.myIndex].finance > 0) {
                this.tips = '请选择一名角色'
                this.selectedSkill = true
                this.chosenSkill = '紧急救援'
              } else {
                this.$message.error('你当前的财力值不足以发动此技能')
              }
              break
            case '炉边聚会':
              if (this.players[this.myIndex].finance > 2) {
                this.tips = '确定要使用“炉边聚会”吗'
                this.selectedSkill = true
                this.chosenSkill = '炉边聚会'
              } else {
                this.$message.error('你当前的财力值不足以发动此技能')
              }
              break
            default:
              break
          }
        } else {
          this.$message.error('你已被“失心”封印，无法使用技能')
        }
      } else {
        this.$message.error('现在还不是你的回合')
      }
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
        if (this.players[this.myIndex].cards[this.chosenCard].name === '共渡难关' || this.players[this.myIndex].cards[this.chosenCard].name === '赠人玫瑰' || this.players[this.myIndex].cards[this.chosenCard].name === '借贷') {
          if (this.chosenPlayerId === 0) {
            canUse = false
            this.$message.error('未选择正确的使用玩家')
          }
          if (this.chosenPlayerId + '' === this.myId + '') {
            canUse = false
            this.$message.error('不能指定自己为目标')
          }
        } else if (this.players[this.myIndex].cards[this.chosenCard].name === '无独有偶' || this.players[this.myIndex].cards[this.chosenCard].name === '底力爆发' || this.players[this.myIndex].cards[this.chosenCard].name === '潜心修学' || this.players[this.myIndex].cards[this.chosenCard].name === '投资' || this.players[this.myIndex].cards[this.chosenCard].name === '5毛零食') {

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
          case 2:
            this.socket.send(JSON.stringify({
              type: '名品牌',
              id: this.myId,
              name: (this.players[this.myIndex].cards[this.chosenCard].name)
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
    useSkill: function () {
      let canUse = true
      switch (this.chosenSkill) {
        case '熟能生巧':
          if (this.chosenPlayerId < 1) {
            this.$message.error('未选择正确的使用玩家')
            canUse = false
          }
          break
        case '失意':
          if (this.chosenPlayerId < 1) {
            this.$message.error('未选择正确的使用玩家')
            canUse = false
          } else if (this.selectedCards.length !== 2) {
            this.$message.error('请选择两张牌')
            canUse = false
          }
          break
        case '重整旗鼓':
          if (this.chosenPlayerId < 1) {
            this.$message.error('未选择正确的使用玩家')
            canUse = false
          } else if (this.selectedCards.length !== 3) {
            this.$message.error('请选择三张牌')
            canUse = false
          }
          break
        case '珍视':
          if (this.selectedCards.length !== 1) {
            this.$message.error('请选择一张牌')
            canUse = false
          }
          break
        case '失心':
          if (this.chosenPlayerId < 1) {
            this.$message.error('未选择正确的使用玩家')
            canUse = false
          } else if (this.selectedCards.length !== 1) {
            this.$message.error('请选择一张牌')
            canUse = false
          }
          break
        case '紧急救援':
          if (this.chosenPlayerId < 1) {
            this.$message.error('未选择正确的使用玩家')
            canUse = false
          }
          break
        default:
          break
      }
      if (canUse) {
        this.socket.send(JSON.stringify({
          type: 'Skill',
          id: this.myId,
          id2: this.chosenPlayerId,
          name: this.chosenSkill,
          cards: this.selectedCards
        }))
        this.clearSkillButton()
      }
    },
    clearSkillButton: function () {
      this.choenSkill = ''
      this.selectedSkill = false
      this.selectedCards = []
      this.tips = ''
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
    },
    calColor: function (id) {
      if (id === this.myId) {
        return '#fd4659'
      } else { return '#01386a' }
    },
    scrollToBottom: function () {
      this.$nextTick(() => {
        const container = this.$el.querySelector('.discuss-receive')
        container.scrollTop = container.scrollHeight
      })
    },
    sendMessage: function () {
      const pattern = /^\s*$/
      if (pattern.test(this.message)) {
        this.$message.error('发送消息不能为空！')
      } else {
        const msg = {
          type: 'text',
          name: this.myName,
          id: this.myId,
          context: this.message
        }
        this.socket.send(JSON.stringify(msg))
      }
      this.message = ''
    },
    cantTurnoverBug: function () {
      this.socket.send(JSON.stringify({
        type: 'turnStartStage',
        id: this.players[(parseInt(this.myIndex) + 1) % this.players.length].id
      }))
    },
    tableRowClassName ({ row, rowIndex }) {
      if (this.finalData[rowIndex].redTeam) {
        return 'success-row'
      } else {
        return 'warning-row'
      }
    },
    haveThisBuff (name) {
      for (const each in this.players[this.myIndex].buffs) {
        if (this.players[this.myIndex].buffs[each].name === name) {
          return true
        }
      }
      return false
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
    font-size: 10px;
}
.playerMessage-header-mid {
    margin: 10px auto;
    color: #E6A23C;
}
.playerMessage-header-right {
    margin: 10px 10px auto auto;
    color: #FF2D2D;
}
.playerMessage-role {
    line-height: 90px;
    text-align: center;
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
}
.playerMessage-buff {
    width: 90%;
    height: 30%;
    display: flex;
    margin: auto;
}
.playerMessage-buff-buff {
    border:1px solid black;
    border-radius: 1px;
    width: 28%;
    height: 45%;
    margin: 10px auto auto 10px;
    text-align: center;
    font-size: 10px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
    border-radius: 5px;
}
.playerMessage-morePlayers-top {
    width: 1000px;
    height: 240px;
    display: flex;
}
.playerMessage-top {
    margin: 20px auto;
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
    position: absolute;
    left: 350px;
    top: 350px;
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
    position: absolute;
    left: 350px;
    top: 400px;
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
.selectSkillTips {
  position: absolute;
  width: 80%;
  text-align: center;
    top: 400px;
  color: #E6A23C;
  font-size: 26px;
  font-family: "Microsoft Yahei", sans-serif;
  letter-spacing: 0.032cm;
}
.haveBeenSelected {
  color: red;
}
.haveBeenSelectedRole {
  border:2px solid red;
}
.buttonArea {
  width: 600px;
  position: absolute;
    left: 350px;
    top: 450px;
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
.discuss-main {
  height: 465px;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: whitesmoke;
  border-radius: 10px;
}

.discuss-receive {
  height: 60%;
  width: 100%;
  border-radius: 10px;
  overflow: scroll;
  overflow-x: hidden;
  word-break: break-all;
}

.discuss-item {
  height: 50px;
  width: 95%;
  margin: 16px 0 0 10px;
  display: inline-block;
}

.discuss-item-name {
  font-size: 18px;
  font-family: "Arial", sans-serif;
  margin-bottom: 2px;
}

.discuss-item-context {
  font-size: 13px;
  font-family: "Microsoft Yahei", sans-serif;
  letter-spacing: 0.032cm;
}

.discuss-input {
  height: 130px;
  width: 96%;
  margin-left: auto;
  margin-right: auto;
}

.discuss-button {
  float: right;
  margin: 10px 5px 5px 5px;
}
.footer {
    display: flex;
    padding: 0;
}
.buffArea {
    border: solid 1px yellow;
    flex: 1;
    display: flex;
}
.buff {
  border:1px solid black;
  border-radius: 1px;
  width: 28%;
  height: 18%;
  margin: 10px auto auto 10px;
  text-align: center;
  font-size: 18px;
  font-family: "Microsoft Yahei", sans-serif;
  letter-spacing: 0.032cm;
  border-radius: 5px;
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
  font-size: 18px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
    background-color: white;

}
.card:hover {
  margin: -20px 5px 40px;
}
.card:focus {
  margin: -20px 5px 40px;
}
.multipleChoose {
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
.roleArea-header-mid {
    margin: 10px auto;
    color:#E6A23C;
}
.roleArea-header-right {
    margin: 10px 10px auto auto;
    color: #FF2D2D;
}
.studyTimes {
  font-size: 10px;
}
.roleArea-mid {
    line-height: 120px;
    text-align: center;
    font-size: 26px;
    font-family: "Microsoft Yahei", sans-serif;
    letter-spacing: 0.032cm;
}
.redBackground {
  background-color: rgba(238, 38, 38, 0.603);
}
.blueBackground {
  background-color: rgba(51, 81, 212, 0.795);
}
.roleArea-footer {
  height: 20px;
  width: 100%;
  display: flex;
  margin: -10px auto;
}
.skill {
  margin: auto 10px;
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
.el-table .warning-row {
    background: rgba(51, 81, 212, 0.795);
  }

  .el-table .success-row {
    background:  rgba(238, 38, 38, 0.603);
  }
.RedScore {
  text-align: center;
  background-color: rgba(238, 38, 38, 0.603);
  font-size: 26px;
}
.BlueScore {
  text-align: center;
  background-color: rgba(51, 81, 212, 0.795);
  font-size: 26px;
}
.restartButton {
  margin: 20px auto auto 45%;
}
</style>
