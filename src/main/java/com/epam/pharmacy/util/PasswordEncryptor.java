package com.epam.pharmacy.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The type Password encryptor.
 */
public final class PasswordEncryptor {
    private PasswordEncryptor() {
    }

    /**
     * Encrypts password.
     *
     * @param password the password string
     * @return the encrypted string
     */
    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password);
    }
}
