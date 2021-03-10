<template>
  <el-container class="gameOutline">
  <el-header height='30px' class="header"></el-header>
  <el-main class="mainArea">
      <div class="gametable"></div>
      <div class="aside"></div>
  </el-main>
  <el-footer height='120px' class="footer">
      <div class="buffArea"></div>
      <div class="cardArea"></div>
      <div class="roleArea"></div>
  </el-footer>
</el-container>
</template>

<script>
export default {
  name: 'outline'
}
</script>

<style scoped>
.header {
    border: black 1px solid;
}
.gameOutline {
    width: 1100px;
    height: 550px;
    border: red 1px solid;
    margin: 100px auto;
}
.gametable {
    border: solid 1px blue;
    flex: 4;
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
</style>
