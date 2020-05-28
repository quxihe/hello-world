package pacOne;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Demo {
	public Demo() {
		
	}
//	public static void main(String[] args) {
//		String[] str = {"2","1","3"};
//		Arrays.sort(str, String::compareTo);
////		Arrays.sort(str, (s2,s1)->s1.compareTo(s2));
//		List<String> list = Arrays.asList(str);
//		list.forEach(obj->System.out.println(obj));
//		new Thread(
//				new Runnable() {
//					@Override
//				    public void run() {
//						for(int i=0;i<1;i++) {
//							System.out.println(222);
//						}
//				    }
//				}
//				).start();
//		
//		new Thread(()->{for(int i=0;i<1;i++) {
//			System.out.println(111);
//		}}
//				).start();
//		
//		
//		Stream<String> stream = Stream.of("A", "B", "C", "D");
//        // forEach()方法相当于内部循环调用，
//        // 可传入符合Consumer接口的void accept(T t)的方法引用：
//        stream.forEach(System.out::println);
//        
//        Stream<Integer> s= Stream.generate(new NatualSupplier()); 
//        s.limit(10).forEach(System.out::print);
//        
//        int a = 0;
//        for (int i = 0; i < 99; i++) {
//            a = a ++;
//        }
//        System.out.println("a="+a);//a=0
//        
//        int b = 0;
//        for (int i = 0; i < 99; i++) {
//            b = ++ b;
//        }
//        System.out.println("b="+b);
//        int x = -5;
//        int y = -12;
//        System.out.println(x % y);
//        
//        Float t =1.0;
//        
//	}
	static{
		   int x=5;
		}
		static int x,y;
		public static void main(String args[]){
		   x--;
		   myMethod( );
		   System.out.println(x+y+ ++x);
		   System.out.println(true&false);
		   String str = "1"+'1';
		   
		   int a =100,b=50,c=a---b,d=a---b;
	        System.out.println(a);
	        System.out.println(b);
	        System.out.println(c);
	        System.out.println(d);
	        
	        System.out.println(true|myMethod());
		}
		public static boolean myMethod( ){
		  System.out.println(111);
		  return false;
		 }
	
}

class NatualSupplier implements Supplier<Integer> {
    int n = -1;
    int a=0;
    int s = 1;
    @Override
    public Integer get() {
       s=n+a<1?1:n+a;
       n=a;
       a=s;
       return s;
    }
}
