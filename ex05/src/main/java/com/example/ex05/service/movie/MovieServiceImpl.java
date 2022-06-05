package com.example.ex05.service.movie;

import com.example.ex05.dto.MovieDTO;
import com.example.ex05.dto.PageRequestDTO;
import com.example.ex05.dto.PageResultDTO;
import com.example.ex05.entity.movie.Movie;
import com.example.ex05.entity.movie.MovieImage;
import com.example.ex05.repository.MovieImageRepository;
import com.example.ex05.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDTO> fn = (arr -> entitiesToDTO(
                (Movie) arr[0],
                (List<MovieImage>)(Arrays.asList((MovieImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {

        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        Movie movie = (Movie) result.get(0)[0]; // Movie 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2]; // 평균 평점
        Long reviewCnt = (Long) result.get(0)[3]; // 리뷰 개수

        return entitiesToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
