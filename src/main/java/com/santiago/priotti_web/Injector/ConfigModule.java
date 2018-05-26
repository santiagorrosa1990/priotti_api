/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1.Injector;

import com.google.inject.AbstractModule;
import com.mycompany.mavenproject1.Interfaces.Controller;
import com.mycompany.mavenproject1.Interfaces.Service;
import com.mycompany.mavenproject1.Item.ItemController;
import com.mycompany.mavenproject1.Item.ItemService;

/**
 *
 * @author santiago
 */
public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(Service.class).to(ItemService.class);
        bind(Controller.class).to(ItemController.class);
        
    }

}
