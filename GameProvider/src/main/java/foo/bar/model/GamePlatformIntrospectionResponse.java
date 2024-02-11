package foo.bar.model;


public class GamePlatformIntrospectionResponse {
    private String token;

    public GamePlatformIntrospectionResponse(String token) {
        this.token = token;
    }

    public static GamePlatformIntrospectionResponse  of(String token){
        return new GamePlatformIntrospectionResponse(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
