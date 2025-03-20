/* Implementation of the Adapter for authenticating users via XML-based requests*/
package com.example.movies_api.auth.adapter;

import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.auth.AuthenticationResponse;

public interface AuthenticationAdapter {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
