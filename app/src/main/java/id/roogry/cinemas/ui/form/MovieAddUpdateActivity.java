package id.roogry.cinemas.ui.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.roogry.cinemas.R;
import id.roogry.cinemas.databinding.ActivityMovieAddUpdateBinding;
import id.roogry.cinemas.databinding.DialogConfirmSaveBinding;
import id.roogry.cinemas.helper.DialogLoading;
import id.roogry.cinemas.helper.ViewModelFactory;
import id.roogry.cinemas.source.entity.Movie;
import id.roogry.cinemas.ui.detail.MovieDetailActivity;

public class MovieAddUpdateActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private ActivityMovieAddUpdateBinding binding;
    private MovieAddUpdateViewModel movieAddUpdateViewModel;

    private Movie movie;
    private boolean isedit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieAddUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (movie != null) {
            setMovieData();
            isedit = true;
        } else {
            movie = new Movie();
        }

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        movieAddUpdateViewModel = new ViewModelProvider(this, factory).get(MovieAddUpdateViewModel.class);

        binding.ivBack.setOnClickListener(view -> onBackPressed());
        binding.btnSave.setOnClickListener(view -> saveMovie());
        binding.sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.tvRating.setText(String.valueOf(getRatingValue(i)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        DialogLoading loading = new DialogLoading(this);
        movieAddUpdateViewModel.isLoading().observe(this, isLoading -> {
            if (isLoading) {
                loading.showLoading();
            } else {
                loading.hideLoading();
            }
        });
        movieAddUpdateViewModel.getMessage().observe(this, message -> {
            if (message != null) {
                showNextPageDialog(message);
            }
        });
    }

    private void saveMovie() {
        resetInvalid();
        if (isValidatedForm()) {
            movie.setTitle(binding.edtTitle.getText().toString());
            movie.setOverview(binding.edtOverview.getText().toString());
            movie.setRating(getRatingValue(binding.sbRating.getProgress()));
            movie.setDuration(Integer.parseInt(binding.edtDuration.getText().toString()));

            int selectedRbid = binding.rgAgeRate.getCheckedRadioButtonId();
            RadioButton checkedRb = findViewById(selectedRbid);
            movie.setAgeRate(checkedRb.getText().toString());

            movie.setGenre(null);

            if (binding.cbFantasi.isChecked()) {
                if (movie.getGenre() == null)
                    movie.setGenre(binding.cbFantasi.getText().toString());
                else
                    movie.setGenre(movie.getGenre() + ", " + binding.cbFantasi.getText().toString());
            }
            if (binding.cbRomantis.isChecked()) {
                if (movie.getGenre() == null)
                    movie.setGenre(binding.cbRomantis.getText().toString());
                else
                    movie.setGenre(movie.getGenre() + ", " + binding.cbRomantis.getText().toString());
            }
            if (binding.cbKomedi.isChecked()) {
                if (movie.getGenre() == null) movie.setGenre(binding.cbKomedi.getText().toString());
                else
                    movie.setGenre(movie.getGenre() + ", " + binding.cbKomedi.getText().toString());
            }
            if (binding.cbAksi.isChecked()) {
                if (movie.getGenre() == null) movie.setGenre(binding.cbAksi.getText().toString());
                else movie.setGenre(movie.getGenre() + ", " + binding.cbAksi.getText().toString());
            }
            if (binding.cbPetualangan.isChecked()) {
                if (movie.getGenre() == null)
                    movie.setGenre(binding.cbPetualangan.getText().toString());
                else
                    movie.setGenre(movie.getGenre() + ", " + binding.cbPetualangan.getText().toString());
            }

            saveConfirmDialog();
        }
    }

    private void saveConfirmDialog() {
        AlertDialog builder = new AlertDialog.Builder(this, R.style.DialogSecondary)
                .create();
        DialogConfirmSaveBinding dialogBinding = DialogConfirmSaveBinding
                .inflate(LayoutInflater.from(this));

        builder.setView(dialogBinding.getRoot());
        builder.setCanceledOnTouchOutside(false);

        dialogBinding.txtSubtitle.setText(String.format("%s berkategori usia %s dengan rating sebesar %s/10", movie.getTitle(), movie.getAgeRate(), movie.getRating()));

        if (isedit) {
            dialogBinding.txtTitle.setText("Ubah Film");
            dialogBinding.txtSubtitle.setText(String.format("%s. Apakah yakin ingin mengubah film ini?", dialogBinding.txtSubtitle.getText()));
        } else {
            dialogBinding.txtTitle.setText("Film Baru");
            dialogBinding.txtSubtitle.setText(String.format("%s. Apakah yakin ingin menambahkan film ini?", dialogBinding.txtSubtitle.getText()));
        }

        dialogBinding.btnCancel.setOnClickListener(view -> builder.dismiss());
        dialogBinding.btnSave.setOnClickListener(view -> {
            if (isedit) {
                movieAddUpdateViewModel.update(movie);
            } else {
                movieAddUpdateViewModel.insert(movie);
            }
            builder.dismiss();
        });

        builder.show();
    }

    private void showNextPageDialog(String message){
        AlertDialog builder = new AlertDialog.Builder(this, R.style.DialogSecondary)
                .create();
        DialogConfirmSaveBinding dialogBinding = DialogConfirmSaveBinding
                .inflate(LayoutInflater.from(this));

        builder.setView(dialogBinding.getRoot());
        builder.setCanceledOnTouchOutside(false);

        dialogBinding.txtTitle.setText(message);
        dialogBinding.txtSubtitle.setVisibility(View.GONE);
        dialogBinding.btnSave.setText("Detail Film");
        dialogBinding.btnCancel.setText("Halaman Utama");

        if (message.equals("Connection failed")){
            dialogBinding.btnSave.setVisibility(View.GONE);
        }

        dialogBinding.btnSave.setOnClickListener(view -> {
            Intent moveDetailTheaterIntent = new Intent(MovieAddUpdateActivity.this, MovieDetailActivity.class);
            moveDetailTheaterIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
            startActivity(moveDetailTheaterIntent);

            finish();
        });
        dialogBinding.btnCancel.setOnClickListener(view -> finish());

        builder.show();
    }

    private boolean isValidatedForm() {
        boolean isValid = true;
        int selectedRbId = binding.rgAgeRate.getCheckedRadioButtonId();

        if (binding.edtTitle.getText().toString().trim().isEmpty()) {
            binding.edtTitle.setError(getString(R.string.err_title));
            binding.tvInvalidTitle.setVisibility(View.VISIBLE);
            binding.tvLabelTitle.setTextColor(ContextCompat.getColor(this, R.color.text_danger));
            binding.edtTitle.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_rounded_light_red
            ));
            isValid = false;
        }

        if (binding.edtOverview.getText().toString().trim().isEmpty()) {
            binding.edtOverview.setError(getString(R.string.err_overview));
            binding.tvInvalidOverview.setVisibility(View.VISIBLE);
            binding.tvLabelOverview.setTextColor(ContextCompat.getColor(this, R.color.text_danger));
            binding.edtOverview.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_rounded_light_red
            ));
            isValid = false;
        }

        if (binding.edtDuration.getText().toString().trim().isEmpty()) {
            binding.edtDuration.setError(getString(R.string.err_duration));
            binding.tvInvalidDuration.setVisibility(View.VISIBLE);
            binding.tvLabelDuration.setTextColor(ContextCompat.getColor(this, R.color.text_danger));
            binding.edtDuration.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_rounded_light_red
            ));
            isValid = false;
        }

        if (selectedRbId == -1) {
            binding.tvInvalidAgeRate.setVisibility(View.VISIBLE);
            binding.tvLabelAgeRate.setTextColor(ContextCompat.getColor(this, R.color.text_danger));
            isValid = false;
        }

        // its because max rating is 10. Then *2 to (get flaot 0,5)
        if (binding.sbRating.getProgress() < 1 || binding.sbRating.getProgress() > 20) {
            binding.tvInvalidRating.setVisibility(View.VISIBLE);
            binding.tvLabelRating.setTextColor(ContextCompat.getColor(
                    this,
                    R.color.text_danger
            ));
            binding.tvRating.setTextColor(ContextCompat.getColor(this, R.color.text_danger));
            isValid = false;
        }

        return isValid;
    }

    private float getRatingValue(int progress) {
        return (float) progress / 2;
    }

    private void resetInvalid() {
        binding.edtTitle.setError(null);
        binding.edtOverview.setError(null);
        binding.edtDuration.setError(null);

        binding.tvInvalidTitle.setVisibility(View.GONE);
        binding.tvInvalidOverview.setVisibility(View.GONE);
        binding.tvInvalidDuration.setVisibility(View.GONE);
        binding.tvInvalidAgeRate.setVisibility(View.GONE);
        binding.tvInvalidRating.setVisibility(View.GONE);

        binding.tvLabelTitle.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        binding.tvLabelOverview.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        binding.tvLabelDuration.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        binding.tvLabelAgeRate.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        binding.tvLabelRating.setTextColor(ContextCompat.getColor(this, R.color.text_primary));

        binding.edtTitle.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_lightgrey));
        binding.edtOverview.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_lightgrey));
        binding.edtDuration.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_lightgrey));
    }

    private void setMovieData() {
        binding.edtTitle.setText(movie.getTitle());
        binding.edtOverview.setText(movie.getOverview());
        binding.edtDuration.setText(String.valueOf(movie.getDuration()));
        binding.sbRating.setProgress((int) (2 * movie.getRating()));
        binding.tvRating.setText(String.valueOf(movie.getRating()));

        switch (movie.getAgeRate()) {
            case "SU":
                binding.rbSU.setChecked(true);
                break;
            case "13+":
                binding.rb13.setChecked(true);
                break;
            case "17+":
                binding.rb17.setChecked(true);
                break;
            case "21+":
                binding.rb21.setChecked(true);
                break;
        }

        if (movie.getGenre() != null) {
            String[] genres = movie.getGenre().split(", ");
            List<String> genreList = new ArrayList<>(Arrays.asList(genres));

            if (genreList.contains(getString(R.string.genre_fantasi))) {
                binding.cbFantasi.setChecked(true);
            }
            if (genreList.contains(getString(R.string.genre_romantis))) {
                binding.cbRomantis.setChecked(true);
            }
            if (genreList.contains(getString(R.string.genre_komedi))) {
                binding.cbKomedi.setChecked(true);
            }
            if (genreList.contains(getString(R.string.genre_aksi))) {
                binding.cbAksi.setChecked(true);
            }
            if (genreList.contains(getString(R.string.genre_petualangan))) {
                binding.cbPetualangan.setChecked(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}