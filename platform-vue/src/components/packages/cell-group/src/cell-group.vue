<template>
    <div class="cell-group">
        <div class="cell-group-title">
            <div class="title-left">
                <span>{{title}}</span>
            </div>
            <div class="title-right">
                <slot name="icon"></slot>
            </div>
        </div>
        <div class="cell-group-view">
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "cell-group",
        props: {
            title: {
                type: String
            }
        }
    };
</script>

<style lang="less" scoped>
    @import '../../../../assets/css/var.less';

    .cell-group {
        .cell-group-title {
            display: flex;
            justify-content: space-between;
            padding: 5px 10px;
            font-size: 14px;
            color: gray;
            .title-left {
                flex-grow: 0;
            }
            .title-right {
                flex-grow: 0;
            }
        }
        .cell-group-view {
            position: relative;
            background: white;
            padding-left: 10px;
            .m-address-cell {
                &:not(:first-child) {
                    margin-top: 10px;
                }
            }
            &:before {
                position: absolute;
                top: -1px;
                left: 0px;
                width: 100%;
                content: '';
                height: 0px;
                border-top: 1px solid @border-color;
                transform: scaleY(0.5);
            }
            &:after {
                position: absolute;
                bottom: -1px;
                left: 0px;
                width: 100%;
                content: '';
                height: 0px;
                border-top: 1px solid @border-color;
                transform: scaleY(0.5);
            }
        }
    }
</style>