package net.sedion.wwf.service;

import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import net.sedion.wwf.aop.TxAop;

/**
 * @author WWF
 */
public class TestService {


    @Before(TxAop.class)
    public Ret doit(String message) {
        System.out.println(message);
        return Ret.ok();
    }

    public void doit2(String message) {
        System.out.println(message);
    }


}
