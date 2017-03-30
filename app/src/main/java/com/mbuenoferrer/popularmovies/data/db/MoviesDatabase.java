package com.mbuenoferrer.popularmovies.data.db;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = MoviesDatabase.VERSION,
        packageName = "com.mbuenoferrer.popularmovies.provider")
public final class MoviesDatabase {

    public static final int VERSION = 1;

    @Table(FavoriteMovieColumns.class)
    public static final String FAVORITE_MOVIES = "favorite_movies";
}

