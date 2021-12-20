package id.roogry.cinemas.source.remote;

import id.roogry.cinemas.source.remote.response.MovieListResponse;
import id.roogry.cinemas.source.remote.response.MovieResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface  ApiService {
    @GET("movies")
    Call<MovieListResponse> getMovies();

    @FormUrlEncoded
    @POST("movies")
    Call<MovieResponse> insertMovie(
        @Field("title") String title,
        @Field("overview") String overview,
        @Field("duration") int duration,
        @Field("age_rate") String ageRate,
        @Field("genre") String genre,
        @Field("rating") float rating
    );

    @FormUrlEncoded
    @PUT("movies/{id}")
    Call<MovieResponse> updateMovie(
        @Path("id") String id,
        @Field("title") String title,
        @Field("overview") String overview,
        @Field("duration") int duration,
        @Field("age_rate") String ageRate,
        @Field("genre") String genre,
        @Field("rating") float rating
    );

    @DELETE("movies/{id}")
    Call<MovieResponse> delMovieById(@Path("id") String id);
}
