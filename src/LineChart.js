import { Bar, mixins } from 'vue-chartjs'
const { reactiveProp } = mixins

export default {
  data () {
    return {
      options: {
        scales: {
          xAxes: [{
            ticks: {
              
              fontFamily: 'SpoqaHanSans'
            },
            gridLines: {
              color: "rgba(0, 0, 0, 0)",
          },
            type: 'category',
            labels: [
              '공기업', '공무원', '200인 이상',
              '200인 이하', '20인 이하', '기타'
              ]
        }],          
          yAxes: [{
            gridLines: {
              color: "rgba(0, 0, 0, 0)",
            },
            ticks: {
                callback: function(value, index, values) {
                    return `${value}명`;
                }
            }
        }]
        //   xAxes: [{
        //     gridLines: {
        //         offsetGridLines: true
        //     }
        // }]
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