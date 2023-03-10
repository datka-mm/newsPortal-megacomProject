package com.example.newsportalmegacomproject.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;


@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(generator = "favorite_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "favorite_gen", sequenceName = "favorite_seq", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = {DETACH, REFRESH, MERGE})
    private News news;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private User user;

    public Favorite(News news, User user) {
        this.news = news;
        this.user = user;
    }
}
