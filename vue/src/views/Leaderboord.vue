<template>
    <div>
    <el-tabs v-model="type" @tab-click="handleClick">
      <el-tab-pane label="预约榜" name="预约榜" style="margin-right: 10px;"></el-tab-pane>
      <el-tab-pane label="浏览榜" name="浏览榜" style="margin-right: 10px;"></el-tab-pane>
      <el-tab-pane label="好评榜" name="好评榜" style="margin-right: 10px;"></el-tab-pane>
      <el-tab-pane label="价格榜" name="价格榜" style="margin-right: 10px;"></el-tab-pane>
    </el-tabs>

    <div>
    <el-table
        :data="tabledata"
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
          width="100">
        <template slot-scope="scope">
          <el-button @click="detail5(scope.row)" type="text" size="small">详情</el-button>
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
</div>
</template>
  
<script>
export default{
    methods: {
    detail5(row){
      this.$router.push({
        path: "/detail",
        query: {
          id: row.id
        }
      })
    },

    handleClick(tab, event) {
        this.list(tab.label);
    },
    
    page(currentPage) {
        const _this=this;
        if(_this.type==="好评榜"){
            axios.get('http://localhost:8282/Stall/gradelist/' + currentPage +'/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(_this.type==="预约榜"){
            axios.get('http://localhost:8282/Stall/reserlist/' + currentPage + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(_this.type==="浏览榜"){
            axios.get('http://localhost:8282/Stall/recordlist/' + currentPage + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(_this.type==="价格榜"){
            axios.get('http://localhost:8282/Stall/pricelist/' + currentPage + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
    },

    list(type){
        const _this=this;
        if(type==="好评榜"){
            axios.get('http://localhost:8282/Stall/gradelist/1' + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(type==="预约榜"){
            axios.get('http://localhost:8282/Stall/reserlist/1' + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(type==="浏览榜"){
            axios.get('http://localhost:8282/Stall/recordlist/1' + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
        if(type==="价格榜"){
            axios.get('http://localhost:8282/Stall/pricelist/1' + '/' + 10).then(function (resp) {
                _this.tabledata = resp.data.content;
                _this.total = resp.data.totalElements;
            })
        }
    }


  },

  data() {
    return {
      total: null,
      tabledata: null,
      pageSize: 10,
      type: "预约榜",
    }
  },

  created() {
    if(localStorage.getItem('Admin')==null){
      this.$router.replace({path: '/login'});
    }
    const _this = this;
    _this.list(_this.type);
  }
}

</script>