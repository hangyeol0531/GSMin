import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  data () {
    return {
      options: {
        scales: {
          xAxes: [{
            ticks: {
              display: true,
              fontFamily : 'Arial'
            }
          }]
        }
      }
    }
  },
  extends: Bar,
  mixins: [reactiveProp],
  mounted () {
    // this.chartData is created in the mixin.
    // If you want to pass options please create a local options object
    this.renderChart(this.chartData, this.options)
  }
}