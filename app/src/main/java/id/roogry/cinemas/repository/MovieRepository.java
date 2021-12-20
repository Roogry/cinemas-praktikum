package id.roogry.cinemas.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.roogry.cinemas.source.local.MovieDao;
import id.roogry.cinemas.source.local.MovieRoomDatabase;
import id.roogry.cinemas.source.entity.Movie;

public class MovieRepository {
    private final MovieDao mMoviesDao;
    private final ExecutorService executorService;

    public MovieRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMoviesDao = db.movieDao();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return mMoviesDao.getAllMovies();
    }

    public void insertAll(final List<Movie> movies) {
        executorService.execute(() -> mMoviesDao.insert(movies));
    }

    public void deleteAll(){
        executorService.execute(mMoviesDao::deleteAll);
    }
}
