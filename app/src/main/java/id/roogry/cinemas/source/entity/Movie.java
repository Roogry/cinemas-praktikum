package id.roogry.cinemas.source.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies")
public class Movie implements Parcelable {
    @PrimaryKey()@NonNull
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private String id;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;

    @SerializedName("duration")
    @ColumnInfo(name = "duration")
    private int duration;

    @SerializedName("age_rate")
    @ColumnInfo(name = "age_rate")
    private String ageRate;

    @SerializedName("genre")
    @ColumnInfo(name = "genre")
    private String genre;

    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    private float rating;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("created_at")
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAgeRate() {
        return ageRate;
    }

    public void setAgeRate(String ageRate) {
        this.ageRate = ageRate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.overview);
        parcel.writeInt(this.duration);
        parcel.writeString(this.ageRate);;
        parcel.writeString(this.genre);
        parcel.writeFloat(this.rating);
        parcel.writeString(this.updatedAt);
        parcel.writeString(this.createdAt);
    }

    @Ignore
    public Movie(){
    }

    public Movie(String title, String overview, int duration, String ageRate, String genre, float rating, String updatedAt, String createdAt) {
        this.title = title;
        this.overview = overview;
        this.duration = duration;
        this.ageRate = ageRate;
        this.genre = genre;
        this.rating = rating;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    private Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.duration = in.readInt();
        this.ageRate = in.readString();
        this.genre = in.readString();
        this.rating = in.readFloat();
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
