package me.trqhxrd.grapesrpg.api.utils.group;

/**
 * A collection for 2 different Field-types.
 * Extends from a one-dimensional Group.
 *
 * @param <X> The first type, which you want to store.
 * @param <Y> The second type, which you want to store.
 * @author Trqhxrd
 */
public class Group2<X, Y> extends Group<X> implements Cloneable {

    /**
     * The parameter for the second value, which you want to store.
     */
    protected Y y;

    /**
     * Creates a new group with the values of x and y.
     *
     * @param x The first value, which you want to set.
     * @param y The second value, which you want to set.
     */
    public Group2(X x, Y y) {
        super(x);
        this.y = y;
    }

    /**
     * This creates a new Group, in which both fields are null.
     */
    public Group2() {
        super();
        this.y = null;
    }

    /**
     * This constructor creates a new Group2 Object and clones the values from the other Group2.
     *
     * @param other The Group from which you want to clone the values.
     */
    public Group2(Group2<X, Y> other) {
        super(other);
        this.y = other.getY();
    }

    /**
     * This method returns the value of the second Object.
     *
     * @return Field Y.
     */
    public Y getY() {
        return y;
    }

    /**
     * This method sets the value of the Y Field.
     *
     * @param y The new value for Field Y.
     */
    public void setY(Y y) {
        this.y = y;
    }
}
