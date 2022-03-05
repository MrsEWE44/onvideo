import Vue from 'vue'
import Router from 'vue-router'
import IndexPage from '@/components/index.vue'
import videoPlayer from '@/components/videoEl/videoplayer.vue'
import userInfoPage from '@/components/userEl/userInfo.vue'
import viewMore from '@/components/videoEl/viewmore.vue'
import viewDetail from '@/components/videoEl/viewdetail.vue'
import refreshPage from '@/components/refresh.vue'
import refreshPage2 from '@/components/refresh2.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'IndexPage',
      component: IndexPage
    },
    {
      path: '/videoplayer',
      name: 'videoplayer',
      component: videoPlayer
    },{
      path: '/userinfo',
      name: 'userInfoPage',
      component: userInfoPage
    },{
      path: '/viewmore',
      name: 'viewMore',
      component: viewMore
    },{
      path: '/viewdetail',
      name: 'viewdetail',
      component: viewDetail
    },{
      path: '/refreshPage',
      name: 'refreshPage',
      component: refreshPage
    },{
      path: '/refreshPage2',
      name: 'refreshPage2',
      component: refreshPage2
    },
  ]
})
