package com.realticker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResponse {
    private String trend;
    private String risk;
    private String suggestion;
    private String disclaimer;
}