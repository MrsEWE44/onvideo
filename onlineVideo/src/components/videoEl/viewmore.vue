<template>
  <div style="background:rgba(153, 255, 204,0.5)">
    <el-container>
      <el-header>
        <IndexHead></IndexHead>
      </el-header>
      <el-main>
        <viewMoreCenter :menuItem="menuItem" @newitem="getnewitem" @newchilder="getnewchilder"></viewMoreCenter>
      </el-main>
      <el-footer>
        <viewMoreEnd :menuItem="menuItem" :menuItemChilder="menuItemChilder"></viewMoreEnd>
      </el-footer>
    </el-container>
  </div>
</template>
<script>
  import IndexHead from '@/components/indexEl/indexhead.vue'
  import viewMoreCenter from '@/components/videoEl/viewmorecenter.vue'
  import viewMoreEnd from '@/components/videoEl/viewmoreend.vue'
  export default {
    data() {
      return {
        menuItem: '',
        menuItemChilder: ''
      }
    },
    methods: {
      getParams() {
        var routeQueryParam = this.$route.params;
        this.menuItem = routeQueryParam.ppp;
        // console.log("p1 :: " , routeQueryParam.ppp)
      },

      getnewitem(a) {
        this.menuItem = a

      },
      getnewchilder(a) {
        this.menuItemChilder = a
      }

    },
    components: {
      IndexHead,
      viewMoreCenter,
      viewMoreEnd
    },
    created() {
      this.getParams()
    }
  }
</script>

<style>

</style>
