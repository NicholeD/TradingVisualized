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
    async sellStock(stock, errorCallBack) {
        try {
            let userId = stock.id.s;
            let recordId = stock.recordId.s;
            let stockSymbol = stock.symbol.s;
            let name = stock.name.s;
            let salePrice = stock.purchasePrice.n;
            let shares = stock.quantity.n;
            let sellStockDate = new Date().toLocaleDateString('en-US');

            let sellStockRequest = [userId, recordId, stockSymbol, name, salePrice, shares, sellStockDate];
            console.log(sellStockRequest);
            const response = await this.client.post(`/stocks/sell`, {
                userId: stock.id.s,
                recordId: stock.recordId.s,
                stockSymbol: stock.symbol.s,
                name: stock.name.s,
                salePrice: stock.purchasePrice.n,
                shares: stock.quantity.n,
                sellStockDate: new Date().toLocaleDateString('en-US')
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
