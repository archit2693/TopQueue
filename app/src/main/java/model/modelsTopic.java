package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class modelsTopic {
    @SerializedName("success")
    public String success;
    @SerializedName("msg")
    public String message;
    @SerializedName("totalCount")
    public String totalCount;
    @SerializedName("data")
    public List<modelsTopicList> topicList;

}
