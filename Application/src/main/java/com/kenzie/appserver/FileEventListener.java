package com.kenzie.appserver;

import com.kenzie.appserver.service.FishService;
import com.kenzie.appserver.service.StockService;
import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.JsonFishConverter;
import com.kenzie.appserver.service.model.converter.StockAndFishConverter;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.kenzie.appserver.service.model.converter.StockAndFishConverter.stockService;

@Component
public class FileEventListener {
    @Value("${app.file.path}")
    private String filePath;

    private long lastModified;
    private File file;
    private FishService fishService;
    private StockServiceClient stockServiceClient;

    public FileEventListener(FishService fishService) throws IOException {
        file = new File(filePath);
        lastModified = file.lastModified();
        this.fishService = fishService;
        stockServiceClient = new StockServiceClient();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 20000)
    public void checkFileModified() {
        System.out.println("FileModified");

        long modified = file.lastModified();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            if (modified > lastModified) {
                System.out.println("Modify detected");
                lastModified = modified;
                try (FileReader reader = new FileReader(file)) {
                    List<Fish> fishList = JsonFishConverter.convertToFishFromFile(file);
                    List<PurchaseStockRequest> purchaseStockRequestList = StockAndFishConverter.fishListToRequestList(fishList);
                    for (PurchaseStockRequest request : purchaseStockRequestList) {
                        stockServiceClient.addPurchasedStock(request);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void setDataFile() {
        //get all stocks via calling stockServiceClient.getPurchaseStock
        List<PurchasedStock> purchasedStocks = stockServiceClient.getPurchasedStock("userId");

        //convert the purchasedStock to Fish
        List<Fish> fishList = purchasedStocks.stream()
                .map(StockAndFishConverter::purchaseStockToFish)
                .collect(Collectors.toList());

        //call this convertToJsonFile from jsonToFishConverter on the list of Fish
        JsonFishConverter.convertToJsonFile(fishList, file);
    }
}
