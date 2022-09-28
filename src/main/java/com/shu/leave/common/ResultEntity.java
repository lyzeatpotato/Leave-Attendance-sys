package com.shu.leave.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity {

    private Integer code;
    private String message;
    private Object data;
}
