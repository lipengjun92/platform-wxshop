package com.platform.modules.app.request;

import lombok.Data;

@Data
public class ApiFeedbackSaveRequest {
    private String mobile;
    private Integer index;
    private String content;
}
