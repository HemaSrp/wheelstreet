package com.wheelstreet.wheelstreet.model;

public class QuestionDatabase {

    private String questionId;
    private String question;
    private String dataType;
    private String answerId;
    private String questionDisplay;
    private String answerDisplay;
    private String position;

    public String getQuestionDisplay() {
        return questionDisplay;
    }

    public void setQuestionDisplay(String questionDisplay) {
        this.questionDisplay = questionDisplay;
    }

    public String getAnswerDisplay() {
        return answerDisplay;
    }

    public void setAnswerDisplay(String answerDisplay) {
        this.answerDisplay = answerDisplay;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public void setPosition(String position) {
        this.position=position;

    }

    public String getPosition() {
        return position;
    }
}
