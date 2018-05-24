export const getters = {
  swiperList: state => {
    return state.banner.map(value => {
      return {
        img: value.image_url
      }
    })
  },
  hotGoodsList: state => {
    return state.hotGoodsList.map(value => {
      return {
        imageUrl:value.primary_pic_url,
        title: value.name,
        desc: value.goods_brief,
        price: value.retail_price
      }
    })
  }
}
