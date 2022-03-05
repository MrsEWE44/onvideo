<template>
  <div></div>
</template>

<script>
  export default {
    name: 'refreshPage',
    data() {
      return {};
    },
    beforeRouteEnter(to, from, next) {
      /**
       * 用于搜索跳转的临时中间页面
       * 需要将head页面的搜索内容再次转发到同一个页面
       *
       *
       * */
      next((vm) => {
        var targetpath = from.path.replace("/", "")
        // console.log("to ::: ",to.params,targetpath)
        vm.$router.push({
          name: targetpath,
          params: {
            "vvv": to.params.vvv,
            "isSearch": to.params.isSearch
          }
        });
      });
    },
  };
</script>

<style>
</style>
