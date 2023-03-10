package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.db.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsInnerResponsePage {

    private Long id;
    private String title;
    private String description;
    private String text;
    private LocalDate createdAt;
    private String imageCover;
    private Boolean isFavorite;
    private List<CommentResponse> commentResponses;

    public NewsInnerResponsePage(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.text = news.getText();
        this.createdAt = news.getCreatedAt();
        this.imageCover = news.getImageCover();
    }
}
