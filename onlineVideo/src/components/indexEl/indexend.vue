<template>
  <div style="background:rgba(153, 255, 204,0.5);">
    <div v-for="(item ,index) in mainMenuChilderNewVideos" :key="index">
      <el-row :gutter="10" v-if="item.childer.length > 0">
        <el-col :span="18">
          <el-container>
            <el-container>
              <el-aside width="200px"></el-aside>
              <el-container>
                <el-header>
                  <el-row style="height: 40%;">
                    <el-col :span="6">
                      <h1 style="font-size: x-large; margin-top: -10px;">
                        <p>{{item.title}}</p>
                      </h1>
                    </el-col>
                    <el-col :span="15">
                      <el-button icon="el-icon-refresh-right" @click="refreshVideo(item.paid)" circle></el-button>
                    </el-col>
                    <el-col :span="2">
                      <el-button @click="intoviewmore(item.paid)" plain>查看更多</el-button>
                    </el-col>
                  </el-row>
                </el-header>
                <el-main>
                  <el-row>
                    <el-col span="6" v-for="(citem, index) in item.childer" :key="index">
                      <showVideoInfo :vinfo="citem"></showVideoInfo>
                    </el-col>
                  </el-row>
                </el-main>
              </el-container>

            </el-container>

          </el-container>
        </el-col>
        <el-col :span="6">

          <el-container>
            <el-header>
              <el-row style="height: 40%;">
                <el-col :span="6">
                  <h1 style="font-size: x-large; margin-top: -10px;">
                    <p>排行榜</p>
                  </h1>
                </el-col>

                <el-col :span="2">
                  <el-button plain>查看更多</el-button>
                </el-col>
              </el-row>
            </el-header>
            <el-main>
              <table>
                <tr v-for="(titem, index) in item.top" :key="index" style="margin-top: 10px;">
                  <topVideoInfo :vinfo="titem"></topVideoInfo>
                </tr>
              </table>
            </el-main>
          </el-container>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  import topVideoInfo from '@/components/widget/topvideoinfo.vue'
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  export default {
    data() {
      return {
        //首页展示的视频都是从头部菜单里推荐过来的，如果有视频就显示，没有就不显示
        mainMenuChilderNewVideos: [],
        headMenu2: [],

      }
    },
    props: ['headMenu'],
    methods: {

      intoviewmore(paid) {
        // console.log("intoviewmore ::: " , paid)
        this.$router.push({
          name: "viewMore",
          params: {
            "ppp": this.headMenu2[paid - 1]
          }
        })

      },

      /**
       * 傻逼前端，傻逼vue，傻逼axios
       *
       * 这里如果要在循环中发送请求，你需要用Promise、async、await组合
       * 因为axios是异步的
       * 2022年2月26日14点11分
       *
       * */
      async getNewVideos() {
        let that = this
        that.mainMenuChilderNewVideos = []
        for (var hm of that.headMenu2) {
          let ccc = await that.getChilder(hm.paid)
          var vv = {
            paid: hm.paid,
            title: hm.title,
            childer: ccc,
            top: ccc
          }
          that.mainMenuChilderNewVideos.push(vv)
        }
      },

      getChilder(paid) {
        let that = this;
        return new Promise((resolve, reject) => {
          httpget("aa/video/video/getChilder?fid=" + paid).then(
            (res) => {
              if (res.code == 0) {
                resolve(res.data)
              } else {
                that.$message.error(res.msg);
              }
            }
          )
        })
      },

      getRefreshVideo(paid) {
        let that = this;
        return new Promise((resolve, reject) => {
          httpget("aa/video/video/getRefreshVideo?fid=" + paid).then(
            (res) => {
              if (res.code == 0) {
                resolve(res.data)
              } else {
                that.$message.error(res.msg);
              }
            }
          )
        })
      },
      async refreshVideo(paid) {
        let that = this
        for (var i = 0; i < that.mainMenuChilderNewVideos.length; i++) {
          if (that.mainMenuChilderNewVideos[i].paid === paid) {
            that.mainMenuChilderNewVideos[i].childer = await that.getRefreshVideo(paid);
          }
        }
      }
    },
    components: {
      showVideoInfo,
      topVideoInfo
    },
    created() {
      this.getNewVideos()
    },
    watch: {
      headMenu(v, o) {
        this.headMenu2 = v
        this.getNewVideos()
      }
    }
  }
</script>

<style>
</style>
