package cn.itcast.day3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

public class ProxyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
		System.out.println(clazzProxy1.getName());
		
		//发现只有一个带参数的构造函数
		System.out.println("----------begin constructors list----------");
		/*$Proxy0()
		$Proxy0(InvocationHandler,int)*/
		Constructor[] constructors = clazzProxy1.getConstructors();
		for(Constructor constructor : constructors){
			String name = constructor.getName();
			StringBuilder sBuilder = new StringBuilder(name);
			sBuilder.append('(');
			Class[] clazzParams = constructor.getParameterTypes();
			for(Class clazzParam : clazzParams){
				sBuilder.append(clazzParam.getName()).append(',');
			}
			if(clazzParams!=null && clazzParams.length != 0)
				sBuilder.deleteCharAt(sBuilder.length()-1);
			sBuilder.append(')');
			System.out.println(sBuilder.toString());			
		}

		System.out.println("----------begin methods list----------");
		/*$Proxy0()
		$Proxy0(InvocationHandler,int)*/
		Method[] methods = clazzProxy1.getMethods();
		for(Method method : methods){
			String name = method.getName();
			StringBuilder sBuilder = new StringBuilder(name);
			sBuilder.append('(');
			Class[] clazzParams = method.getParameterTypes();
			for(Class clazzParam : clazzParams){
				sBuilder.append(clazzParam.getName()).append(',');
			}
			if(clazzParams!=null && clazzParams.length != 0)
				sBuilder.deleteCharAt(sBuilder.length()-1);
			sBuilder.append(')');
			System.out.println(sBuilder.toString());			
		}
		
		System.out.println("----------begin create instance object----------");
		//Object obj = clazzProxy1.newInstance();
		Constructor constructor = clazzProxy1.getConstructor(InvocationHandler.class);
		class MyInvocationHander1 implements InvocationHandler{

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
		
		}
		Collection proxy1 = (Collection)constructor.newInstance(new MyInvocationHander1());
		
		// hashCode, equals, or toString methods  从Object继承来的只有这三个方法交给Hander
		System.out.println(proxy1);
		proxy1.clear();
		//proxy1.size();
		//System.out.println("111111111111111");
		
		Collection proxy2 = (Collection)constructor.newInstance(new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				return null;
			}
			
		});
		
	
		Collection proxy4 =(Collection) Proxy.newProxyInstance(Collection.class.getClassLoader(),new Class[]{ Collection.class}, new InvocationHandler() {
			
			ArrayList target = new ArrayList();	
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				long beginTime = System.currentTimeMillis();
				Object retVal = method.invoke(target, args);
				long endTime = System.currentTimeMillis();
				System.out.println(method.getName() + " running time of " + (endTime - beginTime));
				return retVal;
			}
		});
		proxy4.add("zxx");
		proxy4.add("lhm");
		proxy4.add("bxd");
		System.out.println(proxy4.size());
		
		
		final ArrayList target = new ArrayList();			
		Collection proxy3 = (Collection)getProxy(target,new MyAdvice());
		proxy3.add("zxx");
		proxy3.add("lhm");
		proxy3.add("bxd");
		System.out.println(proxy3.size());
		System.out.println(proxy3.getClass().getName());
	}

	/**
	 * 生成代理的通用方法
	 * 以后只需要写MyAdvice就可以了
	 * @param target
	 * @param advice
	 * @return
	 */
	private static Object getProxy(final Object target,final Advice advice) {
		Object proxy3 = Proxy.newProxyInstance(
				target.getClass().getClassLoader(),
				/*new Class[]{Collection.class},*/
				target.getClass().getInterfaces(),
				new InvocationHandler(){
				
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {

						/*long beginTime = System.currentTimeMillis();
						Object retVal = method.invoke(target, args);
						long endTime = System.currentTimeMillis();
						System.out.println(method.getName() + " running time of " + (endTime - beginTime));
						return retVal;*/
						

						advice.beforeMethod(method);
						Object retVal = method.invoke(target, args);
						advice.afterMethod(method);
						return retVal;						
						
					}
				}
				);
		return proxy3;
	}

}
