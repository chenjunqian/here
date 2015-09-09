package com.eason.here.HttpUtil;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Use for handle response from server and cast response to the model
 *
 * Created by Eason on 7/25/15.
 */
public class HttpResponseHandler implements IObserverHandler {

    private Gson gson;
    public Object result;
    public ResultVO resultVO;

    @Override
    public <T> void response(String response, Class<T> t) {

        gson = new Gson();

        try {
            resultVO = gson.fromJson(response,ResultVO.class);
            if (resultVO!=null&&resultVO.getStatus()==0){
                JSONObject responseJson = new JSONObject(response);
                JSONObject resultData;
                if (resultVO.getResultData()!=null&&t!=null){
                    resultData = (JSONObject) responseJson.get("resultData");
                    if (resultData==null)return;
                    result = gson.fromJson(resultData.toString(),t);
                    resultVO.setResultData(result);
                }
            }
        }catch (Exception e){

        }

        getResult();
    }

    public void getResult(){

    };
}
