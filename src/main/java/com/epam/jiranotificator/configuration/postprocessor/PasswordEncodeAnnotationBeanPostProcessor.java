package com.epam.jiranotificator.configuration.postprocessor;

import com.epam.jiranotificator.configuration.annotations.PasswordEncode;
import com.epam.jiranotificator.utils.CryptUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class PasswordEncodeAnnotationBeanPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        final Class<?> beanClass = bean.getClass();
        final Field[] fields = beanClass.getDeclaredFields();
        for(final Field field : fields){
            if (field.isAnnotationPresent(PasswordEncode.class)){
                field.setAccessible(true);
                try {
                    final String hashPassword = (String) field.get(bean);
                    final String password = CryptUtils.decode(hashPassword);
                    field.set(bean, password);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Can't managed password " + e.getMessage());
                }
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }
}
