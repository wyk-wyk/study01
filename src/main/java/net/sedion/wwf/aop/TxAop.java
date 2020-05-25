package net.sedion.wwf.aop;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;


/**
 * @author WWF
 */
public class TxAop implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        System.out.println("before");
        inv.invoke();
        System.out.println("after");
    }
}
