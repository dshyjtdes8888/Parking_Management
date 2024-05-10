<template>
  <div>
    <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="编号">
      </el-table-column>
      <el-table-column
          prop="小区"
          label="所在小区"
          width="180">
      </el-table-column>
      <el-table-column
          prop="租金"
          label="租金">
      </el-table-column>
      <el-table-column
          prop="当前状态"
          label="当前状态">
      </el-table-column>
      <el-table-column
          prop="可用时间段"
          label="可用时间段">
      </el-table-column>
      <el-table-column
          prop="具体位置"
          label="具体位置">
      </el-table-column>
      <el-table-column
          prop="联系电话"
          label="联系电话">
      </el-table-column>
      <el-table-column
          prop="评分"
          label="评分">
      </el-table-column>

      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <el-button @click="detail(scope.row)" type="text" size="small">详情</el-button>
          <el-button @click="edit(scope.row)" type="text" size="small">修改</el-button>
          <el-button @click="deletestall(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>

    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="10"
        :total="total"
        @current-change="page">
    </el-pagination>
  </div>
</template>

<script>
export default {

  name: "Manager",
  methods: {
    detail(row){
      this.$router.push({
        path: "/detail",
        query: {
          id: row.id
        }
      })
    },
    edit(row) {
      this.$router.push({
        path: "/updatestall",
        query: {
          id: row.id
        }
      })
    },
    deletestall(row) {
      const _this = this;
      axios.delete("http://localhost:8282/Stall/delete/" + row.id).then(function (resp) {
        _this.$router.push("/manager");
        _this.$message.success('删除成功');
      })
      location.reload();
    },

    page(currentPage) {
      const _this = this;
      axios.get('http://localhost:8282/Stall/findAll/' + currentPage + '/' + 10).then(function (resp) {
        _this.tableData = resp.data.content;
        _this.total = resp.data.totalElements;
      })
    }
  },

  data() {
    return {
      total: null,
      tableData: null,
      pagesize: {
        type: Number,
        default: 10 
      },
      inputName: ''
    }
  },

  created() {
    if(localStorage.getItem('Admin')==null){
      this.$router.replace({path: '/login'});
    }
    const _this = this;
    axios.get('http://localhost:8282/Stall/findAll/1/' + 10).then(function (resp) {
      _this.tableData = resp.data.content;
      _this.total = resp.data.totalElements;
    })
  }
}
</script>

<style scoped>

</style>