package com.santiago.priotti_api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.santiago.priotti_api.Injector.ConfigModule;
import com.santiago.priotti_api.Item.Item;
import lombok.Builder;
import lombok.ToString;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ConfigModule());

        Router app = injector.getInstance(Router.class);

        app.launch();


    }

}
