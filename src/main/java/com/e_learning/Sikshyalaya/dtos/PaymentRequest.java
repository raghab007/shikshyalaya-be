package com.e_learning.Sikshyalaya.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("website_url")
    private String websiteUrl;

    private String amount;

    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @JsonProperty("purchase_order_name")
    private String purchaseOrderName;

    @JsonProperty("customer_info")
    private CustomerInfo customerInfo;

    // Getters and Setters
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderName() {
        return purchaseOrderName;
    }

    public void setPurchaseOrderName(String purchaseOrderName) {
        this.purchaseOrderName = purchaseOrderName;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    // Inner class for CustomerInfo
    public static class CustomerInfo {

        private String name;
        private String email;
        private String phone;
        private String token;

        // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setoken(String token) {
            this.token = token;
        }

        public String getToken() {
            return this.token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
