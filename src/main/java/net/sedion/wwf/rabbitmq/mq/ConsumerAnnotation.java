package net.sedion.wwf.rabbitmq.mq;


import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * mq 消费者，参数注解配置
 *
 * @author WWF
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumerAnnotation {


    /**
     * the name of the queue
     *
     * @return
     */
    String queue() default "";

    /**
     * true if we are declaring a durable queue (the queue will survive a server restart)
     *
     * @return
     */
    boolean durable() default true;

    /**
     * true if we are declaring an exclusive queue (restricted to this connection)
     *
     * @return
     */
    boolean exclusive() default false;

    /**
     * true if we are declaring an autodelete queue (server will delete it when no longer in use)
     *
     * @return
     */
    boolean autoDelete() default false;

    /**
     * json 格式
     * {
     *     "key1":"value1",
     *     "key2":value2
     * }
     * @return
     */
    String arguments() default "";

}
