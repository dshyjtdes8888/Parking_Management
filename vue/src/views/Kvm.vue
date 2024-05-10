<template>
    <div id="app">
      <h1>Virtual Machine Management</h1>
  
      <h2>Virtual Machines:</h2>
      <ul>
        <!-- 使用 v-for 循环遍历虚拟机列表，并显示每个虚拟机的信息 -->
        <li v-for="vm in vms" :key="vm.name">
          {{ vm.name }} - {{ vm.status }}
          <el-button @click="controlVM(vm.name, 'stop')">Stop</el-button>
          <el-button @click="controlVM(vm.name, 'start')">Start</el-button>
          <el-button @click="controlVM(vm.name, 'resume')">Resume</el-button>
          <el-button @click="controlVM(vm.name, 'shutdown')">Shutdown</el-button>
          <el-button @click="controlVM(vm.name, 'delete')">Delete</el-button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        vms: [],
      };
    },
    mounted() {
      this.fetchVMs();
    },
    methods: {
      fetchVMs() {
        // 从后端获取虚拟机列表
        fetch('http://192.168.43.11:8002/api/vms/')
          .then(response => response.json())
          .then(data => (this.vms = data))
          .catch(error => console.error('Error fetching virtual machines:', error));
      },
      controlVM(vmName, action) {
        // 执行虚拟机操作（停止、启动、恢复、关机、删除）
        fetch(`http://192.168.43.11:8002/api/vms/${vmName}/${action}/`, {
          method: 'POST',
        })
          .then(response => response.json())
          .then(data => {
            // 处理成功的情况
            console.log('VM action successful:', data);
            this.fetchVMs();  // 操作完成后刷新虚拟机列表
          })
          .catch(error => console.error('Error performing VM action:', error));
      },
    },
  };
  </script>
  
  <style>
  /* Your styles go here */
  </style>
  