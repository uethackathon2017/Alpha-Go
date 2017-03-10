package com.quang.tracnghiemtoan.models;

public class Problem {
    private String id;
    private String question;
    private String answer;
    private String level;
    private String rightAnswer;

    public Problem() {
    }

    public Problem(String id, String question, String answer, String level, String rightAnswer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.level = level;
        this.rightAnswer = rightAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
