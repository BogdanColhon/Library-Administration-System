package Controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class CriptareTest extends ApplicationTest {
    Criptare c;
    @Before
    public void setUp() throws Exception {
        c = new Criptare();
    }
    @After
    public void tearDown() throws Exception {
        c = null;
    }
    @Test
    public void testCriptare() throws NoSuchAlgorithmException {
        String algorithm="SHA-256";
        String x="1";
        assertEquals("6B86B273FF34FCE19D6B804EFF5A3F5747ADA4EAA22F1D49C01E52DDB7875B4B",c.generateHash(x,algorithm));
    }

}