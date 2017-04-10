package it.crudmon.interview.topqueue;

// Modelclass for questions_card to inflate data in cards (ViewHolder)
public class DataModel {
    String question;
    String answer;
    String code;
    Boolean isChecked=false;

    public DataModel(String answer, String code, String question) {
        this.answer = answer;
        this.code = code;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}