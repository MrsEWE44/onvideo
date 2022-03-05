<template>
  <div></div>
</template>

<script>
  export default {
    name: 'refreshPage2',
    data() {
      return {};
    },
    beforeRouteEnter(to, from, next) {
      next((vm) => {
        var targetpath = from.path.replace("/", "")
        // console.log("to ::: ",to.params,targetpath)
        vm.$router.push({
          name: targetpath,
          params: {
            "vinfo": to.params.vinfo
          }
        });
      });
    },
  };
</script>

<style>
</style>
