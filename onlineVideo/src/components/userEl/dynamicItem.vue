<template>

  <div>


    <el-container>
      <el-header>
        <el-row :gutter="20">
          <el-col :span="4">
            <img :src="uinfo.uicon" style="height: 50px; width: 50px;border-radius:100%;" />
          </el-col>
          <el-col :span="20">
            <table>
              <tr>
                <td>
                  <h1>{{uinfo.uname}}</h1>
                </td>
              </tr>
              <tr>
                <td>
                  <p>{{fdate(vinfo.date)}}</p>
                </td>
              </tr>
            </table>
          </el-col>
        </el-row>
      </el-header>
      <el-main>

        <el-container>
          <el-aside width="120px"></el-aside>
          <el-main>
            <el-row :gutter="2">
              <el-col :span="6">
                <div>
                  <img style="width: 170px; height: 170px;" :src="vinfo.icon" />
                </div>
              </el-col>
              <el-col :span="4">
                <table>
                  <tr>
                    <td>
                      <p>{{vinfo.title}}</p>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <p>{{vinfo.msg}}</p>
                    </td>
                  </tr>
                </table>
              </el-col>
            </el-row>
          </el-main>
        </el-container>

      </el-main>
    </el-container>

  </div>

</template>

<script>
  import {
    formatDate
  } from '../../../static/coretools.js'
  export default {
    data() {
      return {

      }
    },
    methods: {
      fdate(d) {
        return formatDate(d)
      },
      fix_icon() {
        let that = this;
        if (that.vinfo.icon.indexOf("http") === -1) {
          that.vinfo.icon = "aa/video/video/getVideoIcon?vid=" + that.vinfo.vid;
        }
      }
    },
    props: ['vinfo', 'uinfo'],
    created() {
      this.fix_icon()
    },
    watch: {
      vinfo(v, o) {
        this.fix_icon()
      }
    }

  }
</script>

<style>
</style>
