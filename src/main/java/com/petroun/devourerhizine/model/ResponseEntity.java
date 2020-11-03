package com.petroun.devourerhizine.model;

import lombok.Data;

@Data
public class ResponseEntity {
    private String responseId;
    private String money;
    private String requestFlow;
    private String responseFlow;
    private String copartnerId;
    private String description;
    private String code;
    private String card;
    private String taxbal;
    private String time;
    private String balance;
}
