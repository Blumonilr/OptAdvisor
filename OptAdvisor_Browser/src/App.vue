<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: "App",
  provide:function () {
    return{
      reload: this.reload
    }
  }
};
</script>

<style>

#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  /*height: 100%;*/
  /*min-height: 100%;*/
  padding: 0;
  margin: 0;
  /*position: absolute;*/
  top: 0;
  left: 0;
  width: 100%;
  border: hidden;
  overflow: auto;
}

</style>
