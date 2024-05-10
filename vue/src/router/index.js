import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/AllView.vue'

Vue.use(VueRouter)

const routes = [

    {
        path: '/',
        name: '私家车位管理',
        show: true,
        component: () => import('../views/Index.vue'),
        redirect: "/allview",

        children: [
            {
                path: '/allview',
                name: '车位总览',
                show: true,
                pshow: "commonAdmin",
                component: HomeView
            },
            {
                path: '/manager',
                name: '车位管理',
                show: true,
                pshow: "systemAdmin",
                component: () => import( '../views/Manager.vue')
            },
            {
                path: '/searchstall',
                name: '车位查询(管理员)',
                show: true,
                pshow: "systemAdmin",
                component: () => import('../views/SearchStall.vue')
            },
            {
                path: '/searchstalluser',
                name: '车位查询',
                show: true,
                pshow: "commonAdmin",
                component: () => import('../views/SearchStallUser.vue')
            },
            {
                path: '/uploadstall',
                name: '发布车位',
                show: true,
                pshow: "systemAdmin",
                component: () => import('../views/UploadStall.vue')
            },
            {
                path: '/updatestall',
                name: '修改车位信息',
                show: false,
                pshow: "systemAdmin",
                component: () => import('../views/StallUpdate.vue')
            },
            {
                path: '/mapcontainer',
                name: '地图',
                show: false,
                pshow: "commonAdmin",
                component: () => import('../views/MapContainer.vue')
            },
            {
                path: '/detail',
                name: '车位详情',
                show: false,
                component: () => import('../views/DetailView.vue')
            },
            {
                path: '/reser',
                name: '车位预约',
                show: false,
                pshow: "commonAdmin",
                component: () => import('../views/Reser.vue')
            },
            {
                path: '/reservation',
                name: '预约状态',
                show: true,
                pshow: "systemAdmin",
                component: () => import('../views/Reservation')
            },
            {
                path: '/reservationsuser',
                name: '我的预约',
                show: true,
                pshow: "commonAdmin",
                component: () => import('../views/ReservationUser')
            },
            {
                path: '/leaderboord',
                name: '人气榜单',
                show: true,
                pshow: "commonAdmin",
                component: () => import('../views/Leaderboord')
            }
        ]
    },
    {
        path: '/userpage',
        name: '私家车位共享平台-普通用户',
        show: false,
        component: () => import( '../views/Index.vue'),
    },
    {
        path: '/login',
        name: '登录',
        show: false,
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: '注册',
        show: false,
        component: () => import('../views/Register.vue')
    },
    {
        path: '/kvm',
        name: '虚拟机管理',
        component: () => import('../views/Kvm.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
