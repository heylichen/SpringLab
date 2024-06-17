package spring.lab.data.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;

@Intercepts({@Signature(type = StatementHandler.class, method = "query",args = {Statement.class, ResultHandler.class})})
@Slf4j
public class LogInterceptor implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    long startTime = System.currentTimeMillis();
    Object ovj = invocation.proceed();
    long endTime = System.currentTimeMillis();
    log.info("elapsed time: {} ms", endTime - startTime);
    return ovj;
  }
}
