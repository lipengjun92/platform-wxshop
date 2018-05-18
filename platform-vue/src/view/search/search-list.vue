<template>
    <div class="search-list">
        <div class="search-list-header">
            <div class="header-left" @click="back">
                <i class="iconfont">&#xe637;</i>
            </div>
            <div class="header-mid">
                <div class="header-mid-search">
                    <div class="search-indicator">
                        <div class="indicator-item">
                            <span>食用油</span>
                            <i class="iconfont">&#xe8b6;</i>
                        </div>
                        <div class="indicator-item">
                            <span>橄榄油</span>
                            <i class="iconfont">&#xe8b6;</i>
                        </div>
                    </div>
                    <i class="iconfont">&#xe626;</i>
                </div>
            </div>
        </div>
        <div class="search-list-filter">
            <div class="filter-sort">
                <ul>
                    <li>综合</li>
                    <li>销量</li>
                    <li>价格</li>
                </ul>
                <div class="sort-icon">
                    <span>排序</span>
                    <i class="iconfont">&#xe66e;</i>
                </div>
            </div>
            <div class="filter-condition">
                <ul>
                    <li>
                        <div class="condition-item">
                            <span>物流</span>
                            <i class="iconfont">&#xe74d;</i>
                        </div>
                    </li>
                    <li>
                        <div class="condition-item">
                            <span>品牌</span>
                            <i class="iconfont">&#xe74d;</i>
                        </div>
                    </li>
                    <li>
                        <div class="condition-item">
                            <span>芯片</span>
                            <i class="iconfont">&#xe74d;</i>
                        </div>
                    </li>
                    <li>
                        <div class="condition-item">
                            <span>容量</span>
                            <i class="iconfont">&#xe74d;</i>
                        </div>
                    </li>
                </ul>
                <div class="condition-icon">
                    <span>筛选</span>
                    <i class="iconfont">&#xe612;</i>
                </div>
            </div>
        </div>
        <div class="search-list-view">
            <goods-cell :key="key" v-for="(item, key) in 2"/>
        </div>
        <div class="search-footer-menu">
            <div class="menu-item" @click="goto('/cart')">
                <i class="iconfont">&#xe600;</i>
                <span>2</span>
            </div>
            <div class="menu-item">
                <i class="iconfont">&#xe61d;</i>
                <span>5</span>
            </div>
            <div class="menu-item">
                <i class="iconfont">&#xe64a;</i>
            </div>
        </div>
    </div>
</template>

<style lang="less" scoped>
    @import "../../assets/css/var";

    .search-list {
        position: absolute;
        top: 0;
        bottom: 0;
        width: 100%;
        display: flex;
        flex-direction: column;
        .search-list-header {
            position: relative;
            flex-basis: 52px;
            display: flex;
            box-sizing: border-box;
            padding: 0 10px;
            background: white;
            .header-left {
                position: absolute;
                top: 50%;
                left: 10px;
                height: 100%;
                transform: translateY(-50%);
                display: flex;
                align-items: center;
                i {
                    font-size: 20px;
                }
            }
            .header-mid {
                flex: 1;
                display: flex;
                align-items: center;
                justify-content: center;
                .header-mid-search {
                    position: relative;
                    display: flex;
                    justify-content: flex-end;
                    align-items: center;
                    flex-basis: 70%;
                    height: 34px;
                    border-radius: 34px;
                    background: @background-color;
                    .search-indicator {
                        position: absolute;
                        top: 0;
                        left: 10px;
                        height: 100%;
                        display: flex;
                        align-items: center;
                        .indicator-item {
                            display: flex;
                            flex-grow: 0;
                            height: 24px;
                            padding: 0 10px;
                            border-radius: 5px;
                            background: @sub-color;
                            span {
                                line-height: 24px;
                                font-size: 12px;
                                color: white;
                            }
                            i {
                                margin: 0 0 0 5px;
                                line-height: 24px;
                                font-size: 12px;
                                color: white;
                            }
                            &:not(:first-child) {
                                margin-left: 5px;
                            }
                        }
                    }
                    i {
                        margin-right: 10px;
                        font-size: 20px;
                        color: gray;
                    }
                }
            }
            &::after {
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 0;
                content: '';
                border-bottom: 1px solid @divider-color;
                transform: scaleY(0.5);
            }
        }
        .search-list-filter {
            position: relative;
            flex-grow: 0;
            padding-left: 10px;
            background: white;
            .filter-sort {
                position: relative;
                height: 45px;
                display: flex;
                align-items: center;
                ul {
                    flex: 1;
                    display: flex;
                    list-style: none;
                    font-size: 16px;
                    li {
                        flex: 1;
                        text-align: center;
                    }
                }
                .sort-icon {
                    display: flex;
                    flex-basis: 80px;
                    align-items: center;
                    justify-content: center;
                    span {
                        margin-right: 5px;
                        font-size: 14px;
                        color: gray;
                    }
                    i {
                        font-size: 20px;
                        color: gray;
                    }
                }
                &::after {
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    width: 100%;
                    height: 0;
                    content: '';
                    border-bottom: 1px solid @divider-color;
                    transform: scaleY(0.5);
                }
            }
            .filter-condition {
                position: relative;
                height: 45px;
                display: flex;
                align-items: center;
                ul {
                    flex: 1;
                    display: flex;
                    list-style: none;
                    font-size: 14px;
                    li {
                        flex: 1;
                        display: flex;
                        justify-content: center;
                        .condition-item {
                            display: flex;
                            padding: 0 10px;
                            border-radius: 5px;
                            background: @background-color;
                            i {
                                line-height: 25px;
                            }
                        }
                    }
                }
                .condition-icon {
                    display: flex;
                    flex-basis: 80px;
                    align-items: center;
                    justify-content: center;
                    span {
                        margin-right: 5px;
                        font-size: 14px;
                        color: gray;
                    }
                    i {
                        font-size: 25px;
                        color: gray;
                    }
                }
            }
            &::after {
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 0;
                content: '';
                border-bottom: 1px solid @divider-color;
                transform: scaleY(0.5);
            }
        }
        .search-list-view {
            flex: 1;
            overflow-x: hidden;
            overflow-y: auto;
            background: white;
        }
        .search-footer-menu {
            position: absolute;
            bottom: 30px;
            right: 10px;
            display: flex;
            flex-direction: column;
            .menu-item {
                position: relative;
                display: flex;
                flex-basis: 35px;
                width: 35px;
                align-items: center;
                justify-content: center;
                border-radius: 35px;
                border: 1px solid @border-color;
                i {
                    font-size: 25px;
                    color: @border-color;
                }
                span{
                    position: absolute;
                    top:-5px;
                    right:-5px;
                    width:20px;
                    height: 20px;
                    text-align: center;
                    line-height: 20px;
                    font-size: 12px;
                    border-radius: 20px;
                    background: #09BB07;
                    color:white;
                    transform: scale(.8);
                }
                &:not(:first-child){
                    margin-top: 15px;
                }
            }
        }
    }
</style>