<template>
  <div style="background:rgba(153, 255, 204,0.5);">
    <div>
      <el-container>
        <el-container>
          <el-aside width="200px"></el-aside>
          <el-container>
            <el-main>
              <el-row>
                <el-col span="6" v-for="(item, index) in searchVideos" :key="index">
                  <showVideoInfo :vinfo="item"></showVideoInfo>
                </el-col>
              </el-row>
            </el-main>
          </el-container>

        </el-container>

      </el-container>

    </div>

    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[5,10, 20, 30, 40,50,100,200,300,400,500,1000]" :page-size="pagesize" :hide-on-single-page="ishide"
        layout="total, sizes, prev, pager, next, jumper" :total="pagetotal">
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import {
    httpget,
    httppost
  } from '../../../static/request.js'
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  export default {
    data() {
      return {
        searchVideos: [],
        currentPage: 1,
        ishide: false,
        pagesize: 5,
        pagetotal: 20
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pagesize = val;
        this.getSearchVideos()
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getSearchVideos()
      },

      getSearchVideos() {
        let that = this;
        if (that.isSearch == true) {
          let pp = {
            page: that.currentPage,
            size: that.pagesize,
            search: that.searchvalue
          }

          httppost("aa/video/video/searchVideo", JSON.stringify(pp)).then((res) => {
            if (res.code == 0) {
              that.searchVideos = res.data
              that.pagetotal = res.total
              if (that.pagetotal <= that.pagesize) {
                that.ishide = true
              }
            }
          }).catch((err) => {
            that.$message.error(err.request.response);
            // that.$router.push({name : 'IndexPage'})
          })
        } else {
          let pp = {
            page: that.currentPage,
            size: that.pagesize,
            fid: that.searchvalue.fid,
            mid: that.searchvalue.chid
          }
          httppost("aa/video/video/getChilder3", JSON.stringify(pp)).then((res) => {
            if (res.code == 0) {
              that.searchVideos = res.data
              that.pagetotal = res.total
              if (that.pagetotal <= that.pagesize) {
                that.ishide = true
              }
            }
          }).catch((err) => {
            that.$message.error(err.request.response);
            // that.$router.push({name : 'IndexPage'})
          })
        }
      }

    },
    props: ['searchvalue', 'isSearch'],
    components: {
      showVideoInfo
    },
    created() {
      this.getSearchVideos()
    },
    watch: {

    }

  }
</script>

<style>
</style>
