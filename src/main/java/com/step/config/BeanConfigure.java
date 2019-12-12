package com.step.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.net.SocketFactory;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by user on 2019-04-17.
 */
@Component
@Slf4j
public class BeanConfigure {
    private SocketFactory socketFactory;

    /***
     * 校验器
     * @return
     */
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                //开启快速校验--默认校验所有参数，false校验全部
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

//    /***
//     * 自定义json 解析器
//     * @return HttpMessageConverters
//     */
//   public HttpMessageConverters  fastJsonHttpMessageConverters(){
//       FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
//       fastConverter.setFastJsonConfig(fastJsonConfig);
//       HttpMessageConverter<?> converter = fastConverter;
//       return new HttpMessageConverters(converter);
//   }

}
