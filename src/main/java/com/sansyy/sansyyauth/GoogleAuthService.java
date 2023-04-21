package com.sansyy.sansyyauth;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

public class GoogleAuthService {
    private static final int CODE_DIGITS = 6;
    private static final int TIME_STEP_SECONDS = 30;
    private static final int WINDOW_SIZE = 1;

    private final GoogleAuthenticator googleAuthenticator;
    public GoogleAuthService() {
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                .setCodeDigits(CODE_DIGITS)
                .setTimeStepSizeInMillis(TIME_STEP_SECONDS)
                .setWindowSize(WINDOW_SIZE)
                .build();
        this.googleAuthenticator = new GoogleAuthenticator(config);
        }
        public GoogleAuthenticatorKey createSecretKey() {
            return this.googleAuthenticator.createCredentials();
        }
        public boolean verifyCode(String secret, int code) {
            return this.googleAuthenticator.authorize(secret, code);
        }
    }
