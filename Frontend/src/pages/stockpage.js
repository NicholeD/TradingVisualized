// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett

import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import StockClient from "../api/stockClient";
import ExampleClient from "../api/exampleClient";


class StockPage extends BaseClass{
    constructor() {
        super();
        this.bindClassMethods(['onGet', 'renderStock'], this);
        this.dataStore = new DataStore();
    }
    async mount() {
        document.getElementById('form').addEventListener('submit', this.onGet);
        this.client = new StockClient();

        this.dataStore.addChangeListener(this.renderStock)
    }
    async renderStock(){
        let resultArea = document.getElementById("stockresults");

        const stock = this.dataStore.get("stock");
        if (stock) {
            let stockStocks = stock.stocks;
            let result = "";
            result += `<div>Stock Name: ${stock.name}</div>`
            result += `<div>Stock Symbol: ${stock.symbol.toUpperCase()}</div>`
            result += `<div>Current Price: \$${stockStocks[0].purchasePrice}</div>`
            var date = new Date(Date.now());
            let currentStock = stockStocks[0];
            const purchasePrices = stockStocks.map(stock => stock.purchasePrice);
            console.log(purchasePrices);
            result += `<div class="stock">
            <h3>\$${currentStock.purchasePrice}</h3>
            ${date.toLocaleDateString()}
            <br></br>
            ${stockStocks.length} day high: $${Math.max(...purchasePrices)}
            <br></br>
            ${stockStocks.length} day low: $${Math.min(...purchasePrices)}
            <a class="hyperlink" href="checkout.html?name=${currentStock.name}&symbol=${currentStock.symbol}&currentprice=${currentStock.purchasePrice}&purchaseprice=${currentStock.purchasePrice}&purchasedate=${currentStock.purchaseDate}"><span></br></span></a></div>`;

            var stockPrice = [];
            let count = 0;
            var max = 0;
            var min = 1000;
            for (let stoc of stockStocks.reverse()){
                if (count == 30){
                    break;
                }

                let price = stoc.purchasePrice;
                let date = stoc.purchaseDate.slice(-5);
                stockPrice.push({ x: date, y: price });

                if (price > max){
                    max = price;
                }
                if (price < min){
                    min = price;
                }
                count++;
            }

            var currentAngle = 360;

            var fillStylesObj = {
                 'currentColor -> lightenMore': ['currentColor', 'lightenMore'],
                 'currentColor -> lighten': ['currentColor', 'lighten'],
                 'currentColor -> darkenMore': ['currentColor', 'darkenMore'],
                 'currentColor -> darken': ['currentColor', 'darken'],
                 'lighten -> darken': ['lighten', 'darken'],
                 'lightenMore -> darkenMore': ['lightenMore', 'darkenMore']
            };

            var currentColors = fillStylesObj['currentColor -> lightenMore'];
            var currentFill = currentColors.concat(currentAngle);
            const chart = new JSC.chart("chart", {
                type: 'area spline',
                title_label_text: `Stock Price over the last ${stockStocks.length} days`,
                yAxis: {
                    defaultTick_gridLine_visible: false,
                    scale: {
                        range: {
                            min: min - 10,
                            max: max + 10,
                            padding: 1
                        }
                    }
                },
                xAxis: {
                    defaultTick: {
                        color: 'red',
                        gridLine_visible: false,
                    }
                    },
                defaultSeries: {
                    color: '#45a29f',
                     shape: { fill: currentFill }
                },
                defaultPoint: {
                    marker: {
                        visible: false
                    }
                },
                series: [{points: stockPrice}],
                legend_visible: false,
                autoFit: false,
                height: 400,
                width: 700,

            });
            var chartElement = document.getElementById("chart");
            var overlay = document.getElementById("overlay");
            var expandedChartContainer = document.getElementById("expanded-chart");

  // show the overlay when the chart is clicked
            chartElement.addEventListener("click", function() {
                overlay.style.display = "block";
                const expandedChart = new JSC.chart(expandedChartContainer, {
                    type: 'area spline',
                    title_label_text: `Stock Price over the last ${stockStocks.length} days`,
                     yAxis: {
                           defaultTick_gridLine_visible: false,
                           scale: {
                               range: {
                                    min: min - 10,
                                    max: max + 10,
                                    padding: 1
                               }
                           }
                     },
                     xAxis: {
                           defaultTick: {
                               color: 'red',
                               gridLine_visible: false,
                           }
                     },
                     defaultSeries: {
                           color: '#45a29f',
                           shape: { fill: currentFill }
                     },
                     defaultPoint: {
                           marker: {
                                visible: false
                           }
                     },
                    series: [{points: stockPrice}],
                    legend_visible: false,
                    autoFit: false,
                    margin: [150, 50, 150, 50],
                    width: window.innerWidth,
                    height: window.innerHeight,
                })
                });
            var closeButton = document.getElementById("close-button");
            closeButton.addEventListener("click", function() {
            overlay.style.display = "none";
  });
            resultArea.innerHTML = result;
        } else {
            resultArea.innerHTML = "Searching...";
        }
    }

    async onGet(event){
        event.preventDefault();

        let symbol = document.getElementById("searchstock").value;
        this.dataStore.set("stock", null);
        let result = await this.client.getStocks(symbol, this.errorHandler);
        this.dataStore.set("stock", result);
        console.log(result);

        if (result) {
            this.showMessage(`Got ${result.name}!`)
        } else {
            this.errorHandler("Error doing GET!  Try again...");
        }
    }

}

const main = async () => {
    const stockPage = new StockPage();
    await stockPage.mount();
};
window.addEventListener('DOMContentLoaded', main);