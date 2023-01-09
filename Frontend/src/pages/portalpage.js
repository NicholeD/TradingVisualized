import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import PortalClient from "../api/portalClient";


        async function sellReal(i, stock, shares, errorHandler){
//                  let shares = parseInt(document.getElementById("sellQuantity").value);
                  console.log("SellQUANTITIY: " + shares);
//                  console.log("sellReal" + i);
                   let superPlus = shares;
                  let client = new PortalClient();
                  let result = await client.sellStock(stock, superPlus, errorHandler);
                  console.log(result.data);
                  window.location.reload();
        }

        function displayPopup(i, stock, errorHandler) {
                    // Create the popup element
                    let popup = document.createElement('div');

                    popup.classList.add('popup');
                    popup.innerHTML = '<div id="sellBox"><p>How many shares would you like to sell?</p><input type="text" id="sellQuantity"><button id="sellConfirm" type="button">Confirm</button></div>';

                    // Add the popup to the page
                    document.body.appendChild(popup);

                    // Add an event listener to the confirm button that triggers the sellShares function when the button is clicked
                    let sellConfirm = document.getElementById('sellConfirm');
                   /* let sellShares = parseInt(sellQuantity.value);*/
                    let ownedShares = stock.stock.quantity;
                    console.log("STOCK:" + JSON.stringify(stock));

                    console.log("OWNEDSHARES: " + ownedShares);
                    sellConfirm.addEventListener('click', function(){
                    let shares = parseInt(document.getElementById('sellQuantity').value);
                    console.log("SELLSHARES: " + shares);
                    if (shares == 0){
                        popup.innerHTML = "<div class='errorMessage'>One cannot sell nothing</div>";
                        setTimeout(function() {
                            displayPopup(i, stock, errorHandler);
                        }, 3000);
                    }
                    else if (shares > ownedShares){
                        popup.innerHTML = "<div class='errorMessage'>One cannot sell more than one has</div>";
                        setTimeout(function() {
                            displayPopup(i, stock, errorHandler);
                        }, 3000);                    }
                    else {
                    sellReal(i, stock, shares, errorHandler)
                    }
                    });
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

                let finale = "<table id='table' border='1' width='90%'><tr><th style='background-color: #4fbbb7; height: 3px;'>Symbol</th><th style='background-color: #4fbbb7; height: 3px;'>Quantity</th><th style='background-color: #4fbbb7; height: 3px;'>Purchase Price</th><th style='background-color: #4fbbb7; height: 3px;'>Total Value</th><th style='background-color: #4fbbb7; height: 3px;'>Purchase Date</th><th style='background-color: #4fbbb7; height: 3px;'>Sell</th></tr>";
                let divy = document.getElementById("stocklist");
                let funds = 0.00;

                for(let i = 0; i < portfolio.length; i++){
                    let symbol = (portfolio[i].stock.symbol).toString();
                    finale += "<tr><td>" + symbol.toUpperCase() + "</td><td>" + portfolio[i].stock.quantity + "</td><td>" + '$' + (portfolio[i].stock.purchasePrice).toFixed(2) + "</td><td>" + '$' + (portfolio[i].stock.quantity*portfolio[i].stock.purchasePrice).toFixed(2) + "</td><td>" + new Date(portfolio[i].stock.purchaseDate).toLocaleDateString() + "</td><td><button id='sell" + i + "' type='button' >  Sell</button></td></tr>";
                    funds += portfolio[i].stock.quantity*portfolio[i].stock.purchasePrice;
                }
                let totalValue = document.getElementById("totalValue");
                totalValue.innerHTML = `Portfolio Value: $${funds.toFixed(2)}`
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
                        displayPopup(i, portfolio[i], this.errorHandler);
                        return false;
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

