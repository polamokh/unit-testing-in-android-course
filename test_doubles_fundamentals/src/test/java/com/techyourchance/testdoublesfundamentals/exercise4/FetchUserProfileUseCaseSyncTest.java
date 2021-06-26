package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import jdk.internal.jline.internal.Nullable;

import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.UseCaseResult;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    private static final String USER_ID = "123";
    private static final String USER_FULL_NAME = "fullName";
    private static final String USER_IMAGE_URL = "imageUrl";

    private UserProfileHttpEndpointSyncTd userProfileHttpEndpointSyncTd;
    private UsersCacheTd usersCacheTd;

    private FetchUserProfileUseCaseSync sut;


    @Before
    public void setUp() {
        userProfileHttpEndpointSyncTd = new UserProfileHttpEndpointSyncTd();
        usersCacheTd = new UsersCacheTd();
        sut = new FetchUserProfileUseCaseSync(userProfileHttpEndpointSyncTd, usersCacheTd);
    }


    @Test
    public void fetchUserProfile_success_userIdPassedToEndpoint() {
        sut.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndpointSyncTd.userId, is(USER_ID));
    }

    // fetch user profile success - user cached

    @Test
    public void fetchUserProfile_success_userCached() {
        sut.fetchUserProfileSync(USER_ID);
        User user = usersCacheTd.getUser(USER_ID);
        assertNotNull(user);
        assertThat(user.getUserId(), is(USER_ID));
        assertThat(user.getFullName(), is(USER_FULL_NAME));
        assertThat(user.getImageUrl(), is(USER_IMAGE_URL));
    }

    // fetch user profile fail - user not cached
    @Test
    public void fetchUserProfile_generalError_userNotCached() {
        userProfileHttpEndpointSyncTd.isGeneralError = true;
        sut.fetchUserProfileSync(USER_ID);
        User user = usersCacheTd.getUser(USER_ID);
        assertNull(user);
    }

    @Test
    public void fetchUserProfile_authError_userNotCached() {
        userProfileHttpEndpointSyncTd.isAuthError = true;
        sut.fetchUserProfileSync(USER_ID);
        User user = usersCacheTd.getUser(USER_ID);
        assertNull(user);
    }

    @Test
    public void fetchUserProfile_serverError_userNotCached() {
        userProfileHttpEndpointSyncTd.isServerError = true;
        sut.fetchUserProfileSync(USER_ID);
        User user = usersCacheTd.getUser(USER_ID);
        assertNull(user);
    }

    // fetch user profile success - return success
    @Test
    public void fetchUserProfile_success_successReturned() {
        UseCaseResult result = sut.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    // fetch user profile fail - return failure
    @Test
    public void fetchUserProfile_generalError_failureReturned() {
        userProfileHttpEndpointSyncTd.isGeneralError = true;
        UseCaseResult result = sut.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_authError_failureReturned() {
        userProfileHttpEndpointSyncTd.isAuthError = true;
        UseCaseResult result = sut.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_serverError_failureReturned() {
        userProfileHttpEndpointSyncTd.isServerError = true;
        UseCaseResult result = sut.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void fetchUserProfile_networkError_networkErrorReturned() {
        userProfileHttpEndpointSyncTd.isNetworkError = true;
        UseCaseResult result = sut.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {

        public String userId = "";
        public boolean isGeneralError;
        public boolean isAuthError;
        public boolean isServerError;
        public boolean isNetworkError;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            this.userId = userId;
            if (isGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if (isAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            } else if (isServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (isNetworkError) {
                throw new NetworkErrorException();
            }
            return new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, USER_FULL_NAME, USER_IMAGE_URL);
        }
    }

    private static class UsersCacheTd implements UsersCache {

        private final List<User> users = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existUser = getUser(user.getUserId());
            if (existUser != null)
                users.remove(existUser);
            users.add(user);
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            for (User user : users) {
                if (user.getUserId().equals(userId))
                    return user;
            }
            return null;
        }
    }
}