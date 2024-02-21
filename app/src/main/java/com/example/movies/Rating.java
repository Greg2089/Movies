package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("kp")
    private double kp;

    public Rating(double kp) {
        this.kp = kp;
    }

    public double getKp() {
        return kp;
    }

}
