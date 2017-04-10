package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class popularList {
  //@SerializedName("")
    @SerializedName("questions")
    public List<questionList> questionlist;
    public String field_id;
    public String description;
    public String topic_name;
    public String logo;
    public String media;
    public String file_url;
}
