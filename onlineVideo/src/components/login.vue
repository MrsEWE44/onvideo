<template>
  <div>

    <el-dialog title="登录" :visible.sync="showdialog" width="30%" :before-close="handleClose">


      <el-form :model="ruleForm" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="accout">
          <el-input v-model="ruleForm.accout" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="reg">注 册</el-button>
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="loginsub">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  import {
    httpget,
    httppost
  } from '../../static/request.js'
  export default {
    data() {
      return {
        ruleForm: {
          accout: '',
          pass: ''
        },
      }
    },
    methods: {
      reg() {
        var loginuser = {
          uid: this.ruleForm.accout,
          pwd: this.ruleForm.pass
        }

        httppost("aa/video/user/reg", JSON.stringify(loginuser)).then(
          (res) => {
            if (res.code == 0) {
              this.$message({
                message: '注册成功!',
                type: 'success'
              });
              this.close()
            } else {
              this.$message.error(res.msg);
            }
          }
        )
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            this.close()
            done();
          })
          .catch(_ => {});
      },
      close() {
        // this.ruleForm.accout=""
        // this.ruleForm.pass=""
        // this.ruleForm.vcode=""
        this.$emit("loginclose", false)
      },
      loginsub() {
        var loginuser = {
          uid: this.ruleForm.accout,
          pwd: this.ruleForm.pass
        }

        httppost("aa/video/user/login", JSON.stringify(loginuser)).then(
          (res) => {
            if (res.code == 0) {
              this.$message({
                message: '登录成功!',
                type: 'success'
              });
              this.close()
            } else {
              this.$message.error(res.msg);
            }
            // console.log("res ::: ",res)
          }
        )
      }
    },
    props: ['showdialog']
  }
</script>

<style>
</style>
