<template>
    <div style="width: 100%">
      <p style="font-weight: bold;font-size: 25px">套期保值效果展示&nbsp&nbsp<Button type="primary" size="small" @click="addToMyGroup">添加至我的组合</Button></p>
      <div style="text-align: left">
        <div>
          <p style="font-size: 20px;font-weight: bold;padding-left: 15%">展示期权：</p>
          <br>
          <optionGroup ref="option_group"></optionGroup>
        </div>
        <br>
        <div>
          <span style="font-size: 20px;font-weight: bold;padding-left:15%">到达预期最大亏损：<p style="-webkit-text-fill-color: red;display: inline">{{expectedLoss}}</p></span>
        </div>
        <br>
        <div style="width: 100%;text-align: center">
          <p style="font-size: 20px;font-weight: bold;padding-left: 15%;text-align: left">组合表现：</p>
          <div id="myChart" style="width:800px;height: 300px;margin: auto"></div>
        </div>
      </div>
    </div>
</template>

<script>
  import optionGroup from "../hedging/OptionTable"
    export default {
        name: "display_result",
      components:{optionGroup},
      data () {
        return {
          expectedLoss:'',
          graph:'',
          lineName:['持有','不持有'],
          groupName:'',
          originData:'',
          n:'',
          pAsset:'',
          sExp:'',
          flag:''
        }
      },
      methods: {
        drawLine(){
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('myChart'))
          // 绘制图表
          myChart.setOption({
            legend: {
              data:this.lineName,
              x: 'center'
            },
            tooltip: {
              trigger: 'axis',
              position: function (pt) {
                return [pt[0], '10%'];
              }
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: this.graph[0]
            },
            yAxis: {
              type: 'value',
              boundaryGap: [0, '100%']
            },
            dataZoom: [{
              type: 'inside',
              start: 0,
              end: 10
            }, {
              start: 0,
              end: 10,
              handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
              handleSize: '80%',
              handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
              }
            }],
            series: [
              {
                name:this.lineName[0],
                type:'line',
                smooth:true,
                symbol: 'none',
                sampling: 'average',
                itemStyle: {
                  normal: {
                    color: 'rgb(25, 191, 107)'
                  }
                },
                data: this.graph[1]
              },
              {
                name:this.lineName[1],
                type:'line',
                smooth:true,
                symbol: 'none',
                sampling: 'average',
                itemStyle: {
                  normal: {
                    color: 'rgb(254, 64, 20)'
                  }
                },
                data: this.graph[2]
              },
            ]
          });
        },
        addToMyGroup(){
          this.$Modal.confirm({
            title: '新建组合',
            okText:'确认',
            cancelText:'取消',
            render: (h) => {
              return h('Input', {
                props: {
                  autofocus: true,
                  placeholder: '请输入组合名'
                },
                on: {
                  input: (val) => {
                    this.groupName= val;
                  }
                }
              })
            },
            onOk: () => {
              var data=this.originData
              data.name=this.groupName
              data.trackingStatus=false
              data.type=1
              data.graph=this.originData.graph
              data.iK=this.originData.iK
              data.options=[this.originData.option]

              this.handleSpinCustom()
              this.axios.post('/backend/portfolio',data)
                .then(re=>{
                  if(re.data.msg=='Add portfolio success'){
                    this.$Message.info('添加成功');
                    this.$router.push('/myPortfolio')
                  }
                  else{
                    this.$Message.error('添加失败');
                  }
                })

            },
            onCancel: () => {
              this.current_clicked_id=''
            }
          });
        },
        handleSpinCustom () {
          this.$Spin.show({
            render: (h) => {
              return h('div', [
                h('Icon', {
                  'class': 'demo-spin-icon-load',
                  props: {
                    type: 'ios-loading',
                    size: 18
                  }
                }),
                h('div', '加载中')
              ])
            }
          });
          setTimeout(() => {
            this.$Spin.hide();
          }, 3000);
        }
      }

    }
</script>

<style scoped>
  .demo-spin-icon-load{
    animation: ani-demo-spin 1s linear infinite;
  }
</style>
