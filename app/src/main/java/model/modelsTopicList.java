package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class modelsTopicList {

    @SerializedName("questions")
    public List<questionList> questionlist;

    public String topic_id;
    public String topic_name;
    public String description;
    public String logo;
    public String media;
    public String field_id;
    public String field_name;
    public String file_url;

}
