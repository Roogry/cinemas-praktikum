package id.roogry.cinemas.ui.detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import id.roogry.cinemas.R;
import id.roogry.cinemas.databinding.ActivityMovieDetailBinding;
import id.roogry.cinemas.databinding.DialogConfirmDeleteBinding;
import id.roogry.cinemas.databinding.DialogConfirmSaveBinding;
import id.roogry.cinemas.helper.DialogLoading;
import id.roogry.cinemas.helper.ViewModelFactory;
import id.roogry.cinemas.source.entity.Movie;
import id.roogry.cinemas.ui.form.MovieAddUpdateActivity;
import id.roogry.cinemas.ui.form.MovieAddUpdateViewModel;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private ActivityMovieDetailBinding binding;
    private MovieDetailViewModel movieDetailViewModel;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        movieDetailViewModel = new ViewModelProvider(this, factory).get(MovieDetailViewModel.class);

        if (movie != null){
            binding.tvName.setText(movie.getTitle());
            binding.tvOverview.setText(movie.getOverview());
            binding.tvDuration.setText(String.format("%d menit", movie.getDuration()));
            binding.tvGenre.setText(movie.getGenre());
            binding.tvAgeRate.setText(movie.getAgeRate());
            binding.tvRating.setText(String.valueOf(movie.getRating()));
        }

        binding.ivBack.setOnClickListener(view -> onBackPressed());
        binding.ivMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(MovieDetailActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.update_delete, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        deleteConfirmDialog();
                        break;
                    case R.id.edit :
                        Intent moveAddUpdateIntent = new Intent(MovieDetailActivity.this, MovieAddUpdateActivity.class);
                        moveAddUpdateIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                        startActivity(moveAddUpdateIntent);
                        finish();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

        DialogLoading loading = new DialogLoading(this);
        movieDetailViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) loading.showLoading();
            else loading.hideLoading();
        });

        movieDetailViewModel.getMessage().observe(this, message -> {
            if (message != null) {
                showNextPageDialog(message);
            }
        });
    }

    private void showNextPageDialog(String message) {
        AlertDialog builder = new AlertDialog.Builder(this, R.style.DialogSecondary)
                .create();
        DialogConfirmSaveBinding dialogBinding = DialogConfirmSaveBinding
                .inflate(LayoutInflater.from(this));

        builder.setView(dialogBinding.getRoot());
        builder.setCanceledOnTouchOutside(false);

        dialogBinding.txtTitle.setText(message);
        dialogBinding.txtSubtitle.setVisibility(View.GONE);
        dialogBinding.btnSave.setVisibility(View.GONE);
        dialogBinding.btnCancel.setText("Halaman Utama");

        dialogBinding.btnCancel.setOnClickListener(view -> finish());

        builder.show();
    }

    private void deleteConfirmDialog() {
        AlertDialog builder = new AlertDialog.Builder(this, R.style.DialogSecondary)
                .create();
        DialogConfirmDeleteBinding dialogBinding = DialogConfirmDeleteBinding
                .inflate(LayoutInflater.from(this));

        builder.setView(dialogBinding.getRoot());
        builder.setCanceledOnTouchOutside(false);

        dialogBinding.txtTitle.setText("Hapus Film");
        dialogBinding.txtSubtitle.setText(String.format("%s berkategori usia %s dengan rating sebesar %s/10 akan dihapus", movie.getTitle(), movie.getAgeRate(), movie.getRating()));
        dialogBinding.txtSubtitle.setText(String.format("%s. Apakah yakin ingin menghapus film ini?", dialogBinding.txtSubtitle.getText()));

        dialogBinding.btnCancel.setOnClickListener(view -> builder.dismiss());
        dialogBinding.btnHapus.setOnClickListener(view -> {
            movieDetailViewModel.delete(movie.getId());
            builder.dismiss();
        });

        builder.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("Selamat datang kembali");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("Berikut data film-nya");
    }

    @Override
    protected void onPause() {
        super.onPause();
        showToast("Kembali lagi nanti yaa");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("Sampai Jumpa");
        binding = null;
    }
}