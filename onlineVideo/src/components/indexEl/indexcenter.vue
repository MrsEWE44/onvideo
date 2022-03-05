<template>
  <div>

    <el-container>
      <el-header>
        <div>
          <el-popover placement="bottom-start" width="400" trigger="hover" v-for="(item,index) in headMenu"
            :key="index">
            <div v-if="item.childer.length > 0">
              <el-button v-for="(citem,index2) in item.childer" :key="index2"
                @click="headMenuClick(item.paid,citem.paid)" plain>{{citem.title}}</el-button>
            </div>
            <div v-else>
              <el-button slot="reference" @click="headMenuClick(item.paid,item.paid)" plain>{{item.title}}</el-button>
            </div>
            <el-button slot="reference" @click="headMenuClick(item.paid,item.paid)" plain>{{item.title}}</el-button>
          </el-popover>
        </div>
      </el-header>
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
                <el-col span="6" v-for="(item, index) in videoList2" :key="index">
                  <showVideoInfo :vinfo="item"></showVideoInfo>
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="2">
              <el-button icon="el-icon-refresh-right" @click="refreshHeadVideo" circle></el-button>
            </el-col>
          </el-row>
        </div>
      </el-main>
    </el-container>


  </div>
</template>

<script>
  import showVideoInfo from '@/components/widget/showvideoinfo.vue'
  export default {
    data() {
      return {
        videoList2: ''
      }
    },
    props: ['headMenu', 'leftImages', 'videoList'],
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
        // console.log("carclick :::: ",index,this.leftImages[index])
      },
      headMenuClick(paid, chpaid) {
        this.$router.push({
          name: "viewMore",
          params: {
            "ppp": this.headMenu[paid - 1]
          }
        })

      },
      refreshHeadVideo() {
        this.$emit("refreshVideo", true)
      }
    },
    watch: {
      videoList(v, o) {
        this.videoList2 = v
      },
      leftImages(v, o) {
        console.log("leftImages v ::: ", v)
      }
    },
    components: {
      showVideoInfo
    }

  }
</script>

<style>

</style>
