<template>
  <div>
    <Row>
      <Col span="12">
        <h2>推荐组合</h2>
        <div class="middleBorder">
          <CellGroup class="ph3 pt3" v-on:on-click="switchOption">
            <Cell v-for="item in data.optionList" :title="item.name"
                  :extra="item.type>0?'买'+item.type:'卖'+(-item.type)"
                  :key="data.optionList.indexOf(item)" class="tl w-80 center" :class="{backRed:item.cp===1}"
                  :name="data.optionList.indexOf(item)"/>
          </CellGroup>
          <!--接下来写展示项目-->
          <div class="interval"></div>
          <h2 class="pt3">期权信息</h2>
          <div class="w-80 ml-auto pt3">
            <p class="f4 tl pb1">{{currentOption.cp===1?'看涨':'看跌'}}期权</p>
            <table class="w-100 tl">
              <tr>
                <td>交易代码</td>
                <td>{{currentOption.tradeCode}}</td>
              </tr>
              <tr>
                <td>到期时间</td>
                <td>{{currentOption.expireTime}}</td>
              </tr>
              <tr>
                <td>执行价格</td>
                <td>{{currentOption.k}}</td>
              </tr>
              <tr>
                <td>成交价格</td>
                <td>{{currentOption.type>0?currentOption.price1:currentOption.price2}}</td>
              </tr>
              <tr>
                <td>delta</td>
                <td>{{currentOption.delta}}</td>
              </tr>
              <tr>
                <td>gamma</td>
                <td>{{currentOption.gamma}}</td>
              </tr>
              <tr>
                <td>theta</td>
                <td>{{currentOption.theta}}</td>
              </tr>
              <tr>
                <td>vega</td>
                <td>{{currentOption.vega}}</td>
              </tr>
              <tr>
                <td>rho</td>
                <td>{{currentOption.rho}}</td>
              </tr>
              <tr>
                <td>beta</td>
                <td>{{currentOption.beta}}</td>
              </tr>
            </table>
          </div>
        </div>
      </Col>
      <Col span="12" class="">
        <h2>资产收益</h2>
        <div class="w-60 center pt3">
          <table class="w-100 tl">
            <tr>
              <td>资产期望收益率</td>
              <td>{{(data.returnOnAssets*100).toFixed(2)}}%</td>
            </tr>
            <tr>
              <td>资产杠杆</td>
              <td>{{data.beta.toFixed(4)}}</td>
            </tr>
          </table>
        </div>
        <div class="interval"></div>
        <h2 class="pt3">组合特征</h2>
        <div class="w-60 center pt3">
          <table class="w-100 tl">
            <tr>
              <td>成本(不含保证金)</td>
              <td>{{data.cost.toFixed(2)}}</td>
            </tr>
            <tr>
              <td>保证金</td>
              <td>{{data.bond.toFixed(2)}}</td>
            </tr>
            <tr>
              <td>delta</td>
              <td>{{data.z_delta.toFixed(4)}}</td>
            </tr>
            <tr>
              <td>gamma</td>
              <td>{{data.z_gamma.toFixed(4)}}</td>
            </tr>
            <tr>
              <td>theta</td>
              <td>{{data.z_theta.toFixed(4)}}</td>
            </tr>
            <tr>
              <td>vega</td>
              <td>{{data.z_vega.toFixed(4)}}</td>
            </tr>
            <tr>
              <td>rho</td>
              <td>{{data.z_rho.toFixed(4)}}</td>
            </tr>
            <tr>
              <td>组合期望收益率</td>
              <td>{{(data.em*100).toFixed(2)}}%</td>
            </tr>
            <tr>
              <td>组合杠杆</td>
              <td>{{data.beta.toFixed(4)}}</td>
            </tr>
          </table>
        </div>
      </Col>
    </Row>
    <Row class="pt4">
      <h2 class="w-90 tl center">回测收益图</h2>
      <div class="interval2"></div>
      <Card style="margin: 50px 0;">
        <div>
          <div id="assertPriceProfit" style="height: 600px;" ref="assertPriceProfit"></div>
        </div>
      </Card>
      <Card style="margin: 50px 0;">
        <div>
          <div id="profitProbability" style="height: 600px;" ref="profitProbability"></div>
        </div>
      </Card>
      <Card style="margin: 50px 0;">
        <div>
          <div id="historyProfitProbability" style="height: 600px;" ref="historyProfitProbability"></div>
        </div>
      </Card>
    </Row>
  </div>
