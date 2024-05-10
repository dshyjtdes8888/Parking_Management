<template>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="小区" prop="小区">
        <el-input v-model="ruleForm.小区"></el-input>
      </el-form-item>
  
      <el-form-item label="当前状态" prop="当前状态">
        <el-select v-model="ruleForm.当前状态" placeholder="请选择车位状态">
          <el-option label="使用中" value="使用中"></el-option>
          <el-option label="空闲中" value="空闲中"></el-option>
          <el-option label="已预约" value="已预约"></el-option>
        </el-select>
      </el-form-item>
  
      <el-form-item label="租金" prop="租金">
        <el-input v-model="ruleForm.租金"></el-input>
      </el-form-item>
  
      <el-form-item label="可用时间段" prop="可用时间段">
      <el-select v-model="ruleForm.可用时间段" placeholder="请选择可用时间段">
        <el-option label="8:00-12:00" value="8:00-12:00"></el-option>
        <el-option label="8:00-19:00" value="8:00-19:00"></el-option>
        <el-option label="8:00-24:00" value="8:00-24:00"></el-option>
        <el-option label="10:00-12:00" value="10:00-12:00"></el-option>
        <el-option label="10:00-19:00" value="10:00-19:00"></el-option>
        <el-option label="10:00-24:00" value="10:00-24:00"></el-option>
        <el-option label="14:00-19:00" value="14:00-19:00"></el-option>
        <el-option label="14:00-24:00" value="14:00-24:00"></el-option>
        <el-option label="19:00-24:00" value="19:00-24:00"></el-option>
        <el-option label="24:00以后" value="24:00以后"></el-option>
        <el-option label="全天" value="全天"></el-option>
      </el-select>
    </el-form-item>
  
      <el-form-item label="具体位置" prop="具体位置">
        <el-input v-model="ruleForm.具体位置"></el-input>
      </el-form-item>
  
      <el-form-item label="联系电话" prop="联系电话">
        <el-input v-model="ruleForm.联系电话"></el-input>
      </el-form-item>

      <el-upload class="avatar-uploader" action="http://localhost:8282/Stall/upload" 
        :show-file-list="false"
        :on-success="handleAvatarSuccess" 
        :before-upload="beforeAvatarUpload">
        <div class="upload-text" v-if="!ruleForm.图片">点击此处上传车位图片</div>
        <img v-if="ruleForm.图片" :src="ruleForm.图片" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"/>
      </el-upload>

      <div class="spacer"></div>

  
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">发布</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script>
  export default {
    name: "StallUpdate",
    data() {
      return {
        value: null,
        ruleForm: {
          id: null,
          小区: null,
          当前状态: null,
          租金: null,
          可用时间段: null,
          具体位置: null,
          联系电话:null,
          图片: null
        },
        rules: {
          小区: [
            {required: true, message: '请输入小区名称', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
          当前状态: [
            {required: true, message: '请选择车位状态', trigger: 'blur'},
            {min: 1, max: 5, message: '长度在  1 到 5 个字符', trigger: 'blur'}
          ],
          租金: [
            { required: true, message: '请输入车位租金', trigger: 'blur' },
            {
               validator: (rule, value, callback) => {
               const numericValue = Number(value);
               if (isNaN(numericValue) || !Number.isInteger(numericValue)) {
                  callback(new Error('车位租金必须是有效的整数'));
               } else {
                  callback();
               }
               },
               trigger: 'blur'
            }
          ],
          可用时间段: [
            {required: true, message: '请输入车位可用时间段', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
          具体位置: [
            {required: true, message: '请输入车位具体位置', trigger: 'blur'},
            {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
          ],
          联系电话:[
            {required: true, message: '请输入您的联系电话' , trigger: 'blur'},
            {min: 1, max:11, message: '长度在 1 到 11 个字符', trigger: 'blur'}
          ]
        }
      };
    },
  
    methods: {
    submitForm(formName) {
      const _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8282/Stall/saveall', this.ruleForm).then(function (resp) {
            console.log(resp);
            if (resp.data == "success") {
              _this.$message.success('发布成功');
              _this.$router.push("/manager")
            }
            if(resp.data == "error")
            {
              _this.$message.error('发布失败，没有该小区！');
              //_this.$router.push("/manager")
            }
          })
          console.log(_this.ruleForm)
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG) {
					this.$message.error('上传头像图片只能是 JPG 格式!');
				}
				if (!isLt2M) {
					this.$message.error('上传图片大小不能超过 2MB!');
				}
        const formData = new FormData();
        formData.append('file', file);
        axios.post('http://localhost:8282/Stall/upload', formData)
        .then(response => {
        this.handleAvatarSuccess(response, file);
        this.ruleForm.图片=response.data;
      })
				return isJPG && isLt2M;
			},
			handleAvatarSuccess(res, file){
				//this.ruleForm.图片 = URL.createObjectURL(file.raw);
				console.log(file)
			},


    },
  }
  
  </script>
  
  <style scoped>
  .upload-text {
  text-align: center;
  font-size: 14px;
  color: #999;
  margin-bottom: 10px;
}

.avatar-uploader {
  width: 300px; 
  height: 300px;
  border: 1px solid #ccc;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #999;
}

.spacer {
  height: 20px; 
}



  </style>