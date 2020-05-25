package net.sedion.wwf.system;

import com.jfinal.config.*;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import net.sedion.wwf.controller.IndexController;
import net.sedion.wwf.rabbitmq.RabbitMQConsumerPlugin;
import net.sedion.wwf.rabbitmq.mq.consumer.SimpleConsumer;

/**
 * 系统配置
 *
 * @author WWF
 */
public class SystemConfig extends JFinalConfig {

    public static void main(String[] args) {
        UndertowServer.start(SystemConfig.class);
    }

    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("system.properties");
        me.setDevMode(getPropertyToBoolean("devMode", false));
        me.setEncoding("UTF-8");
        me.setInjectDependency(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class, "/WEB-INF/web/");
    }

    @Override
    public void configEngine(Engine me) {
        me.setBaseTemplatePath("webapp");
        me.setToClassPathSourceFactory();
    }

    @Override
    public void configPlugin(Plugins me) {
        me.add(new RabbitMQConsumerPlugin(SimpleConsumer.class));
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
}
