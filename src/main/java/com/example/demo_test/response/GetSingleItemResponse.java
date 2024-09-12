package com.example.demo_test.response;

import com.dts.tsdc.common.domain.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetSingleItemResponse<T> extends BaseResponse {
    private T item;

    public GetSingleItemResponse() {
    }

    public void setSuccess(T item) {
        super.setSuccess();
        this.item = item;
    }

    public void setResult(T item) {
        setResult(item, "item not found");
    }

    private void setResult(T item, String errorMessage) {
        if (item == null) {
            super.setItemNotFound(errorMessage);
        } else {
            super.setSuccess();
            this.item = item;
        }
    }

}