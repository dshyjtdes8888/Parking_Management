<template>

  <div>
    <el-descriptions title="车位信息">
      <el-descriptions-item label="id">{{ this.ruleForm.id }}</el-descriptions-item>
      <el-descriptions-item label="小区">{{ this.ruleForm.小区 }}</el-descriptions-item>

      <el-descriptions-item label="当前状态">
        <el-tag size="small">{{ this.ruleForm.当前状态 }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="租金/小时">{{ this.ruleForm.租金 }}</el-descriptions-item>
      <el-descriptions-item label="可用时间段">{{ this.ruleForm.可用时间段 }}</el-descriptions-item>
      <el-descriptions-item label="浏览次数">{{ this.looktimes }}</el-descriptions-item>
      <el-descriptions-item label="预约次数">{{ this.resertimes }}</el-descriptions-item>
    </el-descriptions>

    <el-button @click="reser(ruleForm)" type="primary" round>预约车位</el-button>

 
      <!-- 跑马灯  -->
    <!-- 动态加载url -->
<!-- imageurl是一个数组 -->
    <div style="padding-top:50px;padding-left:450px;">
	     <img style="width:500px;height:800px" :src=this.ruleForm.图片 />
    </div>

    <el-tabs v-model="activename" @tab-click="handleClick">
      <el-tab-pane label="厕所" name="厕所"></el-tab-pane>
      <el-tab-pane label="医疗" name="医疗"></el-tab-pane>
      <el-tab-pane label="交通" name="交通"></el-tab-pane>
      <el-tab-pane label="餐饮" name="餐饮"></el-tab-pane>
      <el-tab-pane label="地名" name="地名"></el-tab-pane>
      <el-tab-pane label="生活" name="生活"></el-tab-pane>
      <el-tab-pane label="购物" name="购物"></el-tab-pane>
      <el-tab-pane label="风景" name="风景"></el-tab-pane>
    </el-tabs>

    <div id="container"></div>
    <div class="spacer"></div>
    <el-table
        :data="tableData1"
        style="width: 100%">
      <el-table-column
          prop="comment"
          label="评论"
          width="1000%">
      </el-table-column>
      <el-table-column
          prop="date"
          label="日期"
          width="100%">
      </el-table-column>
    </el-table>

    <h1>相似车位推荐(2公里以内的相似车位)</h1>
    <el-table
        :data="suggesttableData"
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
          <el-button @click="detail(scope.row)" type="text" size="small">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>


</template>

<script>
import AMapLoader from "@amap/amap-jsapi-loader";

export default {


  methods: {
    detail1(row) {
      // console.log("123",row)
      alert(row.id)
      this.$router.replace({
        path: "/detail",
        query: {
          id: row.id
        }
      })
      location.reload();
    },

    reser(theStall) {
      if(this.ruleForm.当前状态=='已预约'||this.ruleForm.当前状态=='使用中'){
        this.$message.error('该车位已被预约');
        return;
      }
      this.$router.push({
        path: "/reser",
        query: {
          Stall: theStall
        }
      })
    },

    rowStyle({
               row,
               rowIndex,
             }) {
      if (rowIndex == 0) {
        return "background:#f3f6fc;";
      } else {
        return "background:#ffffff;";
      }
    },

    handleClick(tab, event) {
      // console.log(tab, event);
      console.log(tab.label);
      this.initMap(tab.label);
    },

    initMap(taglabel) {
      const _this = this;
      axios.get('http://localhost:8282/POI/KNN/' + this.$route.query.id + '/' + taglabel).then(function (resp) {
        _this.MarkData = resp.data;
        console.log(_this.MarkData);
      })
      axios.get("http://localhost:8282/Mapping/CurrentMapping/" + this.$route.query.id).then(function (resp) {
        _this.CurrentStall = resp.data;
      })
       console.log(this.MarkData);
      
       function showInfoM(e) {
         console.log(e);
       }

      // 过8000毫秒调用
      setTimeout(() => {
        // 方法区
        AMapLoader.load({
          key: "3f8407bf6778dda2a0cd78f8453fda08",             // 申请好的Web端开发者Key，首次调用 load 时必填
          version: "2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
          plugins: [''],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
        }).then((AMap) => {
            this.map = new AMap.Map("container", {  //设置地图容器id
            viewMode: "3D",    //是否为3D地图模式
            zoom: 16,           //初始化地图级别
            center: [_this.CurrentStall.x, _this.CurrentStall.y], //初始化地图中心点位置
          });
        }, 8000);

        // 创建一个 Marker 实例：
        // 过8000毫秒调用
        setTimeout(() => {
          // 方法区
          for (let i = 0, len = _this.MarkData.length; i < len; i++) {
            var marker = new AMap.Marker({
              position: new AMap.LngLat(this.MarkData[i].x, this.MarkData[i].y),
              title: "POIid：" + _this.MarkData[i].id + '\n' + "POI名称：" + _this.MarkData[i].name+'\n'
              +"POI具体位置：" + _this.MarkData[i].text位置 + '\n' + "联系电话：" + _this.MarkData[i].电话,
            });
            // 将创建的点标记添加到已有的地图实例：
            this.map.add(marker);
          }
          var marker = new AMap.Marker({
            position: new AMap.LngLat(_this.CurrentStall.x, _this.CurrentStall.y),   // 经纬度对象
            title: "车位id：" + _this.CurrentStall.sid + '\n' + "小区名称：" + _this.CurrentStall.小区,
            icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
          });
          // 将创建的点标记添加到已有的地图实例：
          this.map.add(marker);
        }, 8000);

      })
    },
  },

  created() {
    const _this = this;
    axios.get("http://localhost:8282/Mapping/NearStall/" + this.$route.query.id).then(function (resp) {
      _this.suggesttableData = resp.data;
    })
    axios.get("http://localhost:8282/Stall/findById/" + this.$route.query.id).then(function (resp) {
      _this.ruleForm = resp.data;
    })
    axios.get("http://localhost:8282/Mapping/CurrentMapping/" + this.$route.query.id).then(function (resp) {
      _this.CurrentStall = resp.data;
    })
    axios.get("http://localhost:8282/LookRecords/findTimes/" + this.$route.query.id).then(function (resp) {
      _this.looktimes = Math.floor(parseInt(resp.data.recordtimes)/2);
      _this.resertimes=resp.data.resertimes;
    })

    // 过500毫秒调用
    setTimeout(() => {
      // 方法区
      var marker = new AMap.Marker({
        position: new AMap.LngLat(_this.CurrentStall.x, _this.CurrentStall.y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        title: "车位id：" + _this.CurrentStall.sid + '\n' + "小区名称：" + _this.CurrentStall.name,
      });
      // 将创建的点标记添加到已有的地图实例：
      _this.map.add(marker);
      _this.comment.id = this.ruleForm.id;
      //alert(_this.ruleForm.id);
    }, 500);

    _this.initMap(this.activename);
  },

  data() {
    return {
      looktimes: null,
      resertimes: null,
      suggesttableData: null,
      activename: "厕所",
      name: null,
      map: null,
      MarkData: [],
      CurrentStall: null,
      comment: {
        textarea: '',
        commentid: ''
      },
      tableData1: [],

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
      sid: '',

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
  },

  mounted() {

    // 过1000毫秒调用
    const _this = this;
    setTimeout(() => {
      // 方法区
      axios.get('http://localhost:8282/Comments/findComment/' + _this.ruleForm.id).then(function (resp) {
        _this.tableData1 = resp.data;
      })
      axios.get("http://localhost:8282/Stall/findById/" + this.$route.query.id).then(function (resp) {
        _this.ruleForm = resp.data;
      })
      axios.get("http://localhost:8282/Mapping/CurrentMapping/" + this.$route.query.id).then(function (resp) {
        _this.CurrentStall = resp.data;
      })
    }, 1000);


    // 过500毫秒调用
    setTimeout(() => {
      //DOM初始化完成进行地图初始化
      this.initMap(this.activename);
      // 方法区
      var marker = new AMap.Marker({
        position: new AMap.LngLat(_this.CurrentStall.x, _this.CurrentStall.y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        title: "车位id：" + _this.CurrentStall.sid + '\n' + "小区名称：" + _this.CurrentStall.name,
        // hid: _this[i].h_id,
      });
      // 将创建的点标记添加到已有的地图实例：
      this.map.add(marker);
      this.comment.id = this.ruleForm.id;
      // alert(_this.ruleForm.id);
    }, 500);

  }
}
</script>

<style scoped>
#container {
  padding: 0px;
  margin: 0px;
  width: 80%;
  height: 600px;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.index {
  background: #c8cfd8;
  width: 80%;
  text-align: center;
  margin-left: 142px;
  width: 80.6%;

}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

.spacer {
  height: 20px; 
}
</style>