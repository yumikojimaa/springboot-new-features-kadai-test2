package com.example.samuraitravel.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ReviewForm {
    @NotNull
    private Long innId;

    @NotBlank
    private String userName;

    private String comment;

    @Min(value = 1, message = "スコアは1以上である必要があります。")
    @Max(value = 5, message = "スコアは5以下である必要があります。")
    private int score;
 // Getter and Setter methods
    public Long getInnId() {
        return innId;
    }

    public void setInnId(Long innId) {
        this.innId = innId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
    


