<template>
  <div style="background:rgba(153, 255, 204,0.5)">
    <el-container>
      <el-header>
        <IndexHead></IndexHead>
      </el-header>
      <el-main>
        <el-row :gutter="10" style="margin-top: 20px;">
          <el-col :span="20">
            <el-container>
              <el-header>
                <table>
                  <tr>
                    <td>
                      <h1>
                        <p>{{videoPlayerInfo.videoInfoPlayer.title}}</p>
                      </h1>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      {{videoPlayerInfo.videoInfoPlayer.commits}}
                    </td>
                    <td>
                      {{videoPlayerInfo.videoInfoPlayer.views}}
                    </td>

                    <td>
                      {{fdate(videoPlayerInfo.videoInfoPlayer.date)}}
                    </td>
                  </tr>
                </table>
              </el-header>
              <el-main>
                <table>
                  <tr>
                    <td>
                      <video width="1280" height="680" controls style="margin-top: 20px;">
                        <source :src="videoPlayerInfo.player" type="video/mp4">
                        您的浏览器不支持 HTML5 video 标签。
                      </video>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <h1>{{videoPlayerInfo.videoInfoPlayer.msg}}</h1>
                    </td>
                  </tr>
                  <tr>
                    <el-row :gutter="10">
                      <el-col :span="4">
                        <el-button type="success" icon="el-icon-check" circle></el-button>
                      </el-col>
                      <el-col :span="16">
                        <el-input placeholder="请输入内容" type="textarea" rows=1 v-model="msg" clearable>
                        </el-input>
                      </el-col>
                      <el-col :span="4">
                        <el-button @click="sendmsg" type="primary">发送评论</el-button>
                      </el-col>
                    </el-row>
                  </tr>
                  <tr>
                    <commentUser v-for="(item,index) in videoPlayerInfo.commitInfos" :key="index" :uinfo="item">
                    </commentUser>
                  </tr>
                </table>

              </el-main>

            </el-container>

          </el-col>
          <el-col :span="4">
            <el-container>
              <el-header>
                <table>
                  <tr>
                    <td><img :src="videoPlayerInfo.uinfo.uicon" style="height: 50px; width: 50px;border-radius:100%;" />
                    </td>
                    <td>
                      <p>{{videoPlayerInfo.uinfo.uname}}</p>
                    </td>
                  </tr>
                  <tr>
                    <showVideoInfo v-for="(item,index) in topVidelList" :key="index" :vinfo="item"></showVideoInfo>
                  </tr>
                </table>
              </el-header>
            </el-container>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  import IndexHead from '@/components/indexEl/indexhead.vue'
  import commentUser from '@/components/widget/commentuser.vue'
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  import {
    formatDate
  } from '../../../static/coretools.js'
  export default {
    data() {
      return {
        msg: '',
        videoPlayerInfo: [],
        topVidelList: [],

      }
    },
    methods: {
      fdate(dd) {
        return formatDate(dd)
      },
      getRouteVidParm() {
        return this.$route.params;
      },
      sendmsg() {
        let that = this
        let pp = {
          "vid": that.getRouteVidParm().vinfo.id,
          "msg": that.msg
        }
        httppost("aa/video/comment/sendMsg", JSON.stringify(pp)).then((res) => {
          if (res.code == 0) {
            that.$message.success(res.msg);
          }
        }).catch((err) => {
          //that.$message.error(err.request.response);

        })

      },

      getVideoPlayerInfo() {
        let that = this
        let vinfo = that.getRouteVidParm().vinfo
        httppost("aa/video/video/getVideoPlayer", JSON.stringify(vinfo)).then((res) => {
          if (res.code == 0) {
            that.videoPlayerInfo = res.data
            if (that.videoPlayerInfo.player === "" || that.videoPlayerInfo.player === undefined) {
              that.videoPlayerInfo.player = "aa/video/video/getVideoFile?vid=" + vinfo.id
            }
            if (that.videoPlayerInfo.uinfo.uicon === "" || that.videoPlayerInfo.uinfo.uicon === undefined) {
              that.videoPlayerInfo.uinfo.uicon = "aa/video/user/getUserIcon?uid=" + that.videoPlayerInfo.uinfo.uid

            }
            // console.log("that.videoPlayerInfo ::: ",that.videoPlayerInfo)
          }
        }).catch((err) => {
          // console.log("getVideoPlayerInfo ::: err ::: ",err.request.response)
          that.$message.error(err.request.response);
          that.$router.push({
            name: 'IndexPage'
          })
        })
      },
      getTopVideoList() {
        let that = this
        let vinfo = that.getRouteVidParm().vinfo
        httpget("aa/video/video/getRefreshVideo2?fid=" + vinfo.fid + "&mid=" + vinfo.mid).then(
          (res) => {
            if (res.code == 0) {
              that.topVidelList = res.data
            }
          }
        ).catch((err) => {
          // console.log("getVideoPlayerInfo ::: err ::: ",err.request.response)
          that.$message.error(err.request.response);
          that.$router.push({
            name: 'IndexPage'
          })
        })
      }
    },
    components: {
      IndexHead,
      commentUser,
      showVideoInfo
    },
    created() {
      this.getVideoPlayerInfo()
      this.getTopVideoList()
    }
  }
</script>

<style>
</style>
