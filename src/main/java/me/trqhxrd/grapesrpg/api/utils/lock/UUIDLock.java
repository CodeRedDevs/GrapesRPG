package me.trqhxrd.grapesrpg.api.utils.lock;

import java.util.UUID;

/**
 * This is an implementation of the Lock-Class, which checks for a UUID as its key.
 *
 * @author Trqhxrd
 */
public class UUIDLock implements Lock<UUID> {

    /**
     * The Key for the lock.
     */
    private final UUID key;

    /**
     * The state of the lock.
     */
    private boolean locked;

    /**
     * This constructor creates a new lock.
     *
     * @param key The key, which should be valid.
     */
    public UUIDLock(UUID key) {
        this.key = key;
        this.locked = true;
    }

    /**
     * This method sets the locks state to locked.
     */
    @Override
    public void lock() {
        this.locked = true;
    }

    /**
     * This method unlocks the lock using the key.
     * If the key is wrong, the lock will stay locked.
     *
     * @param key The key with which you want to unlock the lock.
     * @return Returns true, if the lock was unlocked successfully. Otherwise it will return false.
     */
    @Override
    public boolean unlock(UUID key) {
        if (key.equals(this.key)) {
            this.locked = false;
            return true;
        } else return false;
    }

    /**
     * This method returns true, if the lock is locked. If it is unlocked, this method will return false.
     *
     * @return true if the lock is locked. Otherwise false.
     */
    @Override
    public boolean isLocked() {
        return locked;
    }

    /**
     * This method checks, if the key is valid without unlocking the lock.
     * If the key is valid, the returned value is true.
     *
     * @param key The key, which you want to check.
     * @return true, if the key is valid. Otherwise false.
     */
    @Override
    public boolean checkKey(UUID key) {
        return this.key.equals(key);
    }
}
