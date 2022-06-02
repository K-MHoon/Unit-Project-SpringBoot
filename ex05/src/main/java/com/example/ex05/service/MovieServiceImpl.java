package com.example.ex05.service;

import com.example.ex05.dto.MovieDTO;
import com.example.ex05.entity.movie.Movie;
import com.example.ex05.entity.movie.MovieImage;
import com.example.ex05.repository.MovieImageRepository;
import com.example.ex05.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    @Transactional
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> imgList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);
        movieImageRepository.saveAll(imgList);

        return movie.getMno();
    }
}
