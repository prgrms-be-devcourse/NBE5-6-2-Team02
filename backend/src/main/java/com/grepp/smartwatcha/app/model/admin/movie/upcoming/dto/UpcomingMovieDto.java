package com.grepp.smartwatcha.app.model.admin.movie.upcoming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
 * 공개 예정작 영화 정보 DTO
 * TMDB API에서 조회한 영화 정보를 담는 객체
 * 
 * 주요 정보:
 * - 기본 정보 (제목, 개요, 포스터 등)
 * - 개봉 정보 (개봉일, 개봉 타입)
 * - 장르 정보
 * - 출연진 정보 (배우, 제작진)
 */
@Slf4j
@Data
@Builder
public class UpcomingMovieDto {

    /*
     * TMDB 영화 ID
     * TMDB API 에서 제공하는 고유 식별자
     */
    private Long id;

    /*
     * 영화 제목
     * 원어 제목 (original_title)
     */
    private String title;

    /*
     * 개봉일 문자열
     * TMDB API에서 제공하는 개봉일 (YYYY-MM-DD 형식)
     * 서비스 레이어에서 LocalDateTime으로 변환됨
     */
    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    private String overview;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("genre_ids")
    private List<Long> genreIds;

    @JsonProperty("release_type")
    private Integer releaseType;

    //credits API
    private String country;
    private String certification;

    //neo4j
    private List<String> actorNames = new ArrayList<>();
    private List<String> directorNames = new ArrayList<>();
    private List<String> writerNames = new ArrayList<>();

    /*
     * 개봉일 문자열 반환
     * 실제 날짜 변환은 서비스 레이어에서 처리
     * 
     * @return 개봉일 문자열 (YYYY-MM-DD 형식)
     */
    public String getReleaseDate() {
        return releaseDate;
    }


}
