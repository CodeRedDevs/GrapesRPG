package me.trqhxrd.grapesrpg.api.utils.lock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UUIDLockTest {

    @Test
    public void testUUIDLock() {
        UUID key = UUID.randomUUID();
        UUID other;
        do other = UUID.randomUUID(); while (key.equals(other));

        Lock<UUID> lock = new UUIDLock(key);
        Assertions.assertTrue(lock.isLocked());
        Assertions.assertTrue(lock.checkKey(key));
        Assertions.assertFalse(lock.checkKey(other));

        lock.unlock(key);
        Assertions.assertFalse(lock.isLocked());
    }

}