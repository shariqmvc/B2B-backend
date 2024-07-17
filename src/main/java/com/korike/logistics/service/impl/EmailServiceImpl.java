package com.korike.logistics.service.impl;

import com.korike.logistics.controller.user.CommissionController;
import com.korike.logistics.service.EmailService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class EmailServiceImpl implements EmailService {
    private static Logger logger = Logger.getLogger(EmailServiceImpl.class);
    @Autowired
    RestTemplateFactoryImpl restTemplate;
    @Value("${security.mail.key}")
    private String mailChimpKey;

    public static void main(String[] args) {
        EmailServiceImpl em = new EmailServiceImpl();
        em.addEmailtoAllowList("sshariq30@gmail.com");
    }
    @Override
    public void addEmailtoAllowList(String emailAddress) {
        String apiUrl = "https://mandrillapp.com/api/1.0/allowlists/add";

        // Create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        String requestBody = "{\"key\":\""+mailChimpKey+"\",\"email\":\""+emailAddress+"\",\"comment\":\"\"}";

        // Create the HttpEntity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Get the response body
        String responseBody = responseEntity.getBody();

        // Print the response
        logger.info("Response: " + responseBody);
    }

    @Override
    public void sendOtpToEmail(String email, String otp) {
        {
            try {
                // Replace "YOUR_API_KEY" with your actual Mailchimp API key
               // String apiKey = "md-fI3GDKiBRINpXRPkS3lJoA";
                // Mailchimp Transactional API endpoint
                String apiUrl = "https://mandrillapp.com/api/1.0/messages/send.json";

                // Build your API request payload (message details)
                String requestBody = "{\"key\": \"" + mailChimpKey + "\", \"message\": {\n" +
                        "    \"subject\": \"Your Email Subject\",\n" +
                        "    \"html\": \"<p>OTP: "+otp+"</p>\",\n" +
                        "    \"from_email\": \"support@korikelogistics.com\",\n" +
                        "    \"to\": [\n" +
                        "      {\n" +
                        "        \"email\": \""+email+"\",\n" +
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
}
