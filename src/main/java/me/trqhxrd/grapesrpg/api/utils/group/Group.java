package me.trqhxrd.grapesrpg.api.utils.group;

/**
 * A simple class, which can store 1 value of one type.
 *
 * @param <X> The type of the value you want to store.
 * @author Trqhxrd
 */
public class Group<X> {
    /**
     * The Value for which you created this class.
     */
    protected X x;

    /**
     * Creates a Group with one item stored in it.
     *
     * @param x The item you want to store here.
     */
    public Group(X x) {
        this.x = x;
    }

    /**
     * Creates a group, which value is null.
     */
    public Group() {
        this.x = null;
    }

    /**
     * Creates a group, which copies the values from another group.
     *
     * @param other The other group from which you want to copy.
     */
    public Group(Group<X> other) {
        this.x = other.getX();
    }

    /**
     * Returns the stored value.
     *
     * @return the value, which was set with the constructor or the setter.
     */
    public X getX() {
        return x;
    }

    /**
     * Sets the value in this group.
     *
     * @param x The new value for field x.
     */
    public void setX(X x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Group{" +
                "x=" + x +
                '}';
    }
}
