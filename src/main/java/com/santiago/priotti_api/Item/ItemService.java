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
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    private List<String> getKeywords(String search) {
        String[] words = search.split("\\s+");
        return Arrays.asList(words);
    }

    public String cartAddOrRemove(CartRequest request) {
        try {
            if (request.isErasure()) return itemDao.removeFromCart(request);
            return itemDao.addToCart(request);
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getOrder(CartRequest cartRequest) {
        try {
            return itemDao.getOrder(cartRequest);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getOrderHistory(CartRequest cartRequest){
        try {
            return new Gson().toJson(itemDao.getOrderHistory(cartRequest));
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public Boolean sendOrderEmail(CartRequest cartRequest) {
        try {
            List<List<String>> list = itemDao.closeOrder(cartRequest);
            sendMail(buildEmailBody(list, cartRequest.getComments()), cartRequest);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private String buildEmailBody(List<List<String>> list, String comments) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h3> Comentarios: "+comments+" </h3>");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("</head>");
        sb.append("<table style=\"width: 50%;\" border=\"6\">");
        sb.append("<th> Código </th>");
        sb.append("<th> Marca </th>");
        sb.append("<th> Precio </th>");
        for (List item : list) {
            sb.append("<tr>");
            sb.append("<td &nbsp; > " + item.get(0) + " </td>");
            sb.append("<td &nbsp;> " + item.get(1) + " </td>");
            sb.append("<td &nbsp;> " + item.get(2) + " </td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");

        return sb.toString();

    }

    private void sendMail(String body, CartRequest request) throws MessagingException {
        //Setting up configurations for the email connection to the Google SMTP server using TLS

        Properties props = new Properties();

        props.put("mail.smtp.host", "true");

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.auth", "true");

        //Establishing a session with required user details

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("santiagorrosa2@gmail.com", "Tato1990");

            }

        });

        try {

            //Creating a Message object to set the email content

            MimeMessage msg = new MimeMessage(session);

            //Storing the comma seperated values to email addresses

            String to = "santiagorrosa2@email.com";

            /*Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email

            addresses in an array of InternetAddress objects*/

            InternetAddress[] address = InternetAddress.parse(to, true);

            //Setting the recepients from the address variable

            msg.setRecipients(Message.RecipientType.TO, address);

            //String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());

            msg.setSubject("Pedido de ["+request.getName()+"]");

            msg.setSentDate(new Date());

           // msg.setText(body);

            msg.setContent(body, "text/html; charset=utf-8");

            msg.setHeader("XPriority", "1");

            Transport.send(msg);

            System.out.println("Mail has been sent successfully");

        } catch (MessagingException mex) {
            throw mex;
        }

    }

    //EXP

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