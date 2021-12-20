package id.roogry.cinemas.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import id.roogry.cinemas.R;
import id.roogry.cinemas.databinding.ActivityMainBinding;
import id.roogry.cinemas.helper.ViewModelFactory;
import id.roogry.cinemas.ui.form.MovieAddUpdateActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MovieAdapter movieAdapter = new MovieAdapter();
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMovies.setHasFixedSize(true);
        binding.rvMovies.setAdapter(movieAdapter);

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        mainViewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        mainViewModel.getMovies().observe(this, movies -> {
            if (movies != null){
                movieAdapter.setListMovies(movies);
                binding.emptyHolder.setVisibility(View.INVISIBLE);
            }else{
                movieAdapter.clearData();
                binding.emptyHolder.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.isConneted().observe(this, isConnected -> {
            if (!isConnected){
                mainViewModel.getLocalMovies().observe(this, movies -> {
                    if (movies != null){
                        movieAdapter.setListMovies(movies);
                        binding.emptyHolder.setVisibility(View.INVISIBLE);
                    }else{
                        movieAdapter.clearData();
                        binding.emptyHolder.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        mainViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading){
                binding.srlMovies.setRefreshing(true);
            }else{
                binding.srlMovies.setRefreshing(false);
            }
        });

        binding.srlMovies.setOnRefreshListener(() -> {
            mainViewModel.getAllMovies();
        });

        binding.fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MovieAddUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.getAllMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}