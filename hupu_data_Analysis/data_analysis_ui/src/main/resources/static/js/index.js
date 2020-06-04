// 柱状图模块1
(function() {
  // 1实例化对象
  var myChart = echarts.init(document.querySelector(".bar .chart"));

  myChart.setOption({
    color: ["#2f89cf"],
    tooltip: {
      trigger: "axis",
      axisPointer: {
        // 坐标轴指示器，坐标轴触发有效
        type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    grid: {
      left: "0%",
      top: "10px",
      right: "0%",
      bottom: "4%",
      containLabel: true
    },
    legend: {
      data:['销量']
    },
    xAxis: [
      {
        type: "category",
        data: [

        ],
        axisTick: {
          alignWithLabel: true
        },
        // 修改刻度标签 相关样式
        axisLabel: {
          color: "rgba(255,255,255,.6) ",
          fontSize: "12"
        },
        // 不显示x坐标轴的样式
        axisLine: {
          show: false
        }
      }
    ],
    yAxis: [
      {
        type: "value",
        // 修改刻度标签 相关样式
        axisLabel: {
          color: "rgba(255,255,255,.6) ",
          fontSize: 12
        },
        // y轴的线条改为了 2像素
        axisLine: {
          lineStyle: {
            color: "rgba(255,255,255,.1)",
            width: 2
          }
        },
        // y轴分割线的颜色
        splitLine: {
          lineStyle: {
            color: "rgba(255,255,255,.1)"
          }
        }
      }
    ],
    series: [{
      type: "bar",
      barWidth: "35%",
      name: '人口数',
      type: 'bar',
      barWidth: "35%",
      data: [],
      itemStyle: {
        // 修改柱子圆角
        barBorderRadius: 5
      }
    }]
  });
  myChart.showLoading({text: '数据正在加载中...'  });
  $.ajax({
    url:"/getAddressData",
    dataType:"json",
    success:function(jsonData){
      myChart.setOption({
        xAxis: {
          type: "category",
          data: jsonData.xAxisCategory
        },
        series: [{
          // 根据名字对应到相应的系列
          name: '人口数',
          type: "bar",
          data: jsonData.datas
        }]
      });
      // 设置加载等待隐藏
      myChart.hideLoading();
    }
  });
  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function() {
    myChart.resize();
  });


})();

// 柱状图2
(function() {
  var myColor = ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"];
  // 1. 实例化对象
  var myChart = echarts.init(document.querySelector(".bar2 .chart"));
  // 2. 指定配置和数据

  myChart.showLoading({text: '数据正在加载中...'  });
  $.ajax({
    url:"/getFavoriteSportData",
    dataType:"json",
    success:function(jsonData){
      myChart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          type: 'category',
          data: jsonData.xAxisCategory,
          splitLine: {show: false},
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: true,
            textStyle: {
              color: '#FF4500'
            }

          },
          offset: 10,
          nameTextStyle: {
            fontSize: 15
          }
        },
        series: [
          {
            name: '数量',
            type: 'bar',
            data: jsonData.datas,
            barWidth: 14,
            barGap: 10,
            smooth: true,
            label: {
              normal: {
                show: true,
                position: 'right',
                offset: [5, -2],
                textStyle: {
                  color: '#F68300',
                  fontSize: 13
                }
              }
            },
            itemStyle: {
              emphasis: {
                barBorderRadius: 7
              },
              normal: {
                barBorderRadius: 7,
                color: new echarts.graphic.LinearGradient(
                    0, 0, 1, 0,
                    [
                      {offset: 0, color: '#3977E6'},
                      {offset: 1, color: '#37BBF8'}

                    ]
                )
              }
            }
          }
        ]
      });
      // 设置加载等待隐藏
      myChart.hideLoading();
    }
  });

  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function() {
    myChart.resize();
  });
})();
// 折线图1模块制作
(function() {

  // 1. 实例化对象
  var myChart = echarts.init(document.querySelector(".line .chart"));
  myChart.setOption({
    color: ['#3398DB'],
    tooltip: {
      trigger: 'axis',
      axisPointer: {            // 坐标轴指示器，坐标轴触发有效
        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: [],
        axisTick: {
          alignWithLabel: true,

        },
        axisLabel: {
          show: true,
          textStyle: {
            color: '#FF4500'
          }

        }

      }
    ],
    yAxis: [
      {
        type: 'value',
        axisLabel: {
          show: true,
          textStyle: {
            color: '#FF4500'
          }

        }

      }
    ],
    series: [
      {
        name: '联盟',
        type: 'bar',
        barWidth: '60%',
        data: []
      }
    ]
  });
  myChart.showLoading({text: '数据正在加载中...'  });
  $.ajax({
    url:"/getFavoriteLeagueData",
    dataType:"json",
    success:function(jsonData){
      myChart.setOption({
        xAxis: {
          type: "category",
          data: jsonData.xAxisCategory,
        },
        series: [{
          // 根据名字对应到相应的系列
          name: '联盟',
          type: "bar",
          data: jsonData.datas
        }]
      });
      // 设置加载等待隐藏
      myChart.hideLoading();
    }
  });


  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function() {
    myChart.resize();
  });

})();





