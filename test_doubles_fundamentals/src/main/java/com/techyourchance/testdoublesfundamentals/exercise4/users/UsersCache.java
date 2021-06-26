package com.techyourchance.testdoublesfundamentals.exercise4.users;

import jdk.internal.jline.internal.Nullable;

public interface UsersCache {

    void cacheUser(User user);

    @Nullable
    User getUser(String userId);

}
