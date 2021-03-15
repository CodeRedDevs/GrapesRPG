package me.trqhxrd.grapesrpg.api.utils.group;

import java.util.Objects;

/**
 * This Class can store 3 different Objects from 3 different types.
 *
 * @param <X> The type of the first Object, you want to store.
 * @param <Y> The type of the second Object, you want to store.
 * @param <Z> The type of the third Object, you want to store.
 */
public class Group3<X, Y, Z> extends Group2<X, Y> {

    /**
     * The third Object, which you want to store.
     */
    protected Z z;

    /**
     * This constructor creates a Group with three variables,
     * which contains the three values, you give in the constructor as its start values.
     *
     * @param x The first value, you want to set in this Object.
     * @param y The second value, you want to set in this Object.
     * @param z The third value, you want to set in this Object.
     */
    public Group3(X x, Y y, Z z) {
        super(x, y);
        this.z = z;
    }

    /**
     * This constructor creates an empty Object.
     * All set values are null.
     */
    public Group3() {
        super();
        this.z = null;
    }

    /**
     * This constructor creates a new Object of a group with the values of another group.
     *
     * @param other The Group from which you want to clone the values from.
     */
    public Group3(Group3<X, Y, Z> other) {
        super(other);
        this.z = other.getZ();
    }

    /**
     * Returns the third value of the group.
     *
     * @return The third Object in this group.
     */
    public Z getZ() {
        return z;
    }

    /**
     * This methods sets the third value in the group.
     *
     * @param z The new value for the third field.
     */
    public void setZ(Z z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Group3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group3)) return false;
        if (!super.equals(o)) return false;
        Group3<?, ?, ?> group3 = (Group3<?, ?, ?>) o;
        return Objects.equals(z, group3.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), z);
    }
}
