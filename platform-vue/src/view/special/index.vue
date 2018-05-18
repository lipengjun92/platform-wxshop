<template>
  <div class="special">
    <special-cell
    :data="item"
    :key="key"
    v-for="(item,key) in list"
    ></special-cell>
  </div>
</template>

<script>
export default {
  data(){
    return{
      list:[
        {
          imageUrl:require('../../assets/images/special_default_goods.jpg'),
          title:'玩具不应该只是小孩的专属品',
          desc:'这些成人玩具，虽然无聊但真的叫人上瘾',
          price:'237.00'
        }
      ]
    }
  }
}
</script>

