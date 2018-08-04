/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Cart.CartRequest;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;
import com.santiago.priotti_api.Wrappers.RequestWrapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import spark.Request;
import spark.Response;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ItemService {

    private final ItemDao itemDao;
    private final ItemTranslator translator;

    @Inject
    public ItemService(ItemDao itemDao, ItemTranslator translator) {
        this.itemDao = itemDao;
        this.translator = translator;
    }

    public String update(String body) {
        Item item = translator.buildItem(body);
        try {
            itemDao.updateOne(item);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
        return "Item actualizado"; //TODO devolver un standard response
    }

    public void updateAll(String body) {
        List<Item> itemsList = translator.translateList(body);
        try {
            itemDao.updateAll(itemsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String fullSearch(ItemRequest request) {
        Boolean novelty = request.getNovelty();
        Boolean offer = request.getOffer();
        List keywords = getKeywords(request.getKeywords());
        BigDecimal coeficient = request.getCoeficient();
        String query = new ItemQueryBuilder().build(keywords, offer, novelty);
        try {
            List<Item> itemList = itemDao.search(query);
            return new ItemPresenter().fullTable(itemList, coeficient);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String basicSearch(ItemRequest request) {
        Boolean novelty = request.getNovelty();
        Boolean offer = request.getOffer();
        List keywords = getKeywords(request.getKeywords());
        String query = new ItemQueryBuilder().build(keywords, offer, novelty);
        try {
            List<Item> itemList = itemDao.search(query);
            return new ItemPresenter().basicTable(itemList);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getAll() {
        List<List<String>> datatablesItemList;
        try {
            List<Item> itemList = itemDao.read();
            datatablesItemList = itemList.stream().map(item -> Arrays.asList(
                    item.getCodigo(),
                    item.getAplicacion(),
                    item.getRubro(),
                    item.getMarca(),
                    item.getPrecioLista().toString()))
                    .collect(Collectors.toList());
            return new Gson().toJson(datatablesItemList);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    private List<String> getKeywords(String search) {
        String[] words = search.split("\\s+");
        return Arrays.asList(words);
    }



    public Object buildXlsx(Response response, ItemRequest request){
        BigDecimal coeficient = request.getCoeficient();
        coeficient = coeficient.divide(new BigDecimal(100)).add(new BigDecimal(1));
        String date = LocalDateTime.now().toString();
        response.raw().setContentType("application/octet-stream");
        response.raw().setHeader("Content-Disposition","attachment; filename=lista_priotti_"+date+".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Lista de Precios");
        try {
            int rowNum = 0;
            List<Item> list = itemDao.read();
            for (Item item : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getCodigo());
                row.createCell(1).setCellValue(item.getMarca());
                row.createCell(2).setCellValue(item.getRubro());
                row.createCell(3).setCellValue(item.getAplicacion());
                row.createCell(4).setCellValue(new Double(item.getPrecioLista().multiply(coeficient).toString()));
            }

            workbook.write(response.raw().getOutputStream());
            workbook.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Xlsx file created!");
        return response.raw();
    }



}