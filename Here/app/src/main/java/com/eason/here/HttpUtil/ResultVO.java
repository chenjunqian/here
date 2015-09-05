package com.eason.here.HttpUtil;


/**
 * Created by Eason on 7/24/15.
 */
public class ResultVO {
    private Object resultData;
    private int status;
    private Object errorMessage;


    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResultData() {
        return resultData;
    }

    public int getStatus() {
        return status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }
}
