<template>
    <div class="cell">
        <div class="cell-left">
            <span class="title" v-if="title">{{title}}</span>
            <slot></slot>
        </div>
        <div class="cell-right">
            <i class="iconfont" v-if="isLink">&#xe639;</i>
            <span :style="valueStyle">{{value}}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'cell',
        props: {
            title: {
                type: String
            },
            value: {
                type: String
            },
            valueStyle:{
                type: String
            },
            isLink: Boolean
        }
    }
</script>

<style lang='less' scoped>
    @import '../../../../assets/css/var.less';

    .cell {
        position: relative;
        display: flex;
        background: #fff;
        .cell-left {
            flex-basis: 45%;
            .title {
                height: 48px;
                line-height: 48px;
                font-size: 16px;
            }
        }
        .cell-right {
            display: flex;
            flex: 1;
            flex-direction: row-reverse;
            align-items: center;
            padding-right: 10px;
            color: gray;
            span {
                flex-grow: 0;
                font-size: 14px;
            }
            i {
                flex-grow: 0;
                font-size: 16px;
            }
        }
        &:not(:first-child):before {
            position: absolute;
            content: '';
            top: 0px;
            left: 0px;
            width: 100%;
            height: 0px;
            border-top: 1px solid @border-color;
            transform: scaleY(0.5);
        }
    }
</style>