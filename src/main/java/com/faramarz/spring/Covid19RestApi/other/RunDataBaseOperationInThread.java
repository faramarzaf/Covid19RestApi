package com.faramarz.spring.Covid19RestApi.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RunDataBaseOperationInThread {

    public static ExecutorService build() {
        return Executors.newFixedThreadPool(10);
    }

}
