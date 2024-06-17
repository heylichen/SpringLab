package spring.lab.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class SampleAgent {
  public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    ObjectName helloName = new ObjectName("jmxBean:name=hello");
    //create mbean and register mbean
    server.registerMBean(new Sample(), helloName);
    Thread.sleep(60 * 60 * 1000);
  }
}
