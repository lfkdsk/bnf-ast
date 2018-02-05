package bnfgenast.bnf;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BnfComTest {

    @Test
    public void testEquals() {
        BnfCom bnfCom1 = BnfCom.rule().token("lfkdsk");
        BnfCom bnfCom2 = BnfCom.rule().token("lfkdsk");

        Assert.assertNotNull(bnfCom1);
        Assert.assertNotNull(bnfCom2);
        Assert.assertEquals(bnfCom1, bnfCom2);
    }
}