package com.kenzie.appserver.service;

@Service
public Class PurchasedStockService {
    private PurchaseStockRepository purchaseStockRepository;

    public PurchasedStockService(PurchaseStockRepository purchaseStockRepository) {
        this.purchaseStockRepository = purchaseStockRepository;
        }

    public PurchasedStock purchaseStock(Stock stock, int qty) {
        if (qty <=0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Qty has to be greater than 0, one simply cannot purchase nothing")
        }

        PurchasedStockRecord purchasedStockRecord = new PurchasedStockRecord();
        purchasedStockRecord.setName(stock.getName);
        purchasedStockRecord.setSymbol(stock.getSymbol);
        purchasedStockRecord.setDateOfPurchase(Local.Date.now()); //symantics are wrong here
        purchasedStockRecord.setPurchasePrice(stock.getPrice);
        purchasedStockRecord.setShares(qty);

        purchaseStockRepository.save(purchasedStockRecord)

        return new PurchasedStock(purchasedStockRecord.getName,
        purchasedStockRecord.getSymbol,
        purchasedStockRecord.getDateOfPurchase,
        purchasedStockRecord.getPurchasePrice,
        purchasedStockRecord.getShares);
        }

    public List<PurchasedStock> findByStockSymbol(String symbol) {
            List<PurchasedStockRecord> purchasedStockRecords = purchaseStockRepository
        .findByStockSymbol(symbol);

            List<PurchasedStock> purchasedStock = new ArrayList<>();

            for (PurchsedStockRecord record : purchasedStockRecords) {
                purchasedStock.add(new (PurchasedStock(record.getName(), record.getSymbol(),
        record.getDateOfPurchase(), record.getPurchasePrice(), record.getShares())))
        }

            return purchasedStock;

        }

 }