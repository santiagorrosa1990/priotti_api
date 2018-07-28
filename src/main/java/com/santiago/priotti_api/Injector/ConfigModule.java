/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Injector;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.santiago.priotti_api.Interfaces.Translator;
import com.santiago.priotti_api.Item.*;
import com.santiago.priotti_api.User.UserAuthenticator;
import com.santiago.priotti_api.User.UserService;

/**
 *
 * @author santiago
 */
public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ItemService.class);
        bind(UserAuthenticator.class);
        bind(ItemController.class);
        bind(ItemDao.class);
        bind(ItemTranslator.class);
        bind(UserService.class);

    }

}
