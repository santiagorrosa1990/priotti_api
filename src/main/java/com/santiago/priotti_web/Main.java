package com.mycompany.priotti_web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycompany.mavenproject1.Injector.ConfigModule;

public class Main {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ConfigModule());

        Router app = injector.getInstance(Router.class);

        app.launch();


    }

}
