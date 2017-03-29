package com.mbuenoferrer.popularmovies.data.mappers;

import com.mbuenoferrer.popularmovies.data.api.models.ReviewListResult;
import com.mbuenoferrer.popularmovies.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {
    public static List<Review> map(List<ReviewListResult> results){
        List<Review> reviews = new ArrayList<>();

        for(ReviewListResult result : results)
        {
            Review review = new Review(result.getId(),
                    result.getAuthor(),
                    result.getContent(),
                    result.getUrl());

            reviews.add(review);
        }

        return reviews;
    }
}
