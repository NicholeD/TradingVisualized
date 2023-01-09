// * Copyright (c) 2022 All Rights Reserved
// * Title: Time Travel Trading
// * Authors: Scott Zastrow, Nichole Davidson, Alexander Bennett

import BaseClass from "../util/baseClass"
import axios from 'axios'

export default class PortalClient extends BaseClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getPortfolio', 'visualize', 'sellStock'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady();
        }
    }

    async getPortfolio(userId, errorCallBack) {
        try {
            const response = await this.client.get(`/stocks/portfolio/${userId}`);
            console.log(response.data);
            return response.data;
        } catch (error) {
            this.handleError("getPortfolio", error, errorCallBack);
        }
    }
    async visualize(errorCallBack){
        try {
            const response = await this.client.get(`/visualize`);
            return response.data;
        }
        catch (error){
            this.handleError("visualize", error, errorCallBack);
        }
    }
    async sellStock(stock, shares, errorCallBack) {
        try {

            let userId = stock.userId;
            let stockSymbol = stock.stock.symbol;
            let name = stock.stock.name;
            let salePrice = stock.stock.purchasePrice;
            let sellStockDate = new Date().toLocaleDateString('en-US');
            console.log("SHARES: " + shares)
            let sellStockRequest = [userId, stockSymbol, name, salePrice, shares, sellStockDate];
            console.log(sellStockRequest);
            const response = await this.client.post(`/stocks/sell`, {
                userId: sellStockRequest[0],
                stockSymbol: sellStockRequest[1],
                stockName: sellStockRequest[2],
                salePrice: sellStockRequest[3],
                shares: sellStockRequest[4],
                sellStockDate: sellStockRequest[5]
            });
            return response.data;

        }
        catch (error) {
            this.handleError("sellStock", error, errorCallBack);
        }

    }
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error);
        }
    }

}
