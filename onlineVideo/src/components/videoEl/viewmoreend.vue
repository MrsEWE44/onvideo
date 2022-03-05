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
                <tr v-for="(titem, index) in item.top10" :key="index" style="margin-top: 10px;">
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
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  import topVideoInfo from '@/components/widget/topvideoinfo.vue'
  export default {
    data() {
      return {
        mainMenuChilderNewVideos: [],
        newMenuItem: '',
      }
    },
    methods: {

      getChilder(fid, mid) {
        let that = this;
        return new Promise((resolve, reject) => {
          httpget("aa/video/video/getChilder2?fid=" + fid + "&mid=" + mid).then(
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


      async getNewVides() {
        let that = this
        that.mainMenuChilderNewVideos = []
        for (var nmi of that.newMenuItem.childer) {
          let ccc = await that.getChilder(that.newMenuItem.paid, nmi.paid)
          var vvvv = {
            title: nmi.title,
            paid: nmi.paid,
            childer: ccc,
            top10: ccc
          };
          that.mainMenuChilderNewVideos.push(vvvv)
        }

      },
      getRefreshVideo(fid, mid) {
        let that = this;
        return new Promise((resolve, reject) => {
          httpget("aa/video/video/getRefreshVideo2?fid=" + fid + "&mid=" + mid).then(
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
            that.mainMenuChilderNewVideos[i].childer = await that.getRefreshVideo(that.newMenuItem.paid, paid);
          }
        }
      },

      intoviewmore(chid) {
        // console.log("intoviewmore end ::: " ,this.newMenuItem.paid,chid)
        let vvv = {
          "fid": this.newMenuItem.paid,
          "chid": chid
        }

        this.$router.push({
          name: "viewdetail",
          params: {
            "vvv": vvv,
            "isSearch": false
          }
        })

        // this.$router.push({name : "viewMore",params:{"ppp": this.headMenu2[paid-1]}})

      },


    },
    props: ['menuItem', 'menuItemChilder'],
    components: {
      showVideoInfo,
      topVideoInfo
    },
    created() {
      this.getNewVides()
    },
    watch: {
      menuItem(v, o) {
        this.newMenuItem = v;
        this.getNewVides()
        //console.log("menuItem :::: ",v,o)
      },
      menuItemChilder(v, o) {
        this.newMenuItem = v;
        this.getNewVides()
      }

    }

  }
</script>

<style>
</style>
