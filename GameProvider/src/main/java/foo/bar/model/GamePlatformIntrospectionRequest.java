package foo.bar.model;

import io.smallrye.common.constraint.NotNull;

public class GamePlatformIntrospectionRequest {
    //userID in game platform domain
    @NotNull
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
