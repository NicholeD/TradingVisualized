package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.StockService;
import com.kenzie.capstone.service.caching.CachingStockDao;
import com.kenzie.capstone.service.dao.ExampleDao;

import com.kenzie.capstone.service.dao.StockDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public StockService provideStockService(@Named("CachingDao") CachingStockDao cachingStockDao) {
        return new StockService(cachingStockDao);
    }
}

