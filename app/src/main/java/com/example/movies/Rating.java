package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {
    @SerializedName("kp")
    private double kp;

    public Rating(double kp) {
        this.kp = kp;
    }

    public double getKp() {
        return kp;
    }

}
