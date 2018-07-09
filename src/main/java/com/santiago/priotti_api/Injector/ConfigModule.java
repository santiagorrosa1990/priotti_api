/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Injector;

import com.google.common.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.santiago.priotti_api.Interfaces.*;
import com.santiago.priotti_api.Item.*;
import com.santiago.priotti_api.User.UserAuthenticator;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * @author santiago
 */
public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Service.class).to(ItemService.class);
        bind(UserAuthenticator.class);
        bind(Controller.class).to(ItemController.class);
        bind(new TypeLiteral<Dao<Item>>(){}).to(ItemDao.class);
        bind(new TypeLiteral<Translator<Item, ItemRequest>>(){}).to(ItemTranslator.class);

    }

}
