package com.books.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * POJO layer for book app
 * @author Neutralizer
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String summary;
    @NonNull
    private double rating;


}
