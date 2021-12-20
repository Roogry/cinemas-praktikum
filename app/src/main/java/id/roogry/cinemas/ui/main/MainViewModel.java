package id.roogry.cinemas.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import id.roogry.cinemas.source.entity.Movie;
import id.roogry.cinemas.repository.MovieRepository;
import id.roogry.cinemas.source.remote.ApiConfig;
import id.roogry.cinemas.source.remote.response.MovieListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private final MovieRepository movieRepository;

    private final MutableLiveData<List<Movie>> _listMovie = new MutableLiveData<>();
    public LiveData<List<Movie>> getMovies() {
        return _listMovie;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final MutableLiveData<Boolean> _isConneted = new MutableLiveData<>();
    public LiveData<Boolean> isConneted() {
        return _isConneted;
    }

    public MainViewModel(Application application) {
        this.movieRepository = new MovieRepository(application);
        getAllMovies();
    }

    public LiveData<List<Movie>> getLocalMovies(){
        return movieRepository.getAllMovies();
    }

    public final void getAllMovies() {
        _isConneted.setValue(true);
        _isLoading.setValue(true);

        Call<MovieListResponse> client = ApiConfig.getApiService().getMovies();
        client.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieListResponse> call, @NotNull Response<MovieListResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        _listMovie.setValue(response.body().getMovies());
                        movieRepository.deleteAll();
                        movieRepository.insertAll(_listMovie.getValue());
                    }
                } else {
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure atas: " + response.body().getMessage());
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<MovieListResponse> call, @NotNull Throwable t) {
                _isConneted.setValue(false);
                _isLoading.setValue(false);
                Log.e(TAG, "onFailure bawah: " + t.getMessage());
            }
        });
    }
}