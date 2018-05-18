<template>
    <div class="order">
        <div class="order-tab">
            <tab bar-active-color="#04BE02" active-color="#04BE02">
                <tab-item :selected="activeBar() === 0">全部</tab-item>
                <tab-item :selected="activeBar() === 1">待付款</tab-item>
                <tab-item :selected="activeBar() === 2">待收货</tab-item>
                <tab-item :selected="activeBar() === 3">已完成</tab-item>
                <tab-item :selected="activeBar() === 4">已取消</tab-item>
            </tab>
        </div>
        <div class="order-view"></div>
    </div>
</template>

<script>
    import {Tab, TabItem} from 'vux'

    export default {
        data() {
            return {
                activeTabIndex: 1
            }
        },
        components: {
            Tab,
            TabItem
        },
        methods: {
            activeBar() {
                return parseInt(this.$route.query.status);
            }
        }
    }
</script>

<style lang="less" scoped>
    .order {
        position: absolute;
        top: 46px;
        bottom: 0px;
        left: 0px;
        right: 0px;
        width: 100%;
        display: flex;
        flex-direction: column;
        .order-tab {
            flex-grow: 0;
        }
        .order-view {
            flex: 1;
        }
    }
</style>

