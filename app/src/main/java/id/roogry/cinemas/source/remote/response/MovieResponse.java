package id.roogry.cinemas.source.remote.response;

import com.google.gson.annotations.SerializedName;

import id.roogry.cinemas.source.entity.Movie;

public class MovieResponse{

	@SerializedName("movie")
	private Movie movie;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setMovie(Movie movie){
		this.movie = movie;
	}

	public Movie getMovie(){
		return movie;
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