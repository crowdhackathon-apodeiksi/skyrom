

function apixisi() {
    
    $('#apixisi-pie-container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: 'Duties Completion'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            series: [{
                    type: 'pie',
                    name: 'Αποίχηση στο κοινό',
                    data: [ 
                        {
                            name: 'Έχουν επιστρέψει',
                            y: 34,
                            color: '#64DEAB'
                        },
                        {
                            name: 'Δεν έχουν πάρει μέρος',
                            y: 13,
                            color: '#ED7A40'
                        },
                        {
                            name: 'Νέοι πελάτες',
                            y: 43,
                            color: '#E8BF43'
                        }
                    ]
                }]
        });
    
}