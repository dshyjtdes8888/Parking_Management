<template>
  <el-form style="width: 80%" :model="form" :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
    <el-form-item label="车位id" prop="sid">
      <el-input v-model="form.sid" readonly :disabled=true></el-input>
    </el-form-item>
    <el-form-item label="小区名称" prop="小区">
      <el-input v-model="form.小区" readonly :disabled=true></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="customerName">
      <el-input v-model="form.customerName"></el-input>
    </el-form-item>
    <el-form-item label="联系电话" prop="phonenumber">
      <el-input v-model="form.phonenumber"></el-input>
    </el-form-item>
    <div class="block">
      <el-form-item label="预约日期" prob="usedate">
        <el-date-picker
            v-model="form.date"
            value-format="yyyy-MM-dd"
            prop="usedate"
            type="date"
            placeholder="选择日期"
            :formatter="dateFormat"
            :picker-options="pickerOptions">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="起始时间">
        <el-time-select
            v-model="form.begintime"
            prop="begintime"
            :picker-options="{
              start: '00:00',
              step: '01:00',
              end: '24:00'
            }"
            placeholder="选择时间">
        </el-time-select>
      </el-form-item>

      <el-form-item label="结束时间">
        <el-time-select
            v-model="form.endtime"
            prop="endtime"
            :picker-options="{
              start: '00:00',
              step: '01:00',
              end: '24:00'
            }"
            placeholder="选择时间">
        </el-time-select>
      </el-form-item>
      
    </div>
    <h1></h1>

    <el-form-item>
      <el-button type="primary" @click="onSubmit('form')">确认预约</el-button>
      <el-button type="primary" @click="onCancle">取消</el-button>
    </el-form-item>
  </el-form>
</template> 


<script>
export default {
  name: "Reser",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          const today = new Date();
          today.setHours(0, 0, 0, 0); // 设置时间为 00:00:00否则不能选当天！
          return time.getTime() < today.getTime();
        },
      },

      ruleForm: {
        id: null,
        小区: null,
        当前状态:null,
        租金: null,
        可用时间段: null,
        位置: null,
        评分: null
      },

      form: {
        id: null,
        sid: null,
        小区: null,
        customerName: null,
        phonenumber: null,
        usedate: "",
        begintime: "",
        endtime: "",
      },

      rules: {
        customerName: [
          {required: true, message: '请输入您的姓名', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        phonenumber: [
          {required: true, message: '请输入您的联系方式', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      }
    }
  },

  methods: {
    dateFormat: function (date) {
      return moment(date).format("YYYY-MM-DD")
    },

    onCancle() {
      this.$router.push({
        path: "/detail",
        query: {
          id: this.ruleForm.id
        }
      })
    },

    getHour(timeString) {
      const hourStr = timeString.substring(0, 2);
      return parseInt(hourStr, 10); // 将字符串转换为整数
    },

    onSubmit(formName) {
      if(this.form.begintime===""||this.form.date===""||this.form.endtime===""){
        this.$message.error('预约时间不完整');
        return;
      }
      const times = this.ruleForm.可用时间段.split("-"); // 将时间段字符串拆分为开始时间和结束时间
      const startTime = parseInt(times[0], 10); // 提取开始时间并转换为整数
      const endTime = parseInt(times[1], 10); // 提取结束时间并转换为整数
      if(this.getHour(this.form.begintime)<startTime||this.getHour(this.form.endtime)>endTime){
        this.$message.error('预约时间不在车位可用时间段内');
        return;
      }
      

      //console.log('submit!');
      const _this = this;
      console.log('form', _this.form)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //alert('submit!');
          axios.post('http://localhost:8282/Reservations/save', _this.form).then(function (resp) {
            console.log(resp);
            if (resp.data == "success") {
              _this.$message.success('预约成功');
              _this.$router.push({
                path: "/detail",
                query: {
                  id: _this.form.sid
                }
              })
            }
          })
          console.log(_this.ruleForm)
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  },

  created() {
    this.ruleForm = this.$route.query.Stall;
    this.form.sid = JSON.parse(JSON.stringify(this.ruleForm.id));
    this.form.小区 = JSON.parse(JSON.stringify(this.ruleForm.小区));
    console.log(this.ruleForm)
  }
}
</script>

<style scoped>

</style>