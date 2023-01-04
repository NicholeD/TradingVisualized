import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PortalClient from "../api/portalClient";


        async function sellReal(i, errorHandler){
                  console.log("sellReal" + i);
                  console.log("errorHandler" + errorHandler);
                  let client = new PortalClient();
                  console.log('client' + client);
                  let portfolioStocks = JSON.parse(localStorage.getItem("portfolioStocks") || "[]" );
                  console.log(portfolioStocks);
                  let stock = portfolioStocks[i];
                  console.log(stock);
                  let result = await client.sellStock(stock, errorHandler);
                  console.log(result.data);
                  window.location.reload();
        }



class PortalPage extends BaseClass {

        constructor() {
            super();
            this.bindClassMethods(['onGet', 'renderPortfolio', 'visualize'], this);
            this.dataStore = new DataStore();
        }

        async mount() {
            this.client = new PortalClient();
            this.onGet();
            this.dataStore.addChangeListener(this.renderPortfolio);
            document.getElementById("visualize").addEventListener("click", this.visualize);
        }
        async visualize() {
          let result = this.client.visualize(this.errorHandler);
           console.log(result.data);

        }
        async renderPortfolio() {
            let resultArea = document.getElementById("results-area");

            const portfolio = this.dataStore.get("portfolio");
            console.log(this.dataStore.get("portfolio"));
            let Session = window.sessionStorage;
            if (portfolio) {
                console.log(portfolio);
//                const portfolioStocks = Object.assign({}, portfolio);
//                console.log(portfolioStocks);
                let finale = "<table border='1' width='90%'><tr><th style='background-color: #B894FF; height: 3px;'>Symbol</th><th style='background-color: #B894FF; height: 3px;'>Quantity</th><th style='background-color: #B894FF; height: 3px;'>Purchase Price</th><th style='background-color: #B894FF; height: 3px;'>Price Paid</th><th style='background-color: #B894FF; height: 3px;'>Purchase Date</th><th style='background-color: #B894FF; height: 3px;'>Sell</th></tr>";
                let divy = document.getElementById("stocklist");
                let funds = 10000.00;

                for(let i = 0; i < portfolio.length; i++){
                    finale += "<tr><td>" + portfolio[i].stock.symbol + "</td><td>" + portfolio[i].stock.quantity + "</td><td>" + portfolio[i].stock.purchasePrice + "</td><td>" + portfolio[i].stock.quantity * portfolio[i].stock.purchasePrice + "</td><td>" + portfolio[i].stock.purchaseDate + "</td><td><button id='sell" + i + "' type='button' >  Sell</button></td></tr>";
                    funds -= portfolio[i].stock.quantity.n*portfolio[i].stock.purchasePrice.n;
                }

                localStorage.setItem("funds", funds);
                localStorage.setItem("portfolioStocks", JSON.stringify(portfolio));
                console.log(localStorage);
                finale += "</table>";
                divy.innerHTML = finale;
                let buttons = document.querySelectorAll('button:not(#visualize)');
                // Attach the sellReal() method to each button element using an event listener
                for (let i = 0; i < buttons.length; i++){
                    console.log(buttons[i]);
                    document.getElementById('sell' + i).addEventListener('click',
                    function() {
                        sellReal(i, this.errorHandler);
                    });
                }
            } else {
                resultArea.innerHTML = "";
            }
        }

        async onGet() {

            let id = "userId";

            let result = await this.client.getPortfolio(id, this.errorHandler);
//            this.dataStore.set("portfolio", null);
            console.log(result.data);
            console.log(result);
            this.dataStore.set("portfolio", result);

            if (result) {
                this.showMessage("Got Portfolio!");
            } else {
                this.errorHandler("Error doing GET!  Try again...");
            }
        }
}

const main = async () => {
    console.log('Creating PortalPage instance...');
     const portalPage = new PortalPage();
     console.log('Mounting PortalPage instance...');
     portalPage.mount();
     console.log('Mounted!');
};
window.addEventListener('DOMContentLoaded', main);

