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