// 折线图2 模块制作
(function() {
  var myChart = echarts.init(document.querySelector(".line2 .chart"));
  myChart.setOption({
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: [820, 932, 901, 934, 1290, 1330, 1320],
      type: 'line'
    }]
  });
  myChart.showLoading({text: '数据正在加载中...'  });
  $.ajax({
    url:"/getTopicData",
    dataType:"json",
    success:function(jsonData){
      myChart.setOption({
        xAxis: [{
          type: "category",
          data: jsonData.xAxisCategory,
          axisLabel: {
            show: true,
            textStyle: {
              color: '#FF4500'
            }

          }

        }
        ],
        yAxis: [{
          type: 'value',
          axisLabel: {
            show: true,
            textStyle: {
              color: '#FF4500'
            }

          }

        }],
        series: [{
          data: jsonData.datas,
          type: 'line'
        }]
      });
      // 设置加载等待隐藏
      myChart.hideLoading();
    }
  });
  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function() {
    myChart.resize();
  });
})();
// 饼形图1
(function() {
  // 1. 实例化对象
  var myChart = echarts.init(document.querySelector(".pie .chart"));
  // 2.指定配置
  myChart.setOption( {
    color: ["#FFFF00", "#4A766E", "#FF0000"],
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)"
    },

    legend: {
      bottom: "0%",
      // 修改小图标的大小
      itemWidth: 10,
      itemHeight: 10,
      // 修改图例组件的文字为 12px
      textStyle: {
        color: "rgba(255,255,255,.5)",
        fontSize: "12"
      }
    },
    series: [
      {
        name: "性别分布",
        type: "pie",
        // 这个radius可以修改饼形图的大小
        // radius 第一个值是内圆的半径 第二个值是外圆的半径
        radius: ["40%", "60%"],
        center: ["50%", "45%"],
        avoidLabelOverlap: false,
        // 图形上的文字
        label: {
          show: false,
          position: "center"
        },
        // 链接文字和图形的线是否显示
        labelLine: {
          show: false
        },
        data: [
        ]
      }
    ]
  });

  $.ajax({
    url:"/getSexData",
    dataType:"json",
    success:function(data){
      myChart.setOption({
        series: [
          {
            name: "性别分布",
            type: "pie",
            // 这个radius可以修改饼形图的大小
            // radius 第一个值是内圆的半径 第二个值是外圆的半径
            radius: ["40%", "60%"],
            center: ["50%", "45%"],
            avoidLabelOverlap: false,
            // 图形上的文字
            label: {
              show: false,
              position: "center"
            },
            // 链接文字和图形的线是否显示
            labelLine: {
              show: false
            },
            data: data
          }
        ]
      });
    }
  });

  // 4. 让图表跟随屏幕自动的去适应
  window.addEventListener("resize", function() {
    myChart.resize();
  });
})();

// 饼形图2 地区分布模块
(function() {
  var myChart = echarts.init(document.querySelector(".pie2 .chart"));
  myChart.setOption({
    color: [
      "#006cff",
      "#60cda0",
      "#ed8884",
      "#ff9f7f",
      "#0096ff",
      "#9fe6b8",
      "#32c5e9",
      "#1d9dff"
    ],
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
      bottom: "0%",
      itemWidth: 10,
      itemHeight: 10,
      textStyle: {
        color: "rgba(255,255,255,.5)",
        fontSize: "12"
      }
    },
    series: [
      {
        name: "喜欢球队排行",
        type: "pie",
        radius: ["10%", "70%"],
        center: ["50%", "50%"],
        roseType: "radius",
        // 图形的文字标签
        label: {
          fontSize: 10
        },
        // 链接图形和文字的线条
        labelLine: {
          // length 链接图形的线条
          length: 6,
          // length2 链接文字的线条
          length2: 8
        },
        data: [
          // { value: 20, name: "云南" },
          // { value: 26, name: "北京" },
          // { value: 24, name: "山东" },
          // { value: 25, name: "河北" },
          // { value: 20, name: "江苏" },
          // { value: 25, name: "浙江" },
          // { value: 30, name: "四川" },
          // { value: 42, name: "湖北" }
        ]
      }
    ]
  });
  $.ajax({
    url:"/getFavoriteTeamData",
    dataType:"json",
    success:function(data){
      myChart.setOption({
        series: [
          {
            name: "喜欢球队排行",
            type: "pie",
            radius: ["40%", "60%"],
            center: ["50%", "45%"],
            avoidLabelOverlap: false,
            // 图形上的文字
            label: {
              show: false,
              position: "center"
            },
            // 链接文字和图形的线是否显示
            labelLine: {
              show: false
            },
            data: data
          }
        ]
      });
    }
  });

  // 监听浏览器缩放，图表对象调用缩放resize函数
  window.addEventListener("resize", function() {
    myChart.resize();
  });
})();

