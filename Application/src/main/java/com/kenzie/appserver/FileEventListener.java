package com.kenzie.appserver;

import com.kenzie.appserver.service.model.Fish;
import com.kenzie.appserver.service.model.converter.JsonFishConverter;
import com.kenzie.appserver.service.model.converter.StockAndFishConverter;
import com.kenzie.capstone.service.client.StockServiceClient;
import com.kenzie.capstone.service.model.PurchaseStockRequest;
import com.kenzie.capstone.service.model.PurchasedStock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Component
public class FileEventListener {

    private long lastModified;
    public static File file;
    private StockServiceClient client;

    public static File getFile() {
        return file;
    }
    public FileEventListener() throws IOException {
        file = new File("../Frontend/src/exe/TV_Data/data.txt");
        client = new StockServiceClient();
    }

    @PostConstruct
    public void init() {
        List<PurchasedStock> stockList = client.getPurchasedStock("userId");
        System.out.println("Application Starting...");
        List<Fish> fishList = StockAndFishConverter.purchasedStockListConvertToFishList(stockList);
        //Convert PurchasedStock to Fish
        JsonFishConverter.convertToJsonFile(fishList, file);
        //Convert Fish to JsonFile
        lastModified = file.lastModified();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 10000)
    public void checkFileModified() {
//        System.out.println("FileModified");

        long modified = file.lastModified();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
            }
            if (modified > lastModified) {
                System.out.println("Modify detected");
                lastModified = modified;
                try (FileReader reader = new FileReader(file)) {
                    List<Fish> fishList = JsonFishConverter.convertToFishFromFile(file);
                    List<PurchaseStockRequest> requestList = StockAndFishConverter.fishListToPurchasedStockRecord(fishList);
                    for (PurchaseStockRequest request : requestList) {
                        client.addPurchasedStock(request);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
