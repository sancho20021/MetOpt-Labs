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
            <p>Iteration: <span id="demo"> {{ value }}</span></p>
            <p>Min Value: <span id="minvalue">{{ minValue }}</span></p>
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
            lastSelected: "Dichotomy",
            value: 0,
            func: (x) => (10 * x * Math.log(x) - x * x / 2),
            opt: [],
            arr: [],
            par: [],
            left: 0.1,
            right: 2.5,
            eps: 0.001,
            changed: 0,
            minValue: null,
            chartOptions: {
                title: 'Function graph',
                curveType: 'function',
                legend: {position: 'bottom'},
                width: 1200,
                height: 600,
            }
        };
    },
    methods: {
        calc_data: function (fun, l = this.left, r = this.right) {
            this.arr = [['X', 'F(X)', {role: 'style'}, '', {role: 'style'}]]
            for (let i = this.left; i <= this.right; i += this.eps) {
                if (i < l || r < i) {
                    this.arr.push([i, fun(i), 'red', null, 'red'])
                } else {
                    this.arr.push([i, fun(i), 'blue', null, 'blue']);
                }
            }
            this.arr[this.arr.length - 1][3] = this.arr[this.arr.length - 1][1];
        },
        change: function () {
            this.$root.$emit("onSelect");
        },
        slideChange: function () {
            this.$root.$emit("onSlideChange");
        }
    },
    beforeMount() {
        const selected = this.selected;
        this.calc_data(this.func, this.left, this.right);
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
            this.par = [];
            if (this.lastSelected === "Parabolic") {
                for (let i = 1; i < this.arr.length; i++) {
                    this.arr[i][3] = null;
                }
                this.arr[this.arr.length - 1][3] = this.arr[this.arr.length - 1][1];
            }
            this.lastSelected = this.selected;
            const selected = this.selected;
            axios.get("/api/1/optimize", {
                params: {
                    selected
                }
            }).then(response => {
                this.opt = response.data;
            })
            if (selected === "Parabolic") {
                axios.get("api/1/parabola").then(response => {
                    let a = response.data;
                    for (let i = 0; i < a.length; i++) {
                        this.par.push(eval(a[i]));
                    }
                })
            }
            this.value = 0;
            console.log(selected);
        });
        this.$root.$on("onSlideChange", () => {
            const value = parseInt(this.value);
            // console.log(this.par[value]);
            this.minValue = this.opt[value][2];
            for (let i = 1; i < this.arr.length; i++) {
                if (this.arr[i][0] < this.opt[value][0] || this.opt[value][1] < this.arr[i][0]) {
                    this.arr[i][2] = 'red';
                    if (this.selected === "Parabolic") {
                        this.arr[i][3] = null;
                    }
                } else {
                    this.arr[i][2] = 'blue';
                    if (this.selected === "Parabolic") {
                        this.arr[i][3] = this.par[value](this.arr[i][0]);
                        this.arr[i][4] = 'pink'
                    }
                }
                if (Math.abs(this.arr[i][0] - this.minValue) < this.eps) {
                    this.arr[i][2] = 'point { size: 5; shape-type: circle; fill-color: green; visible: true }';
                }
            }
            this.changed++;
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
