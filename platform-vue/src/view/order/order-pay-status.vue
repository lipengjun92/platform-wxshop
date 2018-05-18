<template>
    <div class="order-pay-status">
        <div class="status-spread"></div>
        <div class="status-icon">
            <i class="iconfont">&#xe620;</i>
            <p>订单支付成功</p>
            <span>{{coolTime}}S后自动返回首页</span>
        </div>
        <div class="status-nav">
            <div class="nav-order">
                <i class="iconfont">&#xe671;</i>
                <span>查看订单</span>
            </div>
            <div class="nav-home">
                <i class="iconfont">&#xe601;</i>
                <span>返回首页</span>
            </div>
        </div>
        <div class="status-info">
            <div class="info-item">
                <span>订单号：</span>
                <p>EZM201803059346SD</p>
            </div>
            <div class="info-item">
                <span>金额：</span>
                <p>￥2365.00</p>
            </div>
            <div class="info-item">
                <span>支付时间：</span>
                <p>2018-03-05 15：30</p>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                coolTime: 7
            }
        },
        methods: {
            routerJump(val) {
                const self = this;
                const timer = setInterval(() => {
                    if (self.coolTime === 0) {
                        clearInterval(timer);
                        self.goto(val)
                    } else {
                        self.coolTime = self.coolTime - 1;
                    }
                }, 1000)
            }
        },
        created() {
            this.routerJump('/index')
        }
    }
</script>

<style lang="less" scoped>
    @import '../../assets/css/var.less';

    .order-pay-status {
        position: absolute;
        top: 46px;
        width: 100%;
        bottom: 0px;
        background: white;
        .status-spread {
            position: relative;
            height: 130px;
            background: url(https://m.360buyimg.com/mobilecms/jfs/t15121/63/2237425096/226392/6edfe5f0/5a75436dN3b8b592d.gif) no-repeat center;
            background-size: 100% 100%;
        }
        .status-icon {
            margin: 10px 0px 20px 0px;
            text-align: center;
            i {
                font-size: 50px;
                color: #04BE02;
            }
            span {
                font-size: 14px;
                color: gray;
            }
        }
        .status-nav {
            display: flex;
            height: 60px;
            align-items: center;
            justify-content: space-between;
            padding:0px 10px;
            .nav-order {
                display: flex;
                align-items: center;
                flex-grow: 0;
                padding: 0px 10px;
                border-radius: 5px;
                background: #04BE02;
                color: white;
                i {
                    font-size: 20px;
                }
                span {
                    margin-left: 10px;
                    font-size: 16px;
                }
            }
            .nav-home {
                flex-grow: 0;
                align-items: center;
                display: flex;
                box-sizing: border-box;
                padding: 0px 10px;
                border: 1px solid #04BE02;
                border-radius: 5px;
                color: #04BE02;
                i {
                    font-size: 20px;
                }
                span {
                    margin-left: 10px;
                    font-size: 16px;
                }
            }
        }
        .status-info {
            padding: 0px 10px;
            margin-top: 20px;
            .info-item {
                display: flex;
                align-items: center;
                height: 25px;
                font-size: 16px;
                color: gray;
                span {
                    flex-basis: 90px;
                }
                p {
                    flex: 1;
                }
            }
        }
    }
</style>