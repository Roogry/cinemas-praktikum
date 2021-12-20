package id.roogry.cinemas.helper;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import id.roogry.cinemas.source.entity.Movie;

public class MovieDiffCallback extends DiffUtil.Callback {
    private final List<Movie> mOldMovieList;
    private final List<Movie> mNewMovieList;

    public MovieDiffCallback(List<Movie> oldMovieList, List<Movie> newMovieList) {
        this.mOldMovieList = oldMovieList;
        this.mNewMovieList = newMovieList;
    }

    @Override
    public int getOldListSize() {
        return mOldMovieList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewMovieList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldMovieList.get(oldItemPosition).getId() == mNewMovieList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Movie oldEmployee = mOldMovieList.get(oldItemPosition);
        final Movie newEmployee = mNewMovieList.get(newItemPosition);
        return oldEmployee.getTitle().equals(newEmployee.getTitle()) &&
                oldEmployee.getOverview().equals(newEmployee.getOverview()) &&
                oldEmployee.getDuration() == newEmployee.getDuration() &&
                oldEmployee.getAgeRate().equals(newEmployee.getAgeRate()) &&
                oldEmployee.getGenre().equals(newEmployee.getGenre()) &&
                oldEmployee.getRating() == newEmployee.getRating();
    }
}
