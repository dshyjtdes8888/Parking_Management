<template>

  <el-container class="home_container">
    <el-header class="home_header">
      <div class="home_title">私家车位共享平台-{{role==='systemAdmin'?'系统管理员':'普通用户'}}</div>
      <div class="home_userinfoContainer">
        <el-dropdown>
                  <span class="el-dropdown-link home_userinfo">
                    你好，{{ admin }}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
                  </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container style="height: 600px; border: 1px solid #eee">
      <!--左边框-->
      <el-aside width="250px" style="background-color: rgb(238, 241, 246)">

        <el-menu router :default-openeds="['0', '1']">
          <el-submenu v-for="(item,index) in $router.options.routes" :index="index+''" v-if="item.show">
            <template slot="title"><i class="el-icon-circle-plus"></i>{{ item.name }}</template>

            <el-menu-item v-for="(item2,index2) in item.children" :index="item2.path"
                          :class="$router.path===item2.path?'is-active':''"
                          v-if="item2.show && (role === 'systemAdmin' && item2.pshow === 'systemAdmin' || 
                          role === 'commonAdmin' && item2.pshow === 'commonAdmin')">

              <template slot="title"><i class="el-icon-s-operation"></i>{{ item2.name }}
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside> 
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      admin: 'llyy',
      role: 'systemAdmin'
    }
  },
  // created() {
  //   let admin = JSON.parse(window.localStorage.getItem('Admin'));
  //   let role = JSON.parse(window.localStorage.getItem('role'));
  //   this.admin = admin;
  //   this.role = role;
  // },
  methods: {
    logout() {
      let _this = this;
      this.$confirm('注销登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        localStorage.removeItem('Admin')
        localStorage.removeItem('role')
        _this.$router.replace({path: '/login'})
      })
    }
  }
}
</script>

<style scoped>
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home_header {
  background-color: #041243;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.home_title {
  color: #C2C2C2;
  font-size: 22px;
  display: inline;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}


.home_footer {
  background-color: #FFFFFF;
  color: #000000;
  font-size: 18px;
  line-height: 60px;
  text-align: center;
}
</style>