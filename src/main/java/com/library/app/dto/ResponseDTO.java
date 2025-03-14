package com.library.app.dto;

/*
 * Author: Francesca Murano
 */

import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseDTO<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    public ResponseDTO(boolean success, String message, T data, Integer pageNumber, Integer pageSize, Long totalElements, Integer totalPage) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }

    public ResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseDTO(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPage=" + totalPage +
                '}';
    }
}
