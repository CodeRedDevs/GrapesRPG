package me.trqhxrd.grapesrpg.api.utils.lock;

/**
 * This interface represents a lock.
 * You can change its state to locked, even if you don't have the key.
 * But you can only unlock it, if you have the valid key.
 *
 * @param <T> The type of Key, which is able to unlock the lock.
 * @author Trqhxrd
 */
public interface Lock<T> {

    /**
     * This method sets the locks state to locked.
     */
    void lock();

    /**
     * This method unlocks the lock using the key.
     * If the key is wrong, the lock will stay locked.
     *
     * @param key The key with which you want to unlock the lock.
     * @return Returns true, if the lock was unlocked successfully. Otherwise it will return false.
     */
    boolean unlock(T key);

    /**
     * This method returns true, if the lock is locked. If it is unlocked, this method will return false.
     *
     * @return true if the lock is locked. Otherwise false.
     */
    boolean isLocked();

    /**
     * This method checks, if the key is valid without unlocking the lock.
     * If the key is valid, the returned value is true.
     *
     * @param key The key, which you want to check.
     * @return true, if the key is valid. Otherwise false.
     */
    boolean checkKey(T key);
}
