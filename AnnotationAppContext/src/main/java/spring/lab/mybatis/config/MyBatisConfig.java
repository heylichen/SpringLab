package spring.lab.mybatis.config;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
@Configuration
public class MyBatisConfig {

  protected static final String DATA_SOURCE_NAME = "dataSource";

  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer mapperScannerConfigurer = getMapperScannerConfigurer();
    mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
    mapperScannerConfigurer.setBasePackage("spring.lab.mybatis.dao.mapper");
    return mapperScannerConfigurer;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactoryBean(@Autowired @Qualifier(DATA_SOURCE_NAME) DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = getSqlSessionFactoryBean(dataSource);
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mappers/**/*.xml"));
    return sqlSessionFactoryBean.getObject();
  }

  protected SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setTypeAliasesPackage("spring.lab.mybatis.dao");

    org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    config.setDefaultStatementTimeout(2);
    config.setDefaultFetchSize(10000);
    config.setDefaultExecutorType(ExecutorType.REUSE);
    config.setLogImpl(Slf4jImpl.class);
    config.setLogPrefix("dao.");
    factory.setConfiguration(config);

    Interceptor[] plugins = new Interceptor[]{
        new LogInterceptor()
    };
    factory.setPlugins(plugins);
    return factory;
  }

  protected static MapperScannerConfigurer getMapperScannerConfigurer() {
    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    return mapperScannerConfigurer;
  }
}
