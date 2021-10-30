package com.flowerhop.movielibrary.data.remote

import com.flowerhop.movielibrary.data.dto.MovieDetailDto
import com.flowerhop.movielibrary.data.dto.MoviePageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    companion object {
        const val PATH_MOVIE_ID = "movie_id"
        const val PATH_CATEGORY = "category"
        const val PAGE = "page"
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val QUERY = "query"
        const val INCLUDE_ADULT = "include_adult"
        const val REGION = "region"
        const val YEAR = "year"
        const val PRIMARY_RELEASE_YEAR = "primary_release_year"
        const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
        const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"

        const val RELEASE_DATE_GTE = "release_date.gte"
        const val RELEASE_DATE_LTE = "release_date.lte"
        const val WITH_RELEASE_TYPE = "with_release_type"

        const val SORT_BY = "sort_by"
        const val CERTIFICATION_COUNTRY = "certification_country"
        const val CERTIFICATION = "certification"
        const val CERTIFICATION_LTE = "certification.lte"
        const val CERTIFICATION_GTE = "certification.gte"
        const val INCLUDE_VIDEO = "include_video"
        const val VOTE_COUNT_GTE = "vote_count.gte"
        const val VOTE_COUNT_LTE = "vote_count.lte"
        const val VOTE_AVERAGE_GTE = "vote_average.gte"
        const val VOTE_AVERAGE_LTE = "vote_average.lte"
        const val WITH_CAST = "with_cast"
        const val WITH_CREW = "with_crew"
        const val WITH_PEOPLE = "with_people"
        const val WITH_COMPANIES = "with_companies"
        const val WITH_GENRES = "with_genres"
        const val WITHOUT_GENRES = "without_genres"
        const val WITH_KEYWORDS = "with_keywords"
        const val WITHOUT_KEYWORDS = "without_keywords"
        const val WITH_RUNTIME_GTE = "with_runtime.gte"
        const val WITH_RUNTIME_LTE = "with_runtime.lte"
        const val WITH_ORIGINAL_LANGUAGE = "with_original_language"
        const val WITH_WATCH_PROVIDERS = "with_watch_providers"
        const val WATCH_REGION = "watch_region"
        const val WITH_WATCH_MONETIZATION_TYPES = "with_watch_monetization_types"
    }

    @GET("movie/{$PATH_MOVIE_ID}")
    suspend fun getMovieById(@Path(PATH_MOVIE_ID) id: Int,
                         @Query(API_KEY) apiKey: String, @Query(LANGUAGE) language: String = "en-US"): Response<MovieDetailDto>

    @GET("movie/{$PATH_CATEGORY}")
    suspend fun getCategoryList(@Path(PATH_CATEGORY) category: String,
                                @Query(API_KEY) apiKey: String,
                                @Query(PAGE) pageIndex: Int,
                                @Query(LANGUAGE) language: String = "en-US"): Response<MoviePageDto>

    @GET("search/movie")
    suspend fun searchMovies(@Query(API_KEY) apiKey: String,
                             @Query(LANGUAGE) language: String = "en-US",
                             @Query(QUERY) query: String,
                             @Query(PAGE) pageIndex: Int,
                             @Query(INCLUDE_ADULT) includeAdult:Boolean = true,
                             @Query(REGION) region: String? = null,
                             @Query(YEAR) year: Int? = null,
                             @Query(PRIMARY_RELEASE_YEAR) primaryReleaseYear: Int? = null): Response<MoviePageDto>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query(API_KEY) apiKey: String,
        @Query(LANGUAGE) language: String = "en-US",
        @Query(REGION) region: String? = null,
        @Query(SORT_BY) sortBy: SortBy = SortBy.POPULARITY_DESC,
        @Query(CERTIFICATION_COUNTRY) certificationCountry: String? = null,
        @Query(CERTIFICATION) certification: String? = null,
        @Query(CERTIFICATION_LTE) certificationLte: String? = null,
        @Query(CERTIFICATION_GTE) certificationGte: String? = null,
        @Query(INCLUDE_ADULT) includeAdult: Boolean? = null,
        @Query(INCLUDE_VIDEO) includeVideo: Boolean? = null,
        @Query(PAGE) pageIndex: Int = 1,
        @Query(PRIMARY_RELEASE_YEAR) primaryReleaseYear: Int? = null,
        @Query(PRIMARY_RELEASE_DATE_GTE) primaryReleaseDateGte: String? = null,
        @Query(PRIMARY_RELEASE_DATE_LTE) primaryReleaseDateLte: String? = null,
        @Query(RELEASE_DATE_GTE) releaseDateGte: String? = null,
        @Query(RELEASE_DATE_LTE) releaseDateLte: String? = null,
        @Query(WITH_RELEASE_TYPE) withReleaseType: Int? = null,
        @Query(YEAR) year: Int? = null,
        @Query(VOTE_COUNT_GTE) voteCountGte: Long? = null,
        @Query(VOTE_COUNT_LTE) voteCountLte: Long? = null,
        @Query(VOTE_AVERAGE_GTE) voteAverageGte: Float? = null,
        @Query(VOTE_AVERAGE_LTE) voteAverageLte: Float? = null,
        @Query(WITH_CAST) withCast: String? = null,
        @Query(WITH_CREW) withCrew: String? = null,
        @Query(WITH_PEOPLE) withPeople: String? = null,
        @Query(WITH_COMPANIES) withCompanies: String? = null,
        @Query(WITH_GENRES) withGenres: String? = null,
        @Query(WITHOUT_GENRES) withoutGenres: String? = null,
        @Query(WITH_KEYWORDS) withKeywords: String? = null,
        @Query(WITHOUT_KEYWORDS) withoutKeywords: String? = null,
        @Query(WITH_RUNTIME_GTE) withRuntimeGte: Int? = null,
        @Query(WITH_RUNTIME_LTE) withRuntimeLte: Int? = null,
        @Query(WITH_ORIGINAL_LANGUAGE) withOriginalLanguage: String? = null,
        @Query(WITH_WATCH_PROVIDERS) withWatchProviders: String? = null,
        @Query(WATCH_REGION) watchRegion: String? = null,
        @Query(WITH_WATCH_MONETIZATION_TYPES) withWatchMonetizationTypes: MonetizationType? = null,
    ): Response<MoviePageDto>
}

enum class SortBy(private val value: String) {
    POPULARITY_DESC("popularity.desc"),
    POPULARITY_ASC("popularity.asc"),
    RELEASE_DATE_DESC("release_date.desc"),
    RELEASE_DATE_ASC("release_date.asc"),
    REVENUE_DESC("revenue.desc"),
    REVENUE_ASC("revenue.asc"),
    PRIMARY_RELEASE_DATE_ASC("primary_release_date.asc"),
    PRIMARY_RELEASE_DATE_DESC("primary_release_date.desc"),
    ORIGINAL_TITLE_DESC("original_title.desc"),
    ORIGINAL_TITLE_ASC("original_title.asc"),
    VOTE_AVERAGE_DESC("vote_average.desc"),
    VOTE_AVERAGE_ASC("vote_average.asc"),
    VOTE_COUNT_DESC("vote_count.desc"),
    VOTE_COUNT_ASC("vote_count.asc");

    override fun toString():String = value
}

enum class MonetizationType(private val value: String) {
    FLATRATE("flatrate"),
    FREE("free"),
    ADS("ads"),
    RENT("rent"),
    BUY("buy"),;

    override fun toString(): String = value
}