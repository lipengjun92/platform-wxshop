<template>
    <div class="selector" :class="{'active':value}" @click="emitValue">
        <div class="selector-wrap">
            <i class="iconfont" v-html="innerText"></i>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'selector',
        data() {
            return {
                value: this.select
            }
        },
        props: {
            select: {
                type: Boolean,
                default: false
            }
        },
        computed: {
            innerText() {
                return this.value ? '&#xe620;' : '&#xe6d1;'
            }
        },
        methods: {
            emitValue() {
                this.value = !this.value;
                this.$emit('handler', this.value)
            }
        },
        watch: {
            select: function (val) {
                this.value = val;
            }
        }
    }
</script>

<style lang="less" scoped>
    .selector {
        display: inline-block;
        .selector-wrap {
            display: flex;
            width: 25px;
            height: 25px;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            i {
                font-size: 20px;
            }
        }
    }

    .active {
        i {
            color: #04BE02;
        }
    }
</style>


