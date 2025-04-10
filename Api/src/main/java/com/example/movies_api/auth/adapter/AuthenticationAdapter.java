/* Implementation of the Adapter for authenticating users via XML-based requests*/
package com.example.movies_api.auth.adapter;

import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.auth.AuthenticationResponse;

public interface AuthenticationAdapter {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    //Open-Close Principle 3/3 (data steering) [added lines]
    AuthenticationResponse authenticateFromRaw(String rawBody); // DODANA
    String getSupportedContentType(); // np. "application/json"
}
