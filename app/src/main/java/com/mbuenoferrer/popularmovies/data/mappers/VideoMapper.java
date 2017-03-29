package com.mbuenoferrer.popularmovies.data.mappers;

import com.mbuenoferrer.popularmovies.data.VideoListResult;
import com.mbuenoferrer.popularmovies.entities.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoMapper {
    public static List<Video> map(List<VideoListResult> results){
        List<Video> videos = new ArrayList<>();

        for(VideoListResult result : results)
        {
            Video video = new Video(result.getId(),
                    result.getName(),
                    result.getKey(),
                    result.getSite());

            videos.add(video);
        }

        return videos;
    }
}
