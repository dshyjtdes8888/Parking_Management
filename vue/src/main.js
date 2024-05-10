import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

/**
 * axios拦截器
 */
// axios.interceptors.request.use(function (config) {
//   console.log('请求参数：', config);
//   return config;
// }, error => {
//   return Promise.reject(error);
// });
// axios.interceptors.response.use(function (response) {
//   console.log('返回结果：', response);
//   return response;
// }, error => {
//   console.log('返回错误：', error);
//   const response = error.response;
//   return Promise.reject(error);
// });