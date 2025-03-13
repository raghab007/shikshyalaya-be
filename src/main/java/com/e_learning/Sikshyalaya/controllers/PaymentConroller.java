package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.PaymentRequest;
import com.e_learning.Sikshyalaya.entities.Course;
import com.e_learning.Sikshyalaya.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class PaymentConroller {

    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    CourseService courseService;

    @GetMapping("/payment/{courseId}")
    public ResponseEntity<?> initiatePayment(@PathVariable Integer courseId) {
        // URL to the API endpoint
        System.out.println("Course course course+"+courseId);
        String url = "https://dev.khalti.com/api/v2/epayment/initiate/";

        Course byId = courseService.findById(courseId);
        // Create the PaymentRequest object and set values
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setReturnUrl("http://localhost:5173/payment");
        paymentRequest.setWebsiteUrl("https://facebook.com/");
        paymentRequest.setAmount(String.valueOf(byId.getCoursePrice()*100));
        paymentRequest.setPurchaseOrderId(courseId.toString());
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


