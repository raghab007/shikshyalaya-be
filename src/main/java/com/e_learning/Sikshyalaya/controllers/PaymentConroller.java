package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping
public class PaymentConroller {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/payment")
    public ResponseEntity<?> initiatePayment() {
        // URL to the API endpoint
        String url = "https://dev.khalti.com/api/v2/epayment/initiate/";

        // Create the PaymentRequest object and set values
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setReturnUrl("http://localhost:5173/enrolled");
        paymentRequest.setWebsiteUrl("https://facebook.com/");
        paymentRequest.setAmount("300000");
        paymentRequest.setPurchaseOrderId("Order01");
        paymentRequest.setPurchaseOrderName("test");

        PaymentRequest.CustomerInfo customerInfo = new PaymentRequest.CustomerInfo();
        customerInfo.setName("Raghab Pokhrel");
        customerInfo.setEmail("test@khalti.com");
        customerInfo.setPhone("9800000001");

        paymentRequest.setCustomerInfo(customerInfo);

        // Set the request headers
     HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization","key ced20f08671f45f7aa2cbf2981a9d618");
        //

        // Wrap the paymentRequest object in an HttpEntity
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

        // Send the POST request and capture the response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return the response body
        return response;
    }
}


