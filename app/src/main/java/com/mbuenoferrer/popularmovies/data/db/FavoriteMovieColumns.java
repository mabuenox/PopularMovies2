package com.mbuenoferrer.popularmovies.data.db;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface FavoriteMovieColumns {
    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(INTEGER) @NotNull
    String ID = "movie_id";

    @DataType(TEXT) @NotNull
    String TITLE = "title";

    @DataType(TEXT) @NotNull
    String POSTER = "poster";

    @DataType(TEXT) @NotNull
    String SYNOPSIS = "synopsis";

    @DataType(TEXT) @NotNull
    String RELEASE_DATE = "release_date";

    @DataType(REAL) @NotNull
    String USER_RATING = "user_rating";

    int MOVIE_ID_INDEX = 1;
    int TITLE_INDEX = 2;
    int POSTER_INDEX = 3;
    int SYNOPSIS_INDEX = 4;
    int RELEASE_DATE_INDEX = 5;
    int USER_RATING_INDEX = 6;
}
