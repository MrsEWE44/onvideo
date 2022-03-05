<template>
  <div>

    <el-dialog title="上传文件" :visible.sync="fileDialog" width="30%" :before-close="handleClose">
      <div>
        <div>
          <el-input placeholder="请输入标题" v-model="inputtitle" clearable>
          </el-input>
        </div>

        <div>

          <el-cascader :options="menus" :props="{ value: 'paid',label: 'title',children : 'childer' }"
            @change="cascaderChange">
            <template slot-scope="{ node, data }">
              <span>{{ data.title }}</span>
              <span v-if="!node.isLeaf"> ({{ data.childer.length }}) </span>
            </template>
          </el-cascader>


        </div>

        <div>
          <el-input type="textarea" placeholder="请输入视频简介" v-model="videomsg" maxlength="255" show-word-limit>
          </el-input>
        </div>


        <div>
          <el-upload class="upload-demo" drag ref="upload" :auto-upload="false" :http-request="uploadfiles" action=""
            :limit="1">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将视频文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </div>


        <div>

          <el-upload class="avatar-uploader" action="#" :show-file-list="false" :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" @click="upload_file">上传</el-button>
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

        inputtitle: '',
        menus: [],
        videomsg: '',
        fid: 0,
        mid: 0,
        imageUrl: '',
        iconfile: ''

      }
    },
    methods: {
      beforeAvatarUpload(file) {
        this.iconfile = file;
        this.imageUrl = URL.createObjectURL(file);
        // console.log("this.imageUrl ::: ",this.imageUrl)
      },
      cascaderChange(a) {
        // console.log("cascaderChange ::: ",a)
        this.fid = a[0]
        this.mid = a[1]
        // console.log("fid ::: ",this.fid, " -- mid :: ",this.mid, " --- ",a)
      },
      getMenus() {
        let that = this
        httpget("aa/video/menu/getMenus").then(
          (res) => {
            if (res.code == 0) {
              that.menus = res.data
            } else {
              this.$message.error(res.msg);
            }
          }
        )
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
        this.$emit("dialogUploadFileClose", true)
      },
      uploadfiles(p) {
        let that = this;
        let formData = new FormData()
        formData.append("file", p.file)
        formData.append("msg", that.videomsg)
        formData.append("fid", that.fid)
        formData.append("mid", that.mid)
        formData.append("title", that.inputtitle)
        formData.append("icon", that.iconfile)

        httppost("aa/video/user/uploadFile", formData).then((res) => {
          if (res.code == 0) {
            that.$message.success(res.msg);
            that.close()
            that.$router.go(0)
          }
        }).catch((err) => {
          that.$message.error(err.request.response);
          // that.$router.push({name : 'IndexPage'})
        })
        // console.log("uploadfiles :::: ",p)
      },
      upload_file() {
        this.$refs.upload.submit();
      },
    },
    props: ['fileDialog'],
    watch: {
      fileDialog(v, o) {
        this.getMenus()
      }
    },created() {
      this.getMenus()
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
