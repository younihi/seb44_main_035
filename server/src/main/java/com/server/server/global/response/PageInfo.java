package com.server.server.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long totalBoards;
    private int totalPages;
}