</template>

<script>
  export default {
    name: "ResultDisplay",
    data() {
      return {
        currentOption: '',
        assertPriceProfit: {},
        profitProbability: {},
        historyProfitProbability: {}
      }
    },
    props: [
      'data',
    ],
    computed: {
      //图表option
      assertPriceOption: function () {
        return {
          title:{
            text:'不同标的价格下的组合收益'
          },
          legend: {
            data: ['收益']
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            },
          },
          xAxis: {
            name:'标的价格/元',
            type: 'category',
            boundaryGap: false,
            data: this.data.assertPrice2Profit[0]
          },
          yAxis: {
            name:'收益',
            type: 'value',
          },
          series: [
            {
              name: '收益',
              type: 'line',
              data: this.data.assertPrice2Profit[1].map(function (item) {
                return parseFloat(item).toFixed(2);
              }),
            }
          ]
        };
      },
      profitOption: function () {
        return {
          title:{
            text:'组合收益在预期市场内的概率分布'
          },
          legend: {
            data: ['概率']
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            },
          },
          xAxis: {
            name:'收益',
            type: 'category',
            boundaryGap: false,
            data: this.data.profit2Probability[0].map(function (item) {
              return parseFloat(item).toFixed(2);
            }),
          },
          yAxis: {
            name:'概率',
            type: 'value',
          },
          series: [
            {
              name: '概率',
              type: 'line',
              itemStyle:{
                normal:{
                  color:'#FBB8A1',
                  lineStyle: {
                    color:'#FBB8A1',
                    width:2
                  },
                }
              },
              data: this.data.profit2Probability[1]
            }
          ]
        };
      },
      historyProfitOption: function () {
        return {
          title:{
            text:'组合收益在历史市场内的概率分布'
          },
          legend: {
            data: ['概率']
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            },
          },
          xAxis: {
            name:'历史收益',
            type: 'category',
            boundaryGap: false,
            data: this.data.historyProfit2Probability[0].map(function (item) {
              return parseFloat(item).toFixed(2);
            }),
          },
          yAxis: {
            name:'概率',
            type: 'value',
          },
          series: [
            {
              name: '概率',
              type: 'line',
              itemStyle: {
                normal:{
                  color:'#697883',
                  lineStyle:{
                    color:'#697883',
                    width:2
                  }
                }
              },
              data: this.data.historyProfit2Probability[1]
            }
          ]
        };
      }
    },
    methods: {
      switchOption: function (index) {
        this.currentOption = this.data.optionList[index];
      },
      refreshChart: function () {
        this.assertPriceProfit.setOption(this.assertPriceOption);
        this.profitProbability.setOption(this.profitOption);
        this.historyProfitProbability.setOption(this.historyProfitOption);
        this.$emit("loadOver");
      }
    },
    mounted: function () {
      this.currentOption = this.data.optionList[0];

      //绘图
      this.assertPriceProfit = this.$echarts.init(this.$refs.assertPriceProfit);
      this.profitProbability = this.$echarts.init(this.$refs.profitProbability);
      this.historyProfitProbability = this.$echarts.init(this.$refs.historyProfitProbability);
      this.refreshChart();
    },
    watch: {
      data() {
        this.refreshChart();
      }
    }
  }
</script>

<style scoped>
  .middleBorder {
    border-right: 1px solid rgba(0, 0, 0, .3);
  }

  .bottomBorder {
    border-bottom: 1px solid rgba(0, 0, 0, .3);
  }

  .interval {
    width: 80%;
    margin: auto;
    padding-top: 1.5rem;
    border-bottom: 1px solid rgba(0, 0, 0, .3);
  }

  .interval2 {
    width: 90%;
    margin: auto;
    padding-top: 0.15rem;
    border-bottom: 1px solid rgba(20, 99, 180, .7);
  }

  .backRed:hover {
    background: #FFEEEE;
  }

  .bgred {
    background: #FFEEEE;
  }

  table {
    line-height: 30px;
  }

  h2 {
    color: #1463B4;
  }
</style>
