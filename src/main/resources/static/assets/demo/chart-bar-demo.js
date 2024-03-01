// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Bar Chart Example
var ctx = document.getElementById("myBarChart");
var myLineChart = new Chart(ctx, {
	type: 'bar',
	data: {
		labels: Object.keys(consolidadoEntrada),
		datasets: [{
			label: "Entrada",
			backgroundColor: "rgba(2,117,216,1)",
			borderColor: "rgba(2,117,216,1)",
			data: Object.values(consolidadoEntrada),
		}, {
			label: "Saída",
			backgroundColor: "rgba(255,0,0,1)",
			borderColor: "rgba(255,0,0,1)",
			data: Object.values(consolidadoSaida),
		}, {
			label: "Saldo Acumulado",
			backgroundColor: "rgba(144,238,144,1)",
			borderColor: "rgba(144,238,144,1)",
			data: Object.values(saldoAcumulado),
		}],
	},
	options: {
		scales: {
			xAxes: [{
				time: {
					unit: 'month'
				},
				gridLines: {
					display: false
				},
				ticks: {
					maxTicksLimit: 6
				}
			}],
			yAxes: [{
				ticks: {
					min: 0,
					maxTicksLimit: 5
				},
				gridLines: {
					display: true
				}
			}],
		},
		legend: {
			display: true
		}
	}
});
