<template>
  <div>
    <el-row>

      <el-autocomplete
          v-model="state"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入内容"
          @select="handleSelect"
      ></el-autocomplete>
      <!--      <el-button type="primary" @click="searchMap(input)" round>搜索</el-button>-->
    </el-row>

    <h1></h1>
    <div id="container"></div>
  </div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';

export default {
  name: "MapContainer",
  data() {
    return {
      input: null,
      map: null,
      MarkData: null,
      restaurants: [],
      state: " ",
      timeout: null,
      results: [],
    }
  },

  methods: {
    initMap: function () {
      const _this = this;
      axios.get('http://localhost:8282/Mapping/MarkPoint').then(function (resp) {
        _this.MarkData = resp.data;
        console.log(resp);
      })

      console.log(this.MarkData);

      function showInfoM(e) {
        console.log(e);
      }

      AMapLoader.load({
        key: "8cc633e74c7d90dacb7c3623ccebfa6c",             // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [''],       // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map("container", {  //设置地图容器id
          viewMode: "3D",    //是否为3D地图模式
          zoom: 12,           //初始化地图级别
          center: [114.3, 30.6], //初始化地图中心点位置
        });
        // 创建一个 Marker 实例：
        // console.assert(123);
        // 过500毫秒调用
        setTimeout(() => {
          // 方法区
          for (let i = 0, len = _this.MarkData.length; i < len; i++) {
            var marker = new AMap.Marker({
              position: new AMap.LngLat(_this.MarkData[i].x, _this.MarkData[i].y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
              title: "车位id：" + _this.MarkData[i].h_id + '\n' + "小区名称：" + _this.MarkData[i].小区,
              // hid: _this[i].h_id,
            });
            // 将创建的点标记添加到已有的地图实例：
            this.map.add(marker);
            marker.on('click', showInfoM);
          }
        }, 500);
      }).catch(e => {
        console.log(e);
      })
    },


    queryData(queryString) {
      const _this = this;
      let queryArr;
      axios.get('https://restapi.amap.com/v5/place/text?key=8e180142e0891119f6c6faa43c25e3e6&keywords=' + _this.state
          + '&region=420100&city_limit=true').then(function (resp) {
        console.log("input: ", _this.state);
        let temp = [];
        queryArr = resp.data.pois;
        if (queryArr == null) {
          return;
        }
        for (let i = 0; i < queryArr.length; i++) {
          temp.push({
            value: queryArr[i].name
          })
        }
        _this.results = temp;
      })
      // axios.get('https://restapi.amap.com/v3/assistant/inputtips?key=8e180142e0891119f6c6faa43c25e3e6&keywords=' + _this.state
      // ).then(function (resp) {
      //   console.log("input: ", _this.state);
      //   let temp = [];
      //   queryArr = resp.data.pois;
      //   if (queryArr == null) {
      //     return;
      //   }
      //   for (let i = 0; i < queryArr.length; i++) {
      //     temp.push({
      //       value: queryArr[i].name
      //     })
      //   }
      //   _this.results = temp;
      // })
    },


    async querySearchAsync(queryString, cb) {
      const _this = this;
      this.queryData(queryString)

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        if (_this.results != []) {
          console.log("call back: ", _this.results)
          cb(_this.results);
        }
      }, 600);
    },


    handleSelect(item) {
      axios.get('https://restapi.amap.com/v5/place/text?key=8e180142e0891119f6c6faa43c25e3e6' +
          '&region=420100&city_limit=true&keywords=' + item.value).then((response) => {
        const data = response.data;
        console.log("select: ", data);
        let location = data.pois[0].location;
        let locArr = location.split(',');
        console.log("location: ", locArr);
        this.map.setCenter([locArr[0], locArr[1]]);
        this.map.setZoom(15);
      })

      // axios.get('https://restapi.amap.com/v3/assistant/inputtips?key=8e180142e0891119f6c6faa43' +
      //     'c25e3e6&keywords=' + item.value).then((response) => {
      //   const data = response.data;
      //   console.log("select: ", data);
      //   let location = data.pois[0].location;
      //   let locArr = location.split(',');
      //   console.log("location: ", locArr);
      //   this.map.setCenter([locArr[0], locArr[1]]);
      //   this.map.setZoom(15);
      // })
    }
  },


  created() {
    this.initMap();
  },

  mounted() {
    //DOM初始化完成进行地图初始化
    this.initMap();
    // this.restaurants = this.loadAll();
  },

}
</script>

<style scoped>
#container {
  padding: 0px;
  margin: 0px;
  width: 95%;
  height: 550px;
}
</style>
