<template>
  <div style="background:rgba(153, 255, 204,0.5)">
    <el-container>
      <el-header>
        <IndexHead></IndexHead>
      </el-header>
      <el-main>
        <IndexCenter :headMenu="headMenu" :leftImages="leftImages" :videoList="videoList" @refreshVideo="refreshVideo">
        </IndexCenter>
      </el-main>
      <el-footer>
        <IndexEnd :headMenu="headMenu"></IndexEnd>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
  import IndexHead from '@/components/indexEl/indexhead.vue'
  import IndexCenter from '@/components/indexEl/indexcenter.vue'
  import IndexEnd from '@/components/indexEl/indexend.vue'
  import {
    httpget,
    httppost
  } from '../../static/request.js'
  export default {
    data() {
      return {
        headMenu: [],
        videoList: [],
        leftImages: []
      }
    },
    methods: {

      getLeftImages() {
        let that = this
        httpget("aa/video/video/getLeftImages?limit=6").then((res) => {
          if (res.code == 0) {
            that.leftImages = res.data
            console.log("that.leftImages :::: ", that.leftImages)
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
        })
      },

      getVideoList() {

        let that = this
        httpget("aa/video/video/getIndexHeadVideo").then(
          (res) => {
            if (res.code == 0) {
              that.videoList = res.data
            } else {
              this.$message.error(res.msg);
            }
          }
        )
      },

      getHeadMenu() {
        let that = this
        httpget("aa/video/menu/getMenus").then(
          (res) => {
            if (res.code == 0) {
              that.headMenu = res.data
            } else {
              this.$message.error(res.msg);
            }
          }
        )
      },
      refreshVideo() {
        this.getVideoList()
      }


    },
    components: {
      IndexHead,
      IndexCenter,
      IndexEnd
    },
    created() {
      this.getHeadMenu()
      this.getLeftImages()
      this.getVideoList()
    }
  }
</script>

<style>
</style>
