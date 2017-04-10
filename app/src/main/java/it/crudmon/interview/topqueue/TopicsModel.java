package it.crudmon.interview.topqueue;

// Modelclass for questions_card to inflate data in cards (ViewHolder)
public class TopicsModel {

    private String title;
    private  String subtitle;
    private String file_url;

    public TopicsModel(String title,String subtitle,String file_url) {
        this.title = title;
        this.subtitle= subtitle;
        this.file_url=file_url;
    }
    public TopicsModel(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}