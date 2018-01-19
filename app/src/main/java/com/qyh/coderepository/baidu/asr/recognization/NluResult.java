package com.qyh.coderepository.baidu.asr.recognization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 邱永恒
 * @time 2018/1/19  15:23
 * @desc 解析语义结果
 */

public class NluResult {
    private String origalJson;
    private String origalResult;
    private int appid;
    private int error;
    private String rawText;
    private String[] resultsRecognition;
    private String domain;
    private String intent;
    private String object;


    public static NluResult parseJson(String jsonStr) {
        NluResult result = new NluResult();
        result.setOrigalJson(jsonStr);
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONObject merged_res = json.optJSONObject("merged_res");
            JSONObject semantic_form = merged_res.optJSONObject("semantic_form");
            int appid = semantic_form.optInt("appid");
            int err_no = semantic_form.optInt("err_no");
            String raw_text = semantic_form.optString("raw_text");
            result.setAppid(appid);
            result.setError(err_no);
            result.setRawText(raw_text);

            JSONArray results = semantic_form.optJSONArray("results");
            if (results != null) {
                int size = results.length();
                String[] recogs = new String[size];
                for (int i = 0; i < size; i++) {
                    recogs[i] = results.getString(i);
                }
                result.setResultsRecognition(recogs);

                String string = results.getString(0);
                JSONObject resultObject = new JSONObject(string);
                String domain = resultObject.optString("domain");
                String intent = resultObject.optString("intent");
                String object = resultObject.optString("object");

                result.setDomain(domain);
                result.setIntent(intent);
                result.setObject(object);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getOrigalJson() {
        return origalJson;
    }

    public void setOrigalJson(String origalJson) {
        this.origalJson = origalJson;
    }

    public String getOrigalResult() {
        return origalResult;
    }

    public void setOrigalResult(String origalResult) {
        this.origalResult = origalResult;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String[] getResultsRecognition() {
        return resultsRecognition;
    }

    public void setResultsRecognition(String[] resultsRecognition) {
        this.resultsRecognition = resultsRecognition;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
