<template>
  <div>
    <div style="background:rgba(153, 255, 204,0.5)">
      <el-container>
        <el-header>
          <IndexHead></IndexHead>
        </el-header>
        <el-main>
          <h1 v-if="isSearch == true">搜索: {{searchValue}}</h1>

        </el-main>
        <el-footer>
          <viewDetailEnd :pagesize="pagesize" :isSearch="isSearch" :searchvalue="searchValue"></viewDetailEnd>

        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script>
  import IndexHead from '@/components/indexEl/indexhead.vue'
  import viewDetailEnd from '@/components/videoEl/viewdetailend.vue'
  export default {

    data() {
      return {
        searchValue: '',
        isSearch: false,

      }
    },
    methods: {

      getRouterParams() {
        this.searchValue = this.$route.params.vvv;
        this.isSearch = this.$route.params.isSearch;
      }
    },
    created() {
      this.getRouterParams()
    },
    components: {
      IndexHead,
      viewDetailEnd
    }
  }
</script>

<style>
</style>
