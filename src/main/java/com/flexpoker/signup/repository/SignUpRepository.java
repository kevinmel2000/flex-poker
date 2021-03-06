package com.flexpoker.signup.repository;

import java.util.UUID;

import com.flexpoker.signup.SignUpUser;

public interface SignUpRepository {

    boolean usernameExists(String username);

    boolean signUpCodeExists(UUID signUpCode);

    UUID findAggregateIdByUsernameAndSignUpCode(String username, UUID signUpCode);

    UUID findSignUpCodeByUsername(String username);

    void storeSignUpInformation(UUID aggregateId, String username, UUID signupCode);

    void storeNewlyConfirmedUsername(String username);

    SignUpUser fetchSignUpUser(UUID signUpUserId);

    void saveSignUpUser(SignUpUser signUpUser);

}
