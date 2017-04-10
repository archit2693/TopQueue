package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hot {
    @SerializedName("recent")
    public List<recentList> recentList;
    @SerializedName("popular")
    public List<popularList> popularList;
}
