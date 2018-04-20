package com.example.admin.movieapp;


public class Movies {

    public   String movieName;
    private  String realeaseDate;
    private  String story;
    private String id;
    private  String image;
    private  float rating;
    private  String video;
    private  boolean adult;


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(String realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
