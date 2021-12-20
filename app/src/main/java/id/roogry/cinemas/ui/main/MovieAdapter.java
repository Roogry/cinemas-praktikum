package id.roogry.cinemas.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.cinemas.databinding.ItemMovieBinding;
import id.roogry.cinemas.source.entity.Movie;
import id.roogry.cinemas.helper.MovieDiffCallback;
import id.roogry.cinemas.ui.detail.MovieDetailActivity;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final ArrayList<Movie> listMovies = new ArrayList<>();

    void setListMovies(List<Movie> listMovies) {
        final MovieDiffCallback diffCallback = new MovieDiffCallback(this.listMovies, listMovies);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listMovies.clear();
        this.listMovies.addAll(listMovies);
        diffResult.dispatchUpdatesTo(this);
    }

    void clearData(){
        final MovieDiffCallback diffCallback = new MovieDiffCallback(this.listMovies, listMovies);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.listMovies.clear();
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.bind(listMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        final ItemMovieBinding binding;

        MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            binding.tvTitle.setText(movie.getTitle());
            binding.tvSubtitle.setText(String.format("%s | %s menit | %s", movie.getAgeRate(), movie.getDuration(), movie.getGenre()));
            binding.tvRating.setText(String.valueOf(movie.getRating()));

            binding.cvMovie.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                v.getContext().startActivity(intent);
            });
        }
    }
}
