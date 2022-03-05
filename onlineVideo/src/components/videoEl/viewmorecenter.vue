<template>
  <div>
    <el-container>
      <el-header>
        <div>
          <el-popover placement="bottom-start" width="400" trigger="hover" v-for="(item,index) in headMenu"
            :key="index">
            <div v-if="item.childer.length > 0">
              <el-button v-for="(citem,index2) in item.childer" :key="index2" @click="headMenuClick(index,index2)"
                plain>{{citem.title}}</el-button>
            </div>
            <div v-else>
              <el-button slot="reference" @click="headMenuClick(index,index)" plain>{{item.title}}</el-button>
            </div>
            <el-button slot="reference" @click="headMenuClick(index,index)" plain>{{item.title}}</el-button>
          </el-popover>
        </div>
      </el-header>

      <el-footer>
        <div>
          <el-button v-for="(item,index) in menuItem.childer" :key="index" slot="reference" class="headmenu2"
            @click="headMenu2Click(menuItem.paid,index)" plain>{{item.title}}</el-button>
        </div>
      </el-footer>

      <el-main>

        <div>
          <el-row :gutter="10">
            <el-col :span="10">
              <div>
                <el-carousel height="430px" type="card">
                  <el-carousel-item v-for="(item,index) in leftImages" :key="index">
                    <div @click="carclick(index)">
                      <img v-if="item.icon === ''" :src="`aa/video/video/getVideoIcon?vid=${item.id}`" />
                      <img v-else :src="item.icon" />
                    </div>
                  </el-carousel-item>
                </el-carousel>
              </div>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-col span="6" v-for="(item, index) in videoList" :key="index">
                  <showVideoInfo :vinfo="item"></showVideoInfo>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="2">
              <el-button icon="el-icon-refresh-right" @click="refreshVideo" circle></el-button>
            </el-col>
          </el-row>
        </div>


      </el-main>

    </el-container>


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
        leftImages: [],
        videoList: [],
        headMenu: [],


      }
    },
    props: ['menuItem'],
    methods: {
      carclick(index) {
        if (this.$route.path === "/videoplayer") {
          this.$router.replace({
            name: "refreshPage2",
            params: {
              "vinfo": this.leftImages[index]
            }
          })
        } else {
          this.$router.push({
            name: "videoplayer",
            params: {
              "vinfo": this.leftImages[index]
            }
          })
        }
      },
      getLeftImages() {
        let that = this
        httpget("aa/video/video/getLeftImages2?fid=" + that.menuItem.paid + "&limit=6").then(
          (res) => {
            if (res.code == 0) {
              that.leftImages = res.data
            } else {
              that.$message.error(res.msg);
            }
          }
        )
      },
      headMenuClick(paid, chpaid) {
        //console.log("headMenuClick viewmore  ::: " ,paid," -- " ,chpaid,this.headMenu[paid])
        this.menuItem = this.headMenu[paid]
        this.getVideoList()
        this.$emit("newitem", this.menuItem)
      },

      headMenu2Click(paid, chpaid) {
        // console.log("headMenu2Click viewmore  ::: " ,paid," -- " ,chpaid,this.menuItem.childer[chpaid-1])
        var tt = []
        tt.push(this.menuItem.childer[chpaid])
        var vvv = {
          "paid": paid,
          "chpaid": chpaid,
          "childer": tt
        }
        this.$emit("newchilder", vvv)
        // this.$router.push({path : "/viewmore2",query:{"ppp": this.headMenu[paid]}})
      },

      getVideoList() {
        let that = this
        httpget("aa/video/video/getChilder?fid=" + that.menuItem.paid).then(
          (res) => {
            if (res.code == 0) {
              that.videoList = res.data
            } else {
              that.$message.error(res.msg);
            }
          }
        )
      },

      getHeadMenu() {
        let that = this
        httpget("aa/video/menu/getMenus").then(
          (res) => {
            if (res.code == 0) {
              that.headMenu = res.data
            } else {
              this.$message.error(res.msg);
            }
          }
        )
      },
      refreshVideo() {
        let that = this
        httpget("aa/video/video/getRefreshVideo?fid=" + that.menuItem.paid).then(
          (res) => {
            if (res.code == 0) {
              that.videoList = res.data
            } else {
              that.$message.error(res.msg);
            }
          }
        )
      }

    },
    components: {
      showVideoInfo
    },
    created() {
      this.getHeadMenu()
      this.getVideoList()
      this.getLeftImages()
    }

  }
</script>

<style>
  .headmenu2: {
    height: 20px;
  }
</style>
