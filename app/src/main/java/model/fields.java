package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class fields {
    @SerializedName("success")
    public String success;
    @SerializedName("message")
    public String message;
    @SerializedName("totalCount")
    public String totalcount;
    @SerializedName("data")
    public List<FieldsList> fieldsArray;


}
