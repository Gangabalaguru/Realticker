package com.realticker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String ticker;
    private String company;
    private double price;
    private double changePercent;
    private long volume;
}