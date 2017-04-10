package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class recentandpopulars {
    @SerializedName("recent")
    public List<model.recentList> recentList;
    @SerializedName("popular")
    public List<model.popularList> popularList;
}
