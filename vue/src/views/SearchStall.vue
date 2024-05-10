<template>
  <div>
    <el-row>
      <el-input style="width: 60%" v-model="inputName" placeholder="请输入小区名"></el-input>
      <el-button type="primary" @click="searchName(inputName, radioType, radioPrice,radioTime,radioGrade)" round>搜索
      </el-button>
    </el-row>
    <el-row>
      <span>车位状态：</span>
      <el-radio-group v-model="radioType">
        <el-radio :label="'使用中'">使用中</el-radio>
        <el-radio :label="'空闲中'">空闲中</el-radio>
        <el-radio :label="'已预约'">已预约</el-radio>
        <el-radio :label="'不限'">不限</el-radio>
      </el-radio-group>
    </el-row>

    <el-row>
      <span>车位租用价格/每小时：</span>
      <el-radio-group v-model="radioPrice">
        <el-radio :label="'0'+'~'+'5'">0-5</el-radio>
        <el-radio :label="'5'+'~'+'10'">5-15</el-radio>
        <el-radio :label="'15'+'~'+'99'">15以上</el-radio>
        <el-radio :label="'0'+'~'+'99'">不限</el-radio>
      </el-radio-group>
    </el-row>

    <el-row>
      <span>可用时间段：</span>
      <el-radio-group v-model="radioTime">
        <el-radio :label="'8:00-12:00'">8:00-12:00</el-radio>
        <el-radio :label="'8:00-19:00'">8:00-19:00</el-radio>
        <el-radio :label="'8:00-24:00'">8:00-24:00</el-radio>
        <el-radio :label="'10:00-12:00'">10:00-12:00</el-radio>
        <el-radio :label="'10:00-19:00'">10:00-19:00</el-radio>
        <el-radio :label="'10:00-24:00'">10:00-24:00</el-radio>
        <el-radio :label="'14:00-19:00'">14:00-19:00</el-radio>
        <el-radio :label="'14:00-24:00'">14:00-24:00</el-radio>
        <el-radio :label="'19:00-24:00'">19:00-24:00</el-radio>
        <el-radio :label="'24:00以后'">24:00以后</el-radio>        
        <el-radio :label="'全天'">全天</el-radio>
        <el-radio :label="'不限'">不限</el-radio>
      </el-radio-group>
    </el-row>

    <el-row>
      <span>评分：</span>
      <el-radio-group v-model="radioGrade">
          <el-radio :label="'4.5'+'~'+'5'">4.5-5.0</el-radio>
          <el-radio :label="'4'+'~'+'4.5'">4.0-4.5</el-radio>
          <el-radio :label="'3.5'+'~'+'4'">3.5-4.0</el-radio>
          <el-radio :label="'0'+'~'+'3.5'">3.5以下</el-radio>
          <el-radio :label="'不限'">不限</el-radio>
          <el-radio :label="null">暂无评分</el-radio>
      </el-radio-group>
    </el-row>


    <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="编号">
      </el-table-column>
      <el-table-column
          prop="小区"
          label="小区"
          width="180">
      </el-table-column>
      <el-table-column
          prop="当前状态"
          label="当前状态">
      </el-table-column>
      <el-table-column
          prop="租金"
          label="租金">
      </el-table-column>
      <el-table-column
          prop="可用时间段"
          label="可用时间段">
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
  </div>
</template>

<script>
export default {
  name: "SearchStall",
  methods: {
    detail(row) {
      this.$router.push({
        path: "/detail",
        query: {
          id: row.id
        }
      })
    },
    handleClick(row) {
      console.log(row);
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
        _this.$message.success('删除成功');
        axios.get('http://localhost:8282/Stall/findByNameType/' + inputName + '/' +
          stallstate + '/' + stallPrice + '/' + time + '/' +
          grade).then(function (resp){

          console.log(resp1);
           _this.$message('查询成功');
          // _this.inputName = null;
          _this.tableData = resp1.data;
        })
      })

      // location.reload();
    },
    searchName(inputName,stallstate,stallPrice,time,grade) {

      const _this = this;
      // alert('submit!');
      if (inputName === "") {
        inputName = null;
      }
      axios.get('http://localhost:8282/Stall/findByNameType/' + inputName + '/' +
          stallstate + '/' + stallPrice + '/' + time + '/' +grade).then(function (resp){
        console.log(resp);
        _this.$message.success('查询成功');
        // _this.inputName = null;
        _this.tableData = resp.data;
      })
    }
  },

  data() {
    return {
      inputName: '',
      tableData: [],
      radioPrice: '0~99',
      radioType: '不限',
      radioTime:'不限',
      radioGrade: '不限',
    }
  }
}
</script>

<style scoped>

</style>