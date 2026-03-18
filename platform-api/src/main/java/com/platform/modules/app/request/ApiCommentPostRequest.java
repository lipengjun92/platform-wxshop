package com.platform.modules.app.request;

import lombok.Data;

import java.util.List;

@Data
public class ApiCommentPostRequest {
    private Integer typeId;
    private Integer valueId;
    private String content;
    private List<String> imagesList;
}
