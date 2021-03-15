package me.trqhxrd.grapesrpg.api.utils.group;

import java.util.Objects;

/**
 * A Group, which contains four different Object of four different types.
 *
 * @param <X> The type of the first value in this group.
 * @param <Y> The type of the second value in this group.
 * @param <Z> The type of the third value in this group.
 * @param <T> The type of the fourth value in this group.
 * @author Trqhxrd
 */
public class Group4<X, Y, Z, T> extends Group3<X, Y, Z> {

    /**
     * The field, which stores the fourth value of this group.
     */
    private T t;

    /**
     * This constructor creates a new Group, which contains the four values you enter here.
     *
     * @param x The first value of this group.
     * @param y The second value of this group.
     * @param z The third value of this group.
     * @param t The fourth value of this group.
     */
    public Group4(X x, Y y, Z z, T t) {
        super(x, y, z);
        this.t = t;
    }

    /**
     * This constructor creates a new Group, which content values will be null.
     */
    public Group4() {
        super();
        this.t = null;
    }

    /**
     * This constructor clones the values from another Group.
     *
     * @param other The other group from which you want to get the values from.
     */
    public Group4(Group4<X, Y, Z, T> other) {
        super(other);
        this.t = other.getT();
    }

    /**
     * Returns the fourth value of the Group.
     *
     * @return The fourth value, which is stored in the field t.
     */
    public T getT() {
        return t;
    }

    /**
     * This method sets the value of t.
     * The old value will be overwritten.
     *
     * @param t The new value for t.
     */
    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "Group4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", t=" + t +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group4)) return false;
        if (!super.equals(o)) return false;
        Group4<?, ?, ?, ?> group4 = (Group4<?, ?, ?, ?>) o;
        return Objects.equals(t, group4.t);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), t);
    }
}
