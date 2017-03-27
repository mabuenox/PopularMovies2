package com.mbuenoferrer.popularmovies.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MovieListSort {
    public static final int POPULAR = 0;
    public static final int TOP_RATED = 1;
    public static final int FAVORITES = 2;

    public final int itemType;

    // Describes when the annotation will be discarded
    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @IntDef({ POPULAR, TOP_RATED, FAVORITES })
    // Create an interface for validating int types
    public @interface ItemTypeDef { }
    // Mark the argument as restricted to these enumerated types
    public MovieListSort(@ItemTypeDef int itemType) {
        this.itemType = itemType;
    }

}
