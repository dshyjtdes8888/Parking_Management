<template>
    <div>
      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="id"
            label="编号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="sid"
            label="车位编号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="小区"
            label="小区名称"
            width="200">
        </el-table-column>
        <el-table-column
            prop="customerName"
            label="客户姓名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="phonenumber"
            label="客户电话"
            width="300">
        </el-table-column>
        <el-table-column
            prop="date"
            label="预约日期"
            width="200">
        </el-table-column>
        <el-table-column label="预约时间" width="200">
         <template slot-scope="scope">
           {{ scope.row.begintime }} ~ {{ scope.row.endtime }}
         </template>
        </el-table-column>
        <el-table-column
            prop="state"
            label="状态"
            width="170">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="200">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">完成</el-button>
            <el-dialog title="请为车位进行评价" :visible.sync="dialogFormVisible" :append-to-body="true">
              <el-row>
                <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入内容(100字以内)"
                v-model="comment.textarea"
                style="width: 80%">
              </el-input>

              <div class="spacer"></div>

              <el-button type="primary" @click="submitForm(comment)">发表评论</el-button>
              </el-row>

              <div class="spacer"></div>
              
              <el-form style="width: 40%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="80px" class="demo-ruleForm">
                <el-form-item label="评分" prop="评分">
                  <el-input 
                  :rows="2"
                  placeholder="请输入评分0~5"
                  v-model="ruleForm.评分">
                </el-input>

                <div class="spacer"></div>

                <el-button type="primary" @click="Newgrade(ruleForm.评分)" style="float: right;">提交评分</el-button>
                </el-form-item>
              </el-form>
          </el-dialog>
          <el-button @click="runinfo(scope.row)" type="text" size="small">开始使用</el-button>
          <el-button @click="cancelinfo(scope.row)" type="text" size="small" >取消</el-button>
        </template>
  
        </el-table-column>
      </el-table>
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size=10
        :total="total"
        @current-change="page">
    </el-pagination>

    </div>
  </template>
  
  <script>
  export default {
    name: "ReservationUser",
    methods: {
      cancelinfo(row) {
        const _this = this;
        if(row.state==='已取消'||row.state==='结束'){
        _this.$message.error('订单已经取消或结束');
        return;
       }
       if(row.state==='使用中'){
        _this.$message.error('订单开始,无法取消');
        return;
       }
        axios.post("http://localhost:8282/Reservations/cancel/", row).then(function (resp) {
          if (resp.data === "success") {
            _this.$message.success('修改成功');
            // _this.$router.push("/manager")
            location.reload();
          }
        })
      },

      runinfo(row) {
        const _this = this;
        const date = new Date();
        _this.nowhour = date.getHours();
        const beginhour = parseInt(row.begintime.split(":")[0], 10);
        const endhour = parseInt(row.endtime.split(":")[0], 10);
        // 获取当前日期
        const currentDate = new Date();
        const nowDate = currentDate.toISOString().slice(0, 10);

        if(row.state==='已取消'||row.state==='结束'){
        _this.$message.error('订单已经取消或结束');
        return;
       }
       else if(row.state==='使用中'){
        _this.$message.error('车位正在使用');
        return;
       }
      else if(beginhour>_this.nowhour||nowDate<row.date){
        _this.$message.error('还未到达预约时间');
        return;
       }
       else if(endhour<=_this.nowhour||nowDate>row.date){
        _this.$message.error('超过预约时间,已为您自动取消订单');
        _this.cancelinfo(row);
        return;
       }
       else{axios.post("http://localhost:8282/Reservations/run/", row).then(function (resp) {
          if (resp.data === "success") {
            _this.$message.success('修改成功');
            // _this.$router.push("/manager")
            location.reload();
          }
        })
      }
      },

  
      handleClick(row) {
        const _this = this;
        if(row.state==='结束'||row.state==='已取消'){
          _this.$message.error('订单已经取消或结束');
          return;
        }
        if(row.state==='已预约'){
          _this.$message.error('订单还未开始,不能结束');
          return;
        }
        axios.post('http://localhost:8282/Reservations/update', row).then(function (resp) {
          console.log(resp);
          if (resp.data === "success") {
            _this.$message.success('修改成功');
          axios.get("http://localhost:8282/Stall/findById/" + row.sid).then(function (resp) {
             _this.ruleForm = resp.data;
             _this.dialogFormVisible=true;
            });
          }
          //location.reload();
          });
      },
  
      page(currentPage) {
        const _this = this;
        axios.get('http://localhost:8282/Reservations/findAll/' + currentPage + '/' + 10).then(function (resp) {
          _this.tableData = resp.data.content;
          _this.total = resp.data.totalElements;
        })
      },

      submitForm(mycomment) {
      if (mycomment.textarea !== '') {
        const _this = this;
        mycomment.id = _this.ruleForm.id;
        axios.post('http://localhost:8282/Comments/save', mycomment).then(function (resp) {
          console.log(resp);
          if (resp.data == "success") {
            _this.$message.success('评论发布成功');
            _this.comment = '';
            // _this.$router.push("/housingmanager")
          }
        })
        console.log(mycomment);
      }
    },

    Newgrade(grade){
      const _this=this;
      if(grade!==''&&parseFloat(grade)>=0&&parseFloat(grade)<=5){
        axios.post('http://localhost:8282/Stall/upgrade/'+_this.ruleForm.id+'/'+_this.ruleForm.评分).then(function (resp) {
          console.log(resp);
          if (resp.data == "success") {
            _this.$message.success('评分提交成功');
            // _this.$router.push("/housingmanager")
          }
        })
      }
      else{
        _this.$message.error('评分格式有误');
      }
    },

  },
  
    created() {
      if(localStorage.getItem('Admin')==null){
        this.$router.replace({path: '/login'});
      }
      const _this=this;
      axios.get('http://localhost:8282/Reservations/findAll/1/' + 10).then(function (resp) {
        _this.tableData = resp.data.content;
        _this.total = resp.data.totalElements;
      })
    },
  
    data() {
      return {
        total: null,
        pageSize: 10,
        tableData: [],
        dialogFormVisible: false,
        comment: {
        textarea: '',
        commentid: '',
        nowhour: 0,
      },

      ruleForm: {
        id: null,
        小区: null,
        当前状态:null,
        可用时间段:null,
        租金: null,
        评分: null,
        图片: null
      },
      textarea: '',

      rules: {
          评分: [
            {required: true, message: '请输入评分', trigger: 'blur'},
            {
              pattern: /^(?:[0-5](?:\.\d+)?|5)$/,
              message: '评分为0-5之间的小数或整数',
              trigger: 'blur'
            }
          ],
        }

      }
    }

  }
  </script>
  
  <style scoped>
  .spacer {
  height: 20px; 
}

  </style>