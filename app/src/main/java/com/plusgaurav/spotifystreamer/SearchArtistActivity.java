package com.plusgaurav.spotifystreamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SearchArtistActivity extends AppCompatActivity{

    protected static final String CLIENT_ID = "fc573f5f2253431e962277817cc9b23a";
    private static final String REDIRECT_URI = "cis-music-player-login://callback";

    private static String accessToken;
    private static final int REQUEST_CODE = 1337;

    protected static String getAccessToken() {
        return accessToken;
    }

    private static void setAccessToken(String accessToken) {
        SearchArtistActivity.accessToken = accessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //authenticate to spotify
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        setContentView(R.layout.activity_search_artist);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {

                case TOKEN:
                    Toast.makeText(SearchArtistActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                    setAccessToken(response.getAccessToken());

                    break;

                case ERROR:
                    Toast.makeText(SearchArtistActivity.this, "Could not log in, please restart app!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
