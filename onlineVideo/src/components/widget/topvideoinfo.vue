<template>
  <div>
    <table style="height: 20px;">
      <tr>
        <td><img :src="vinfo.icon" style="width: 100px;height: 60px;" /></td>
        <td>
          <h4>{{vinfo.title}}</h4>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
  export default {
    data() {
      return {

      }
    },
    props: ['vinfo']
  }
</script>

<style>
</style>
