package id.roogry.cinemas.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.roogry.cinemas.source.entity.Movie;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Movie> Movie);

//    @Update()
//    void update(Movie Movie);

//    @Delete()
//    void delete(Movie Movie);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM movies")
    void deleteAll();
}
