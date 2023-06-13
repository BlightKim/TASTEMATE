package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("AmountVO")
public class AmountVO {

    private Integer total, tax_free, vat, point, discount;
}
