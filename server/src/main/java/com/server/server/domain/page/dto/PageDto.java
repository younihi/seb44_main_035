package com.server.server.domain.page.dto;

public class PageDto {
    private int page;
    private int size;
    private int totalPages;

    public PageDto(int page, int size, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }
}
