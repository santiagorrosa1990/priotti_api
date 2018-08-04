package com.santiago.priotti_api.Cart;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.santiago.priotti_api.Item.ItemDao;
import com.santiago.priotti_api.Item.ItemTranslator;
import com.santiago.priotti_api.StandardResponse.StandardResponse;
import com.santiago.priotti_api.StandardResponse.StatusResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class CartService {

    private final CartDao cartDao;
    private final ItemTranslator translator;

    @Inject
    public CartService(CartDao cartDao, ItemTranslator translator) {
        this.cartDao = cartDao;
        this.translator = translator;
    }

    public String cartAddOrRemove(CartRequest request) {
        try {
            if (request.isErasure()) return cartDao.removeFromCart(request);
            return cartDao.addToCart(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getOrder(CartRequest cartRequest) {
        try {
            return cartDao.getOrder(cartRequest);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public String getOrderHistory(CartRequest cartRequest){
        try {
            return new Gson().toJson(cartDao.getOrderHistory(cartRequest));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, "Error: " + ex.getMessage()));
        }
    }

    public Boolean sendOrderEmail(CartRequest cartRequest) {
        try {
            List<List<String>> list = cartDao.closeOrder(cartRequest);
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

}
