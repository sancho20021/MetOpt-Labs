<template>
    <div id="app">
        <select v-model="selected" name="funcs" id="funcselect" @change="change()">
            <option selected value="Dichotomy">дихотомии</option>
            <option value="Golden">золтого сечения</option>
            <option value="Fibonacci">фиббоначи</option>
            <option value="Parabolic">параболл</option>
            <option value="Combination">Брента</option>
        </select>
        <GChart
            type="LineChart"
            :data="arr"
            :options="chartOptions"
            :key="changed"
        />
        <div class="slidecontainer">
            <input v-model="value" type="range" min="0" :max="opt.length - 1" value="0" class="slider" id="myRange">
            <br>
            <button @click="slideChange()">Отобразить</button>
            <p>Value: <span id="demo"> {{ value }}</span></p>
        </div>
    </div>
</template>

<script>
import axios from "axios"
// import Middle from "./components/Middle";
import {GChart} from "vue-google-charts";


export default {
    name: "App",
    components: {
        GChart
    },
    data() {
        return {
            // Array will be automatically processed with visualization.arrayToDataTable function
            selected: "Dichotomy",
            value: 0,
            func: (x) => (10 * x * Math.log(x) - x * x / 2),
            opt: [],
            arr: [],
            left: 0.1,
            right: 2.5,
            eps: 0.001,
            changed: 0,
            chartOptions: {
                chart: {
                    title: 'Function graph',
                    curveType: 'function',
                    legend: {position: 'bottom'},
                }
            }
        };
    },
    methods: {
        calc_data: function (fun, l = this.left, r = this.right) {
            this.arr = [['X', 'F(X)', {role: 'style'}]]
            for (let i = this.left; i <= this.right; i += this.eps) {
                if (i < l || r < i) {
                    this.arr.push([i, fun(i), 'red'])
                } else {
                    this.arr.push([i, fun(i), 'blue']);
                }
            }
        },
        change: function () {
            this.$root.$emit("onSelect");
        },
        slideChange: function () {
            this.$root.$emit("onSlideChange");
        },
        paint: function (l = this.left, r = this.right) {
            for (let i = 1; i < this.arr.length; i++) {
                this.arr[i][2] = this.arr[i][0] < l || r < this.arr[i][0] ? 'red' : 'blue';
            }
        }
    },
    beforeMount() {
        this.calc_data(this.func, this.left, this.right);
        const selected = this.selected;
        axios.get("/api/1/optimize", {
            params: {
                selected
            }
        }).then(response => {
            this.opt = response.data;
        });
    },
    beforeCreate() {
        this.$root.$on("onSelect", () => {
            const selected = this.selected;
            axios.get("/api/1/optimize", {
                params: {
                    selected
                }
            }).then(response => {
                this.opt = response.data
            })
            console.log(selected);
        });
        this.$root.$on("onSlideChange", () => {
            const value = parseInt(this.value);
            for (let i = 1; i < this.arr.length; i++) {
                this.arr[i][2] = this.arr[i][0] < this.opt[value][0] || this.opt[value][1] < this.arr[i][0] ? 'red' : 'blue';
            }
            this.changed++;
            console.log("change");
            // this.$root.$emit("onChangeData", this.chartData);
        });
    }
}
;
</script>

<style>
#app {

}
</style>
