package com.korike.logistics.controller.notifications;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EmailController {


    public static void main(String[] args) {
        try {
            // Replace "YOUR_API_KEY" with your actual Mailchimp API key
            String apiKey = "md-fI3GDKiBRINpXRPkS3lJoA";
            String otp =  "332322";
            // Mailchimp Transactional API endpoint
            String apiUrl = "https://mandrillapp.com/api/1.0/messages/send.json";

            // Build your API request payload (message details)
            String requestBody = "{\"key\": \"" + apiKey + "\", \"message\": {\n" +
                    "    \"subject\": \"Your Email Subject\",\n" +
                    "    \"html\": \"<p>OTP: "+otp+"</p>\",\n" +
                    "    \"from_email\": \"support@korikelogistics.com\",\n" +
                    "    \"to\": [\n" +
                    "      {\n" +
                    "        \"email\": \"shariq1926@outlook.com\",\n" +
                    "        \"name\": \"Shareq\",\n" +
                    "        \"type\": \"to\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }}";

            // Create an HTTP client
            HttpClient httpClient = HttpClients.createDefault();

            // Create an HTTP POST request
            HttpPost httpPost = new HttpPost(apiUrl);

            // Set the request body
            httpPost.setEntity(new StringEntity(requestBody));

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPost);

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseStringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStringBuilder.append(line);
            }

            // Print the response
            System.out.println(responseStringBuilder.toString());
        } catch (Exception e) {
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }
}


