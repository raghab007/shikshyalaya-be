package com.e_learning.Sikshyalaya.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transaction {
     private  String txnId;
     private  long amount;
     private long total_amount;
     private  String status;
     private  String mobile;
     private  String tidx;
     private  String purchase_order_id;
     private  String purchase_order_name;
     private  String transaction_id;
}
