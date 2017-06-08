package io.github.zwieback.relef.analyzers.dto;

public class NameDistanceDto {

    private long id;
    private double dist;

    public NameDistanceDto() {
    }

    public long getId() {
        return id;
    }

    public NameDistanceDto setId(long id) {
        this.id = id;
        return this;
    }

    public double getDist() {
        return dist;
    }

    public NameDistanceDto setDist(double dist) {
        this.dist = dist;
        return this;
    }
}
