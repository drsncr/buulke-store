package com.buulke.buulkestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question implements Serializable {
    private String text;
    private List<String> options;
    private String rightOption;
}
