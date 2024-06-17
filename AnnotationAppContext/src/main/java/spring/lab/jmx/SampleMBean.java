package spring.lab.jmx;

public interface SampleMBean {
  String getName();

  void setName(String name);

  String getAge();

  void setAge(String age);

  void report(String message);

}