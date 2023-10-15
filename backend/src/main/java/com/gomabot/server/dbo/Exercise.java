package com.gomabot.server.dbo;

public class Exercise {
    int id;
    int idLesson;
    int idExam;
    String question;
    String correctAnswer;
    String incorrectAnswer1;
    String incorrectAnswer2;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getIdLesson() {
        return idLesson;
    }

    public void setIdLesson(final int idLesson) {
        this.idLesson = idLesson;
    }

    public int getIdExam() {
        return idExam;
    }

    public void setIdExam(final int idExam) {
        this.idExam = idExam;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(final String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIncorrectAnswer1() {
        return incorrectAnswer1;
    }

    public void setIncorrectAnswer1(final String incorrectAnswer1) {
        this.incorrectAnswer1 = incorrectAnswer1;
    }

    public String getIncorrectAnswer2() {
        return incorrectAnswer2;
    }

    public void setIncorrectAnswer2(final String incorrectAnswer2) {
        this.incorrectAnswer2 = incorrectAnswer2;
    }
}
