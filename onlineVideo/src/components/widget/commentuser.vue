<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="4">
        <img :src="uinfo.uicon" style="height: 50px; width: 50px;border-radius:100%;" />
      </el-col>
      <el-col :span="8">
        <table>
          <tr>
            <td>
              <p>{{uinfo.uname}}</p>
            </td>
          </tr>
          <tr>
            <td>
              <h1>{{uinfo.msg}}</h1>
            </td>
          </tr>
          <tr>
            <td>{{fdate(uinfo.date)}}</td>
          </tr>
        </table>

      </el-col>

      <el-col v-if="uinfo.vicon !== null" :span="10">
        <div @click="intoVideo">
          <img style="width: 170px; height: 170px;" :src="uinfo.vicon" />

        </div>
      </el-col>
      <el-col v-if="uinfo.vicon !== null" :span="2">
        <el-button type="danger" icon="el-icon-delete" @click="delcom" circle></el-button>
      </el-col>

    </el-row>


  </div>
</template>

<script>
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  import {
    formatDate
  } from '../../../static/coretools.js'
  export default {
    data() {
      return {

      }
    },
    props: ['uinfo'],
    methods: {
      intoVideo(){
        let that=this
        httpget("aa/video/video/getVideoInfo?vid="+that.uinfo.vid).then((res)=>{
          if (res.code == 0) {
            if (that.$route.path === "/videoplayer") {
              that.$router.replace({
                name: "refreshPage2",
                params: {
                  "vinfo": res.data
                }
              })
            } else {
              that.$router.push({
                name: "videoplayer",
                params: {
                  "vinfo": res.data
                }
              })
            }
          }
        }).catch((err)=>{
          that.$message.error(err.request.response);
        })
        console.log("intoVideo :::: ",this.uinfo)
      },
      delcom() {
        let that = this;
        httppost("aa/video/comment/delMSG", JSON.stringify(that.uinfo)).then((res) => {
          if (res.code == 0) {
            that.$message.success(res.msg);
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
        })
        // console.log("delcom ::::: ",this.uinfo)
      },
      fdate(d) {
        return formatDate(d)
      },
      fix_icon() {
        if (this.uinfo.uicon === "" || this.uinfo.uicon === undefined) {
          this.uinfo.uicon = "aa/video/user/getUserIcon?uid=" + this.uinfo.uid
        }
        if (this.uinfo.vicon === "" || this.uinfo.vicon === undefined) {
          this.uinfo.vicon = "aa/video/video/getVideoIcon?vid=" + this.uinfo.vid;
        }
      }
    },
    created() {
      this.fix_icon()
    },
    watch: {
      uinfo(v, o) {
        this.fix_icon()
      }
    }
  }
</script>

<style>
</style>
