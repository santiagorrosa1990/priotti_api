/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_web.Injector;

import com.google.inject.AbstractModule;
import com.santiago.priotti_web.Interfaces.Controller;
import com.santiago.priotti_web.Interfaces.Dao;
import com.santiago.priotti_web.Interfaces.Service;
import com.santiago.priotti_web.Item.ItemController;
import com.santiago.priotti_web.Item.ItemDao;
import com.santiago.priotti_web.Item.ItemService;

/**
 *
 * @author santiago
 */
public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Service.class).to(ItemService.class);
        bind(Controller.class).to(ItemController.class);
        bind(Dao.class).to(ItemDao.class);
    }

}
