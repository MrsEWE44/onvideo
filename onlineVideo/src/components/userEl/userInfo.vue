<template>
  <div style="background:rgba(153, 255, 204,0.5)">
    <div>
      <el-container>
        <el-header>
          <IndexHead></IndexHead>
        </el-header>
        <el-main>
          <el-container>
            <el-header>
              <table>
                <tr>
                  <td><img :src="uinfo.uicon" style="height: 50px; width: 50px;border-radius:100%;" /></td>
                  <td>
                    <h1>{{uinfo.uname}}</h1>
                  </td>
                </tr>
              </table>
            </el-header>
            <el-main>
              <el-row :gutter="20">
                <el-col :span="20">
                  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                    <el-tab-pane label="视频" name="视频">
                      <el-row :gutter="10">
                        <el-col v-for="(item,index) in videoList" :key="index" :span="6">
                          <showVideoInfo :vinfo="item" :showbt="true"></showVideoInfo>
                        </el-col>
                      </el-row>
                    </el-tab-pane>
                    <el-tab-pane label="动态" name="动态">
                      <div style="background-color: #42B983; margin-bottom: 10px;" v-for="(item,index) in vinfos"
                        :key="index">
                        <dynamicitem :uinfo="uinfo" :vinfo="item"></dynamicitem>
                      </div>
                    </el-tab-pane>
                    <el-tab-pane label="评论" name="评论">

                      <div style="background-color: #e626ff; margin-bottom: 10px;" v-for="(item,index) in commits"
                        :key="index">
                        <commentUser :uinfo="item"></commentUser>
                      </div>
                    </el-tab-pane>
                  </el-tabs>
                </el-col>
                <el-col :span="4">
                  <el-button @click="uploadVideos" type="primary">上传视频</el-button>
                </el-col>

              </el-row>
            </el-main>

          </el-container>


        </el-main>
      </el-container>
    </div>

    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[5,10, 20, 30, 40,50,100,200,300,400,500,1000]" :page-size="pagesize" :hide-on-single-page="ishide"
        layout="total, sizes, prev, pager, next, jumper" :total="pagetotal">
      </el-pagination>
    </div>
    <div>
      <uploadFile :fileDialog="fileDialog" @dialogUploadFileClose="getDialogUploadFileClose"></uploadFile>
    </div>
  </div>
</template>

<script>
  import IndexHead from '@/components/indexEl/indexhead.vue'
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  import dynamicitem from '@/components/userEl/dynamicItem.vue'
  import commentUser from '@/components/widget/commentuser.vue'
  import uploadFile from '@/components/widget/fileupload.vue'
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  export default {

    data() {
      return {
        activeName: "视频",
        uinfo: '',
        videoList: '',
        commits: '',
        vinfos: '',
        currentPage: 1,
        ishide: false,
        pagesize: 5,
        pagetotal: 0,
        //需要在初次使用时，修改为true，上传一个文件后，就可以改为false
        fileDialog: false,
        isme2: false

      }
    },
    methods: {
      getUserInfo() {
        let that = this;
        if(that.uinfo !== undefined){
          httpget("aa/video/user/getUserInfo2?uid=" + that.uinfo.uid).then(
            (res) => {
              if (res.code == 0) {
                that.uinfo = res.data;
                if (that.uinfo.uicon === "" || that.uinfo.uicon === undefined) {
                  that.uinfo.uicon = "aa/video/user/getUserIcon?uid=" + that.uinfo.uid
                }
              } else {
                that.$message.error(res.msg);
              }
            }
          )
        }

      },
      getDialogUploadFileClose() {
        this.fileDialog = false
      },
      uploadVideos() {
        this.fileDialog = true
      },
      handleSizeChange(val) {
        this.pagesize = val;
        this.handleClick()
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.handleClick()
      },
      handleClick() {
        // console.log("activeName ::: " ,this.activeName)
        this.isme2 = this.$route.params.isMe;
        switch (this.activeName) {
          case "视频":
            this.getUserHomePageVideos()
            break;
          case "动态":
            this.getUserHomePageDynamics()
            break;
          case "评论":
            this.getUserHomePageCommits()
            break;
        }
        // this.getUserInfo()
      },
      getUserHomePageVideos() {
        let that = this;
        let pp = {
          isme: that.isme2,
          uid: '',
          page: that.currentPage,
          size: that.pagesize
        }
        httppost("aa/video/user/getUserHomePageVideos", JSON.stringify(pp)).then((res) => {
          if (res.code == 0) {
            that.videoList = res.data
            that.uinfo = res.data[0]
            that.pagetotal = res.total
            if (that.pagetotal <= that.pagesize) {
              that.ishide = true
            }
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
      },
      getUserHomePageCommits() {
        let that = this;
        let pp = {
          isme: that.isme2,
          uid: '',
          page: that.currentPage,
          size: that.pagesize
        }
        httppost("aa/video/user/getUserHomePageCommits", JSON.stringify(pp)).then((res) => {
          if (res.code == 0) {
            that.commits = res.data
            that.uinfo = res.data[0]
            that.pagetotal = res.total
            if (that.pagetotal <= that.pagesize) {
              that.ishide = true
            }
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
      },
      getUserHomePageDynamics() {
        let that = this;
        let pp = {
          isme: that.isme2,
          uid: '',
          page: that.currentPage,
          size: that.pagesize
        }
        httppost("aa/video/user/getUserHomePageDynamics", JSON.stringify(pp)).then((res) => {
          if (res.code == 0) {
            that.vinfos = res.data
            that.uinfo = res.data[0]
            that.pagetotal = res.total
            if (that.pagetotal <= that.pagesize) {
              that.ishide = true
            }
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
      },


    },
    components: {
      IndexHead,
      showVideoInfo,
      dynamicitem,
      commentUser,
      uploadFile
    },
    created() {
      this.handleClick();
    },
    watch: {

    }
  }
</script>

<style>
</style>
