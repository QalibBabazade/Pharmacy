package az.babazade.pharmacy.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordEncoder extends BCryptPasswordEncoder {

    public PasswordEncoder() {
        super();
        //  Auto-generated constructor stub
    }

    public PasswordEncoder(int strength, SecureRandom random) {
        super(strength, random);
        //  Auto-generated constructor stub
    }

    public PasswordEncoder(int strength) {
        super(strength);
        //  Auto-generated constructor stub
    }

    public String getHashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        // System.out.println(hashedPassword);
        return hashedPassword;
    }

    /*	 * (non-Javadoc)
     *
     * @see
     * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder#encode
     * (java.lang.CharSequence)*/


    @Override
    public String encode(CharSequence rawPassword) {
        //  Auto-generated method stub

        try {
            return PasswordHash
                    .createHash(rawPassword.toString().toCharArray());
        } catch (NoSuchAlgorithmException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*	 * (non-Javadoc)
     *
     * @see
     * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder#matches
     * (java.lang.CharSequence, java.lang.String)*/


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return PasswordHash.validatePassword(rawPassword.toString()
                    .toCharArray(), encodedPassword);
        } catch (NoSuchAlgorithmException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
