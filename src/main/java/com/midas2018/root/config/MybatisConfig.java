package com.midas2018.root.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.midas2018.root.model.CafeMenuStatus;
import com.midas2018.root.model.CategoryVO;
import com.midas2018.root.model.OrderStatus;
import com.midas2018.root.model.UserStatus;
import com.midas2018.root.model.UserVO;
import com.midas2018.root.support.typehandler.CategoryVOTypeHandler;
import com.midas2018.root.support.typehandler.DateLongTypeHandler;
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
        typeHandlerRegistry.register(CategoryVO.class, CategoryVOTypeHandler.class);

        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setMapperLocations(new Resource[] {
                new ClassPathResource("mapper/DemoUserMapper.xml"),
                new ClassPathResource("mapper/BoardMapper.xml"),
                new ClassPathResource("mapper/UserMapper.xml")
                });
        return sqlSessionFactoryBean.getObject();
    }
}
