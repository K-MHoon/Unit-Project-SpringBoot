package com.example.ex05.repository;

import com.example.ex05.entity.movie.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
