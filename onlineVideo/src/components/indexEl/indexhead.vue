<template>
  <div>

    <el-row :gutter="20">
      <el-col :span="7">
        <div>
          <el-row :gutter="4">
            <el-col v-for="(leftMenuItem ,index) in leftMenu" :span="leftMenuItem.span">
              <div class="ast">
                <a :href="leftMenuItem.hll">{{leftMenuItem.title}}</a>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="9">
        <div class="search_elmm">
          <el-input placeholder="请输入内容" v-model="searchValue" clearable>
          </el-input>
        </div>
      </el-col>
      <el-col :span="1">
        <div class="search_elmm">
          <el-button class="search_but" icon="el-icon-search" @click="searchVideo" circle></el-button>
        </div>
      </el-col>
      <el-col :span="7">
        <div>
          <el-row :gutter="4">
            <el-col :span="4">
              <div v-if="haveu == true" @click="intoUser">
                <img :src="userInfo.uicon" style="height: 50px; width: 50px;border-radius:100%;" />
                <el-button @click="logout" plain>注销</el-button>
              </div>
              <div v-else>
                <el-button @click="intoLogin" plain>登陆/注册</el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <LoginDialog :showdialog="showLogin" @loginclose="loginclose"></LoginDialog>
  </div>
</template>

<script>
  import {
    getCookie,
    delCookie
  } from '../../../static/coretools.js'
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  import LoginDialog from '@/components/login.vue'
  export default {
    data() {
      return {
        userInfo: '',
        searchValue: '',
        leftMenu: [{
          title: "首页",
          hll: "/",
          span: 6
        }],
        haveu: false,
        showLogin: false
      }
    },
    methods: {
      logout() {
        let that = this;
        httpget("aa/video/user/logout").then(
          (res) => {
            if (res.code == 0) {
              delCookie("uid")
              that.$message.success(res.msg);
            } else {
              that.$message.error(res.msg);
            }
          }
        )
      },
      loginclose() {
        this.showLogin = false
      },
      intoLogin() {
        // console.log("intoLogin !!")
        this.showLogin = true
      },

      haveUser() {
        var uu = getCookie("uid");
        if (uu) {
          let that = this;
          httpget("aa/video/user/getUserInfo").then(
            (res) => {
              if (res.code == 0) {
                that.haveu = true
                that.userInfo = res.data;
                if (that.userInfo.uicon === "" || that.userInfo.uicon === undefined) {
                  that.userInfo.uicon = "aa/video/user/getUserIcon?uid=" + that.userInfo.uid
                }
              } else {
                that.$message.error(res.msg);
              }
            }
          )
        }
      },

      searchVideo() {
        // console.log('this.searchValue :::: ',this.searchValue)
        if (this.$route.path === "/viewdetail") {
          this.$router.replace({
            name: "refreshPage",
            params: {
              "vvv": this.searchValue,
              "isSearch": true
            }
          })
        } else {
          this.$router.push({
            name: "viewdetail",
            params: {
              "vvv": this.searchValue,
              "isSearch": true
            }
          })
        }

      },
      intoUser() {
        this.$router.push({
          name: "userInfoPage",
          params: {
            isMe: "true"
          }
        })
      }
    },
    components: {
      LoginDialog
    },
    created() {
      this.haveUser()
    }
  }
</script>

<style>
  .search_elmm {
    margin-top: 10px;
    height: 20px;
  }

  .search_but {
    background: rgba(255, 255, 255, 0.247);
  }

  .el-input__inner {
    height: 47px;
    background-color: rgba(255, 255, 255, 0.247);
  }

  .ast {
    margin-top: 10px;
    font-size: 20px;
  }

  .ast:hover {
    background-color: #42B983;
  }
</style>
