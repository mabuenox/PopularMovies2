package com.mbuenoferrer.popularmovies;

import com.mbuenoferrer.popularmovies.data.NetworkMovieRepository;
import com.mbuenoferrer.popularmovies.entities.Movie;
import com.mbuenoferrer.popularmovies.entities.Video;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test_api_call_movies() throws Exception {

        NetworkMovieRepository networkMovieRepository = new NetworkMovieRepository();
        List<Movie> movies = networkMovieRepository.getPopular();

        assertEquals(20, movies.size());
    }

    @Test
    public void test_api_call_videos() throws Exception {

        NetworkMovieRepository networkMovieRepository = new NetworkMovieRepository();
        List<Video> videos = networkMovieRepository.getVideos(127380);

        assertTrue(videos.size() > 0);
    }
}
