package id.roogry.cinemas.source.remote.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import id.roogry.cinemas.source.entity.Movie;

public class MovieListResponse{

	@SerializedName("movies")
	private List<Movie> movies;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setMovies(List<Movie> movies){
		this.movies = movies;
	}

	public List<Movie> getMovies(){
		return movies;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}