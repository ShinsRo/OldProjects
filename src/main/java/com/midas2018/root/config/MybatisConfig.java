package com.midas2018.root.config;

import javax.sql.DataSource;

import com.midas2018.root.model.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.midas2018.root.support.typehandler.CafeMenuAndOptionsListTypeHandler;
import com.midas2018.root.support.typehandler.CafeMenuAndOptionsTypeHandler;
import com.midas2018.root.support.typehandler.CategoryVOTypeHandler;
import com.midas2018.root.support.typehandler.DateLongTypeHandler;
import com.midas2018.root.support.typehandler.OptionDataTypeHandler;
import com.midas2018.root.support.typehandler.ValueEnumTypeHandler;

@Configuration
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        typeAliasRegistry.registerAlias("UserVO", UserVO.class);

        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        typeHandlerRegistry.register(DateLongTypeHandler.class);
        typeHandlerRegistry.register(UserStatus.class, ValueEnumTypeHandler.class);
        typeHandlerRegistry.register(CafeMenuStatus.class, ValueEnumTypeHandler.class);
        typeHandlerRegistry.register(OrderStatus.class, ValueEnumTypeHandler.class);
        typeHandlerRegistry.register(CategoryStatus.class, ValueEnumTypeHandler.class);
        typeHandlerRegistry.register(OptionStatus.class, ValueEnumTypeHandler.class);
        typeHandlerRegistry.register(OptionData.class, OptionDataTypeHandler.class);
        typeHandlerRegistry.register(CafeMenuAndOptions.class, CafeMenuAndOptionsTypeHandler.class);
        typeHandlerRegistry.register(CafeMenuAndOptionsList.class, CafeMenuAndOptionsListTypeHandler.class);

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setMapperLocations(new Resource[] {
                new ClassPathResource("mapper/DemoUserMapper.xml"),
                new ClassPathResource("mapper/UserOrderMapper.xml"),
                new ClassPathResource("mapper/BoardMapper.xml"),
                new ClassPathResource("mapper/UserMapper.xml"),
                new ClassPathResource("mapper/CafeMenuMapper.xml")
                });
        return sqlSessionFactoryBean.getObject();
    }
}
