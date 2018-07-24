/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.santiago.priotti_api.Item;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Cart.CartRequest;
import com.santiago.priotti_api.Interfaces.Translator;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ItemService {

    private final ItemDao itemDao;
    private final Translator<Item, ItemRequest> translator; // TODO ver si mandar el translator al controller

    @Inject
    public ItemService(ItemDao itemDao, Translator<Item, ItemRequest> translator) {
        this.itemDao = itemDao;
        this.translator = translator;
    }

    public void updateAll(String body) {
        List<Item> itemsList = translator.translateList(body);
        try {
            itemDao.update(itemsList);
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
                    item.getInfo(),
                    "$" + item.getPrecioLista().toString(), //TODO sacar el $ de aca
                    "$" + item.getPrecioOferta().toString(),
                    item.getImagen()))
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

    public String sendOrderEmail(CartRequest cartRequest) {
        try {
            List<List<String>> list = itemDao.closeOrder(cartRequest);
            sendMail(buildEmailBody(list, cartRequest.getComments()), cartRequest);
            return "Sent!";
        } catch (SQLException ex) {
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
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

        System.out.println(sb.toString());


        return sb.toString();

    }

    private void sendMail(String body, CartRequest request) {
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

            System.out.println("Unable to send an email" + mex);

        }

    }
}