<template>
    <view>
        <view class="cu-bar tabbar bg-white shadow foot">
            <view class="action" @click="NavChange" data-cur="basics">
                <view class='cuIcon-cu-image'>
                    <image :src="'/static/tabbar/basics' + [PageCur=='basics'?'_cur':''] + '.png'"></image>
                </view>
                <view :class="PageCur=='basics'?'text-green':'text-gray'">首页</view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                PageCur: 'basics'
            }
        }
    }
</script>

<style>

</style>
