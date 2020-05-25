package net.sedion.wwf.controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import net.sedion.wwf.service.TestService;

/**
 * @author WWF
 */
public class IndexController extends Controller {


    @Inject
    TestService testService;

    public void index() {
        testService.doit("1");
        testService.doit2("2");
        renderTemplate("index.html");
    }

    public void json() {
        setAttr("success", true);
        setAttr("message", "访问正确");
        setAttr("error", "");
        renderJson();
    }

}
