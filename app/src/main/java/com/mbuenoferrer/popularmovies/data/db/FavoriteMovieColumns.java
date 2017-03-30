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
}
