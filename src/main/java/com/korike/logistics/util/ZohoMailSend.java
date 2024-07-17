package com.korike.logistics.util;

import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Reference to use templates
//https://www.zoho.com/zeptomail/help/api/email-templates.html

public class ZohoMailSend {
    public static String sendMail() throws Exception {
        String postUrl = "https://api.zeptomail.in/v1.1/email";
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String output = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(postUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r0ME+nuiGR58RZT5fG7FM6sZ4woqOI1LgQV5otKWKQFF00A+owoxjTkqB98B/JBQP6YmY094++e4L+HITy4YDxOD2qyqK3sx/VYSPOZsbq6x00at1UTcU3YUo/qddBt0iHTu92X");
            JSONObject object = new JSONObject("{\n" + "\"from\": { \"address\": \"no-reply@korikelogistics.com\"},\n" + "\"to\": [{\"email_address\": {\"address\": \"vinsome@gmail.com\",\"name\": \"vinsome\"}}],\n" + "\"subject\":\"Test Email\",\n" + "\"htmlbody\":\"<div><b> Test email sent successfully.  </b></div>\"\n" + "}");
            OutputStream os = conn.getOutputStream();
            os.write(object.toString().getBytes());
            os.flush();
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String mailSendResult = ZohoMailSend.sendMail();
    }
}
