package com.grepp.smartwatcha.app.model.details.service.jpaservice;

import com.grepp.smartwatcha.app.model.details.dto.jpadto.RatingBarDto;
import com.grepp.smartwatcha.app.model.details.dto.jpadto.RatingRequestDto;
import com.grepp.smartwatcha.infra.jpa.entity.MovieEntity;
import com.grepp.smartwatcha.infra.jpa.entity.RatingEntity;
import com.grepp.smartwatcha.infra.jpa.entity.UserEntity;
import com.grepp.smartwatcha.app.model.details.repository.jparepository.MovieDetailsJpaRepository;
import com.grepp.smartwatcha.app.model.details.repository.jparepository.RatingJpaRepository;
import com.grepp.smartwatcha.app.model.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "jpaTransactionManager")
public class RatingJpaService {

    private final RatingJpaRepository ratingJpaRepository;
    private final MovieDetailsJpaRepository movieDetailsJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public void addRating(RatingRequestDto dto) {
        MovieEntity movie = movieDetailsJpaRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("영화를 찾을 수 없습니다."));
        UserEntity user = userJpaRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Optional<RatingEntity> optionalRating = ratingJpaRepository.findByUserAndMovie(user, movie);
        RatingEntity rating = optionalRating.orElseGet(RatingEntity::new);
        rating.setUser(user);
        rating.setMovie(movie);
        rating.setScore(dto.getScore());

        if (rating.getCreatedAt() == null) {
            rating.setCreatedAt(LocalDateTime.now());
        }
        ratingJpaRepository.save(rating);
    }

    public Map<Integer, Integer> getRatingDistribution(Long movieId) {
        List<Object[]> results = ratingJpaRepository.countRatingsByScore(movieId);
        Map<Integer, Integer> distribution = new HashMap<>();

        for (Object[] row : results) {
            Double score = (Double) row[0];
            Long count = (Long) row[1];
            distribution.put(score.intValue(), count.intValue());
        }

        // 누락된 점수는 0으로 채우기
        for (int i = 1; i <= 5; i++) {
            distribution.putIfAbsent(i, 0);
        }

        return distribution;
    }

    public List<RatingBarDto> getRatingDistributionList(Long movieId) {
        Map<Integer, Integer> rawMap = getRatingDistribution(movieId);
        List<RatingBarDto> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            list.add(new RatingBarDto(i, rawMap.get(i)));
        }
        return list;
    }
    public double getAverageRating(Long movieId) {
        Double avg = ratingJpaRepository.getAverageRating(movieId);
        return avg != null ? avg : 0.0;
    }

    public Integer getUserRating(Long userId, Long movieId) {
        return ratingJpaRepository.findRatingByUserAndMovie(userId, movieId)
                .map(r -> r.getScore() != null ? r.getScore().intValue() : null)
                .orElse(null);
    }

    public void deleteRatingByUser(Long userId, Long movieId) {
        ratingJpaRepository.deleteByUserIdAndMovieId(userId, movieId);
    }


}
