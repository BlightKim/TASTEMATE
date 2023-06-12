package com.tastemate.domain;

import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class TokenVO {

    private String code;
    private String messege;
    private JSONObject response = new JSONObject();


}
