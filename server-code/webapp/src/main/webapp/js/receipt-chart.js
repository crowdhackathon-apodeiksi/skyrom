//$(function () {
//    initReceiptChart();
//});
$(document).ready(function (){
   
});

var initReceiptChart = function () {
//    TaxMachina.utils.ajaxCall('/wserv/rs/v0/chart/receipt', 'GET', {}, 'json', function (data) {
//        if (!data || data === null)
//            return;

        $('#receipt-collected-chart').highcharts({
            title: {
                text: 'Αποδείξεις στις 10 τελευταίες μέρες',
                x: -20 //center
            },
            xAxis: {
                categories: ['4/6', '5/6','6/6','7/6','8/6','9/6','10/6','11/6','12/6','13/6','14/6'],
                labels:{
                    step: 1
                }
            },
            yAxis: {
                title: {
                    text: 'Άθροισμα'
                },
                plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
            },
            tooltip: {
                valueSuffix: ''
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: [
                {
                    name: 'Αποδείξεις',
                    data: [9,6,5,5,12,3,8,7,2,9,5]
                },
                {
                    name: 'Ποσό',
                    data: [44.5,63.2,12.9,23.2,102.3,23.34,67.6,13.4,34.4,32.54,55]
                }
            ]
        });
//    }, function (data) {
//        console.log(data);
//    });

};