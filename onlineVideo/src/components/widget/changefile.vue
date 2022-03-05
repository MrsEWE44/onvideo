<template>
  <div>

    <el-dialog title="修改文件" :visible.sync="fileDialog" width="30%" :before-close="handleClose">
      <div>
        <div>
          <el-input placeholder="请输入标题" v-model="vinfo.vtitle" clearable>
          </el-input>
        </div>

        <div>
          <el-input type="textarea" placeholder="请输入视频简介" v-model="vinfo.vmsg" maxlength="255" show-word-limit>
          </el-input>
        </div>

        <div>
          <el-upload class="avatar-uploader" action="#" :show-file-list="false" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="uploadfiles">上传</el-button>
        </span>
      </div>
    </el-dialog>


  </div>
</template>

<script>
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  export default {
    data() {
      return {

        imageUrl: '',
        iconfile: '',
        vinfo: ''

      }
    },
    methods: {
      beforeAvatarUpload(file) {
        this.iconfile = file;
        this.imageUrl = URL.createObjectURL(file);
        // console.log("this.imageUrl ::: ",this.imageUrl)
      },

      handleClose(done) {
        let that = this;
        that.$confirm('确认关闭？')
          .then(_ => {
            that.close()
            done();
          })
          .catch(_ => {});
      },
      close() {
        this.$emit("dialogChangeFileClose", true)
      },
      uploadfiles(p) {
        let that = this;
        let formData = new FormData()
        formData.append("icon", that.iconfile)
        formData.append("vid", that.vinfo.vid)
        formData.append("title", that.vinfo.vtitle)
        formData.append("msg", that.vinfo.vmsg)

        // console.log("imageUrl :: ",that.imageUrl,that.vinfo)

        httppost("aa/video/video/changevideo", formData).then((res) => {
          if (res.code == 0) {
            that.$message.success(res.msg);
            that.close()
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
      },
      getChangeVideoInfo() {
        let that = this
        httpget("aa/video/video/getchangevideo?vid=" + that.vid).then(
          (res) => {
            if (res.code == 0) {
              that.vinfo = res.data
              console.log("vinfo ::: ", that.vinfo)
            } else {
              this.$message.error(res.msg);
            }
          }
        ).catch((err) => {
          that.$message.error(err.request.response);
        })
      },

    },
    props: ['fileDialog', 'vid'],
    watch: {
      fileDialog(v, o) {
        this.getChangeVideoInfo()
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
