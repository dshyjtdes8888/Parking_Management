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
          <el-button @click="deleteinfo(scope.row)" type="text" size="small">删除</el-button>
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
  name: "Reservation",
  methods: {

    deleteinfo(row) {
      const _this = this;
      axios.delete("http://localhost:8282/Reservations/delete/" + row.id).then(function (resp) {
        // _this.$router.push("/manager");
        _this.$message.success('删除成功');
        location.reload();
      })
    },

    handleClick(row) {
      const _this = this;
      if(row.state==='完成'||row.state==="结束"||row.state==="已取消"){
        _this.$message.error('订单已完成或取消');
        return;
      }
      axios.post('http://localhost:8282/Reservations/update', row).then(function (resp) {
        console.log(resp);
        if (resp.data === "success") {
          _this.$message.success('修改成功');
          // _this.$router.push("/manager")
          location.reload();
        }
      })
      console.log(row);
    },

    page(currentPage) {
      const _this = this;
      axios.get('http://localhost:8282/Reservations/findAll/' + currentPage + '/' + 10).then(function (resp) {
        _this.tableData = resp.data.content;
        _this.total = resp.data.totalElements;
      })
    }
  },

  created() {
    if(localStorage.getItem('Admin')==null){
      this.$router.replace({path: '/login'});
    }
    const _this = this;
    axios.get('http://localhost:8282/Reservations/findAll/1/' + 10).then(function (resp) {
      _this.tableData = resp.data.content;
      _this.total = resp.data.totalElements;
    })
  },

  data() {
    return {
      total: null,
      pagesize: 10,
      tableData: []
    }
  }
}
</script>

<style scoped>

</style>