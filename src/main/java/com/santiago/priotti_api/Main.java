package com.santiago.priotti_api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.santiago.priotti_api.Injector.ConfigModule;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ConfigModule());

        Router app = injector.getInstance(Router.class);

        app.launch();

    }

}
