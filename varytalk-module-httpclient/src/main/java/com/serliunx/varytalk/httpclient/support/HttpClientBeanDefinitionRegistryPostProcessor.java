package com.serliunx.varytalk.httpclient.support;

import com.serliunx.varytalk.httpclient.annotation.FeignClient;
import com.serliunx.varytalk.httpclient.decoder.JacksonDecoder;
import com.serliunx.varytalk.httpclient.encoder.JacksonEncoder;
import feign.Feign;
import feign.Request;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Http客户端bean注册
 * @author SerLiunx
 * @since 1.0
 * @see BeanDefinitionRegistryPostProcessor
 */
@Component
@SuppressWarnings("all")
public final class HttpClientBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String BASE_PACKAGE = "com.serliunx.varytalk.httpclient";
    private final Class<? extends Annotation> annotatedType = FeignClient.class;
    private final Encoder encoder = new JacksonEncoder();
    private final Decoder decoder = new JacksonDecoder();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathClientScanner scanner = new ClassPathClientScanner(registry);
        scanner.addIncludeFilter(new InterfaceWithAnnotationTypeFilter(annotatedType));
        Set<BeanDefinition> components = scanner.findCandidateComponents(BASE_PACKAGE);
        //扫描、逐一注册符合条件的HttpClient客户端
        for (BeanDefinition component : components) {
            BeanDefinitionHolder beanDefinitionHolder = processBeanDefinition(component);
            registry.registerBeanDefinition(beanDefinitionHolder.getBeanName(),
                    beanDefinitionHolder.getBeanDefinition());
        }
    }

    private BeanDefinitionHolder processBeanDefinition(BeanDefinition beanDefinition){
        try {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            if(!clazz.isInterface()){
                throw new RuntimeException("无法注册非接口类型的Feign客户端!");
            }
            FeignClient feignClient = clazz.getAnnotation(FeignClient.class);
            if(feignClient == null){
                throw new RuntimeException("该接口为包含指定的注解: " + feignClient.getClass().getName());
            }
            //获取注解的url
            String url = feignClient.url();

            //初始化bean定义
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition(beanDefinition);
            genericBeanDefinition.setBeanClass(clazz);

            //设置bean对象
            genericBeanDefinition.setInstanceSupplier(() -> {
                return build(clazz, url);
            });

            //注册bean、生成bean名称
            String name = feignClient.value().isEmpty() ? generateBeanName(beanDefinition) : feignClient.value();
            return new BeanDefinitionHolder(genericBeanDefinition, name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将类名的首字母小写作为bean的名称
     */
    private String generateBeanName(BeanDefinition beanDefinition){
        String[] splitName = beanDefinition.getBeanClassName().toString().split("\\.");
        String name = splitName[splitName.length - 1];
        if (name == null || name.isEmpty()) {
            return name;
        }
        char[] charArray = name.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    /**
     * 使用Feign的Builder来构建客户端
     */
    private <T> T build(Class<T> clazz, String url){
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .options(new Request.Options(10,
                        TimeUnit.SECONDS, 10,
                        TimeUnit.SECONDS, true))
                .target(clazz, url);
    }
}
