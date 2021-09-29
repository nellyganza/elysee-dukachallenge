package com.elysee.springapp.dukachallenge.services;

import com.elysee.springapp.dukachallenge.domain.TaskOwner;
import com.elysee.springapp.dukachallenge.payloads.JwtAccessTokenResponsePayload;
import com.elysee.springapp.dukachallenge.payloads.request.LoginPayload;
import com.elysee.springapp.dukachallenge.payloads.request.SignupPayload;

public interface TaskOwnerService {
    TaskOwner register(SignupPayload signupPayload);
    TaskOwner updateProfile(TaskOwner signupPayload);
    JwtAccessTokenResponsePayload ownerLogin(LoginPayload loginPayload);
}
