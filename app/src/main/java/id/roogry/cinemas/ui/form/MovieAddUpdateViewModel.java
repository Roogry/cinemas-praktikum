package id.roogry.cinemas.ui.form;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import id.roogry.cinemas.source.entity.Movie;
import id.roogry.cinemas.repository.MovieRepository;
import id.roogry.cinemas.source.remote.ApiConfig;
import id.roogry.cinemas.source.remote.response.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieAddUpdateViewModel extends ViewModel {
    private static final String TAG = "MovieAddUpdateViewModel";
    private Application application;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    private final MutableLiveData<String> _message = new MutableLiveData<>();
    public LiveData<String> getMessage() {
        return _message;
    }

    public MovieAddUpdateViewModel(Application application) {
        this.application = application;
    }

    public final void insert(@NotNull Movie movie) {
        _isLoading.setValue(true);
        Call<MovieResponse> client = ApiConfig.getApiService().insertMovie(
                movie.getTitle(),
                movie.getOverview(),
                movie.getDuration(),
                movie.getAgeRate(),
                movie.getGenre(),
                movie.getRating()
        );

        client.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                _isLoading.setValue(false);
                _message.setValue("Film gagal ditambahkan");

                if (response.isSuccessful() && response.body() != null) {
                    _message.setValue(response.body().getMessage());
                    Log.d(TAG, "success: " + _message);
                } else {
                    if (response.body() != null) {
                        _message.setValue(response.body().getMessage());
                    }
                    Log.e(TAG, "onFailure: " + _message);
                }
            }
            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                _isLoading.setValue(false);
                _message.setValue("Connection failed");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public final void update(@NotNull Movie movie) {
        _isLoading.setValue(true);
        Call<MovieResponse> client = ApiConfig.getApiService().updateMovie(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview(),
                movie.getDuration(),
                movie.getAgeRate(),
                movie.getGenre(),
                movie.getRating()
        );

        client.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                _isLoading.setValue(false);
                _message.setValue("Film gagal diperbaharui");
                if (response.isSuccessful() && response.body() != null) {
                    _message.setValue(response.body().getMessage());
                    Log.d(TAG, "success: " + _message);
                } else {
                    if (response.body() != null) {
                        _message.setValue(response.body().getMessage());
                    }
                    Log.e(TAG, "onFailure: " + _message);
                }
            }
            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                _isLoading.setValue(false);
                _message.setValue("Connection failed");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
