<template>
  <div class="login-container">
    <el-form :model="ruleForm" :rules="rules"
             status-icon
             ref="ruleForm"
             label-position="left"
             label-width="0px"
             class="demo-ruleForm login-page">
      <h3 class="title">系统登录</h3>
      <el-form-item prop="username">
        <el-input type="text"
                  v-model="ruleForm.username"
                  placeholder="用户名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password"
                  v-model="ruleForm.password"
                  placeholder="密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-radio v-model="ruleForm.usertype" label="systemAdmin">系统管理员</el-radio>
        <el-radio style="position: relative;left: 80px" v-model="ruleForm.usertype" label="commonAdmin">普通用户</el-radio>
      </el-form-item>
      <el-form-item style="width: 100%; display: flex; justify-content: center;">
        <el-button type="primary" style="width: 40%; margin-right: 20px;" @click="handleSubmit" :loading="logining">登录</el-button>
        <el-button type="primary" style="width: 40%;" @click="register" :loading="regist">注册</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      logining: false,
      regist: false,
      ruleForm: {
        username: '',
        password: '',
        usertype: '',
      },

      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      }
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.logining = true
          let _this = this
          if (_this.ruleForm.usertype === 'commonAdmin') {

            axios.post('http://localhost:8282/Users/systemAdmin', _this.ruleForm).then(function (resp) {
              _this.logining = false
              console.log(resp);
              if (resp.data === -1) {
                _this.$alert('用户名不存在', '提示', {
                  confirmButtonText: '确定'
                })
              }
              if (resp.data === -2) {
                _this.$alert('密码错误', '提示', {
                  confirmButtonText: '确定'
                })
              }
              if (resp.data === 0) {
                //跳转到SystemAdmin
                //展示当前登录用户信息
                localStorage.setItem('Admin', JSON.stringify(_this.ruleForm.username));
                localStorage.setItem('role', JSON.stringify(_this.ruleForm.usertype));
                _this.$router.replace({path: '/allview'})
              }
            })
          }
          if (_this.ruleForm.usertype === 'systemAdmin') {
            console.log(123);

            axios.post('http://localhost:8282/Users/systemAdmin', _this.ruleForm).then(function (resp) {
              _this.logining = false
              console.log(resp.data);
              if (resp.data === -1) {
                _this.$alert('用户名不存在', '提示', {
                  confirmButtonText: '确定'
                })
              }
              if (resp.data === -2) {
                _this.$alert('密码错误', '提示', {
                  confirmButtonText: '确定'
                })
              }
              if (resp.data === 0) {
                //跳转到SystemAdmin
                //展示当前登录用户信息
                localStorage.setItem('Admin', JSON.stringify(_this.ruleForm.username));
                localStorage.setItem('role', JSON.stringify(_this.ruleForm.usertype));
                _this.$router.replace({path: '/manager'})
              }
            })
          }
        }
      })
    },

    register(){
      this.regist=true;
      this.$router.replace({path: '/register'})
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 100%;
  height: 100%;
}

.login-page {
  -webkit-border-radius: 5px;
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

label.el-checkbox.rememberme {
  margin: 0px 0px 15px;
  text-align: left;
}
</style>