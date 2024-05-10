<template>
    <div class="login-container">
      <el-form :model="ruleForm" :rules="rules"
               status-icon
               ref="ruleForm"
               label-position="left"
               label-width="0px"
               class="demo-ruleForm login-page">
        <h3 class="title">系统注册</h3>
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

        <el-form-item prop="password2">
          <el-input type="password"
                    v-model="ruleForm.password2"
                    placeholder="密码确认"
          ></el-input>
        </el-form-item>

        <el-form-item prop="key">
          <el-input type="text"
                    v-model=key
                    placeholder="管理员注册密钥"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-radio v-model="ruleForm.roletype" label="systemAdmin">注册管理员</el-radio>
          <el-radio style="position: relative;left: 80px" v-model="ruleForm.roletype" label="commonAdmin">注册普通用户</el-radio>
        </el-form-item>
        <el-form-item style="width: 100%; display: flex; justify-content: center;">
          <el-button type="primary" style="width: 100%;" @click="register2" :loading="regist2">注册</el-button>
        </el-form-item>
  
      </el-form>
    </div>
  </template>
  
  <script>
  export default {
    name: "Register",
    data() {
      return {
        regist2: false,
        ruleForm: {
          username: null,
          password: null,
          roletype: null,
        },
        password2:null,
        key:null,
  
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { validator: this.validusername, trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { validator: this.validPassword, trigger: 'blur' }
          ],
          password2: [
            { required: true, message: '请确认密码', trigger: 'blur' },
            { validator: this.validPassword2, trigger: 'blur' }
          ],
          key:[
            { required: false, trigger: 'blur' },
            { validator: this.validkey, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
        validusername(rule,value,callback){
            axios.post('http://localhost:8282/Users/findusername/'+value).then(function (resp) {
            console.log(resp);
            if (resp.data === "had") {
                callback(new Error('账号已经存在,请更改!'));
              //_this.$router.push("/manager")
            }else{
                callback();
            }
          })
        },
    
        validPassword(rule, value, callback) {
            const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{8,16}$/;
            if (!passwordPattern.test(value)) {
                callback(new Error('密码必须是8-16位的字母和数字组合'));
            } else {
                callback();
            }
        },
        validPassword2(rule, value, callback) {
            if (value !== this.ruleForm.password) {
                callback(new Error('两次密码输入不一致'));
            } else {
                callback();
            }
        },

        validkey(rule, value, callback) {
            if (this.ruleForm.roletype === "systemAdmin") {
                if (this.key!=="lysdsg"&&this.key!==null) {
                  callback(new Error('管理员密钥有误'));
                } 
                else if(this.key===null){
                  callback(new Error('管理员注册需要密钥'))
                }
                else {
                    callback();
                }
            } else {
                callback();
            }
        },
  
      register2(){
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            this.regist2 = true
            let _this = this
            if (_this.ruleForm.roletype !== null) {
              axios.post('http://localhost:8282/Users/save', _this.ruleForm).then(function (resp) {
                _this.regist2 = false
                console.log(resp);
                if (resp.data === "success") {
                  _this.$message.success("注册成功");
                  _this.$router.replace({path: '/login'})
                }
              })
            }
            else{
                _this.message.error("请选择注册角色!");
            }
          }
        })
        
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