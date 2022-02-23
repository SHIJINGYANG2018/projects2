package com.sjy.abcpay;

import com.sjy.abcpay.util.OAuthlogin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbcPayApplicationTests {

    /*@Test
    void oauth1() {
        OAuthlogin.oauth1("1","2","3","4");
    }
    @Test
    void oauth2() {
        OAuthlogin.oauth2("1","2","3","4");
    }
    @Test
    void oauth3() {
        OAuthlogin.oauth3("1");
    }*/
    public static void main(String[] args) {
        OAuthlogin.oauthLogin("1", "2", "3", "4");
    }
}
