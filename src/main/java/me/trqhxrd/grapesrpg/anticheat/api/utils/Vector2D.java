package me.trqhxrd.grapesrpg.anticheat.api.utils;

/**
 * This just a simple vector.
 */
public class Vector2D {

    /**
     * The x-length of the vector.
     */
    private double x;
    /**
     * The y-length of the vector.
     */
    private double y;

    /**
     * This constructor creates a new vector with initial x and y coordinates.
     *
     * @param x The initial length of the x-part of the vector.
     * @param y The initial length of the y-part of the vector.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This creates a new vector with a length of 0.
     */
    public Vector2D() {
        this(0, 0);
    }

    /**
     * Getter for the vectors x length.
     *
     * @return The vectors x-length
     */
    public double getX() {
        return x;
    }

    /**
     * Setter for the vectors x-length.
     *
     * @param x The vectors new x-length.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter for the vectors y length.
     *
     * @return The vectors y-length
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for the vectors y-length.
     *
     * @param y The vectors new y-length.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the length of the vector,
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(Math.abs(this.getX()) * Math.abs(this.getX()) + Math.abs(this.getY()) * Math.abs(this.getY()));
    }
}
