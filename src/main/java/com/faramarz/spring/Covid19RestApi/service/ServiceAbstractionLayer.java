package com.faramarz.spring.Covid19RestApi.service;


import java.io.IOException;

public abstract class ServiceAbstractionLayer {

    abstract public void fetchConfirmedData() throws IOException, InterruptedException;


}
