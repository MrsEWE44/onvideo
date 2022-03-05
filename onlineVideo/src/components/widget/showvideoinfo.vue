<template>
  <div>
    <div class="video_div_st" @click="vinfo_click(vinfo.id)">
      <img class="image_st" :src="vinfo.icon" />
      <h3 style="margin-top: -5px;">{{vinfo.title}}</h3>
      <table style="height: 50px; margin-top: -38px;">
        <tr>
          <td>
            <h4>{{vinfo.uname}}</h4>
          </td>
          <td>
            <h4>{{fdate(vinfo.date)}}</h4>
          </td>
        </tr>
      </table>
    </div>
    <div>
      <table>
        <tr v-if="showbt == true">
          <td>
            <el-button type="primary" icon="el-icon-edit" @click="changevideo" circle></el-button>
          </td>
          <td>
            <el-button type="danger" icon="el-icon-delete" @click="delvideo" circle></el-button>
          </td>
        </tr>
      </table>
    </div>
    <div>
      <changeFileDialog :fileDialog="fileDialog" :vid="vid" @dialogChangeFileClose="getDialogChangeFileClose">
      </changeFileDialog>
    </div>
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
  import changeFileDialog from '@/components/widget/changefile.vue'
  export default {
    data() {
      return {
        fileDialog: false,
        vid: ''
      }
    },
    props: ['vinfo', 'showbt'],
    methods: {
      changevideo() {
        // console.log("changevideo ::: ",this.vinfo.id)
        this.vid = this.vinfo.id;
        this.fileDialog = true
      },
      getDialogChangeFileClose() {
        this.fileDialog = false
      },
      delvideo() {
        let that = this;
        let formData = new FormData()
        formData.append("vid", that.vinfo.id)
        httppost("aa/video/video/delvideo", formData).then((res) => {
          if (res.code == 0) {
            that.$message.success(res.msg);
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
      },
      fdate(dd) {
        return formatDate(dd)
      },
      vinfo_click(id) {
        // console.log("clicked !!! " , id,this.vinfo)
        // this.$router.push({name : "videoPlayer",params:{
        //   "vinfo": this.vinfo
        // }})

        if (this.$route.path === "/videoplayer") {
          this.$router.replace({
            name: "refreshPage2",
            params: {
              "vinfo": this.vinfo
            }
          })
        } else {
          this.$router.push({
            name: "videoplayer",
            params: {
              "vinfo": this.vinfo
            }
          })
        }


        // this.$router.push({path: "/videoPlayer",query:{"vinfo": }})
      },
      fix_icon() {
        let that = this;
        if (that.vinfo.icon.indexOf("http") === -1) {
          that.vinfo.icon = "aa/video/video/getVideoIcon?vid=" + that.vinfo.id;
        }
      }
    },
    created() {
      this.fix_icon()
    },
    watch: {
      vinfo(v, o) {
        this.fix_icon()
      }
    },
    components: {
      changeFileDialog
    }


  }
</script>

<style>
  .image_st {
    height: 100px;
    width: 140px;
  }

  .video_div_st {
    align-items: center;
    height: auto;
    width: 160px;
  }
</style>
