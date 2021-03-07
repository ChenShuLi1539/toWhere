<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: 'Home',
  beforeMount () {
    this.$store.state.eventBus.$on('homeCheckCookie', (val) => {
      this.$store.getters.checkCookie(this, val)
    })
  }
}
</script>
