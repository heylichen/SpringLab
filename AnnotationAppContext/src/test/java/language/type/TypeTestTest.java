package language.type;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.Arrays;

public class TypeTestTest {
  @Test
  public void testTypeVariable() throws NoSuchFieldException {
    Field v = TypeTest.class.getField("v");//用反射的方式获取属性 public V v;
    TypeVariable typeVariable = (TypeVariable) v.getGenericType();//获取属性类型
    System.out.println("TypeVariable1 getGenericType(): " + typeVariable);
    //获取类型变量上界 [class java.lang.Number, interface java.io.Serializable]
    System.out.println("TypeVariable2 getBounds() :" + Arrays.asList(typeVariable.getBounds()));
    System.out.println("TypeVariable3 getGenericDeclaration():" + typeVariable.getGenericDeclaration());//获取类型变量声明载体
    //1.8 AnnotatedType: 如果这个这个泛型参数类型的上界用注解标记了，我们可以通过它拿到相应的注解
    // getAnnotatedBounds() 对 getBounds 的扩展
    AnnotatedType[] annotatedTypes = typeVariable.getAnnotatedBounds();
    System.out.println("TypeVariable4 getAnnotatedBounds():" + Arrays.asList(annotatedTypes) + " : " +
        Arrays.asList(annotatedTypes[0].getAnnotations()));
    System.out.println("TypeVariable5 :" + typeVariable.getName());
  }

  @Test
  public void testParameterizedType() throws NoSuchFieldException {
    Field list = TypeTest.class.getField("list");
    Type genericType1 = list.getGenericType();
    System.out.println("参数类型1:" + genericType1.getTypeName()); //参数类型1:java.util.List<T>

    Field map = TypeTest.class.getField("map");
    Type genericType2 = map.getGenericType();
    System.out.println("参数类型2:" + genericType2.getTypeName());//参数类型2:java.util.Map<java.lang.String, T>

    if (genericType2 instanceof ParameterizedType) {
      ParameterizedType pType = (ParameterizedType) genericType2;
      Type[] types = pType.getActualTypeArguments();
      System.out.println("参数类型列表:" + Arrays.asList(types));//参数类型列表:[class java.lang.String, T]
      System.out.println("参数原始类型:" + pType.getRawType());//参数原始类型:interface java.util.Map
      System.out.println("参数父类类型:" + pType.getOwnerType());//参数父类类型:null,因为Map没有外部类，所以为null
    }
  }

  @Test
  public void testGenericArrayType() throws NoSuchFieldException {
    Field tArray = TypeTest.class.getField("tArray");
    System.out.println("数组参数类型1:" + tArray.getGenericType());
    Field ltArray = TypeTest.class.getField("ltArray");
    System.out.println("数组参数类型2:" + ltArray.getGenericType());//数组参数类型2:java.util.List<T>[]
    if (tArray.getGenericType() instanceof GenericArrayType) {
      GenericArrayType arrayType = (GenericArrayType) tArray.getGenericType();
      System.out.println("数组参数类型3:" + arrayType.getGenericComponentType());//数组参数类型3:T
    }
  }

  @Test
  public void testWildcardType() throws NoSuchFieldException {
    //Map<? super String, ? extends Number> mapWithWildcard
    Field mapWithWildcard = TypeTest.class.getField("mapWithWildcard");
    Type wild = mapWithWildcard.getGenericType();//先获取属性的泛型类型 Map<? super String, ? extends Number>
    if (wild instanceof ParameterizedType) {
      ParameterizedType pType = (ParameterizedType) wild;
      Type[] actualTypes = pType.getActualTypeArguments();//获取<>里面的参数变量 ? super String, ? extends Number
      System.out.println("WildcardType1:" + Arrays.asList(actualTypes));
      WildcardType first = (WildcardType) actualTypes[0];//? super java.lang.String
      WildcardType second = (WildcardType) actualTypes[1];//? extends java.lang.Number
      System.out.println("WildcardType2: lower:" + Arrays.asList(first.getLowerBounds()) + "  upper:" + Arrays.asList(first.getUpperBounds()));//WildcardType2: lower:[class java.lang.String]  upper:[class java.lang.Object]
      System.out.println("WildcardType3: lower:" + Arrays.asList(second.getLowerBounds()) + "  upper:" + Arrays.asList(second.getUpperBounds()));//WildcardType3: lower:[]  upper:[class java.lang.Number]
    }
  }
}