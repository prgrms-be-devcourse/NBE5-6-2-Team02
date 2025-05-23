package com.grepp.smartwatcha.app.model.details.service.jpaservice;

import com.grepp.smartwatcha.app.model.details.dto.jpadto.MovieDetailsDTO;
import com.grepp.smartwatcha.app.model.details.repository.jparepository.MovieDetailsJpaRepository;
import com.grepp.smartwatcha.infra.jpa.entity.MovieEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "jpaTransactionManager")
public class MovieJpaService {

    private final MovieDetailsJpaRepository movieRepository;

    public MovieDetailsDTO getMovieDetail(Long movieId) {
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));

        return new MovieDetailsDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseDate(),
                movie.getCountry(),
                movie.getOverview(),
                movie.getPoster(),
                movie.getCertification(),
                movie.getIsReleased()
        );
    }

    public Double getAverageScore(Long movieId) {
        Double avg = movieRepository.findAverageScore(movieId);
        return avg != null ? avg : 0.0;
    }
}
