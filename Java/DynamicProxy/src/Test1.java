import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ArrayList代理,通过代理计算每个方法调用所需时间
		@SuppressWarnings("unchecked")
		List<Integer> arrayListProxy = (List<Integer>) Proxy.newProxyInstance(
				ArrayList.class.getClassLoader(), /*
												 * 定义代理类的类加载器,用于创建代理对象,
												 * 不一定必须是ArrayList,也可以是其他的类加载器
												 */
				ArrayList.class.getInterfaces(), /* 代理类要实现的接口列表 */
				new InvocationHandler() { /* 指派方法调用的调用处理程序,这里用了匿名内部类 */

					private ArrayList<Integer> target = new ArrayList<Integer>(); // 目标对象(真正操作的对象)

					/**
					 * <B>方法描述：</B>
					 * <p style="margin-left:20px;color:#A52A2A;">
					 * 在代理实例上处理方法调用并返回结果
					 * 
					 * @param proxy
					 *            代理对象(注意不是目标对象)
					 * @param method
					 *            被代理的方法
					 * @param args
					 *            被代理的方法的参数集
					 * @return <span style="color: #008080;"> 返回方法调用结果 </span>
					 */
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {

						long beginTime = System.nanoTime(); // 开始时间
						// TimeUnit.MICROSECONDS.sleep(1);
						Object obj = method.invoke(target, args); // 实际调用的方法,并接受方法的返回值
						long endTime = System.nanoTime(); // 结束时间
						System.out.println("[" + method.getName() + "] spend "
								+ (endTime - beginTime) + " ns");
						return obj; // 返回实际调用的方法的返回值

					}

				});
		arrayListProxy.add(2);
		arrayListProxy.add(4);
		arrayListProxy.add(4);
		arrayListProxy.add(4);
		System.out.println("--------- 迭代 ---------");
		for (int i : arrayListProxy) {
			System.out.print(i + "\t");
		}
		List<Integer> list = new ArrayList<>();
		list.add(2);
		list.add(4);
		list.add(4);
		long begin = System.nanoTime();
		list.add(4);
		long end = System.nanoTime();
		System.out.println(end - begin);
		begin = System.nanoTime();
		list.add(4);
		end = System.nanoTime();
		System.out.println(end - begin);
		begin = System.nanoTime();
		list.add(4);
		end = System.nanoTime();
		System.out.println(end - begin);
	}

}
