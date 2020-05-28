package pacOne;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDemo {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);  
		List<Future<String>> callableList = new ArrayList<Future<String>>();  
		Callable<String> c = ()->{
			//模拟业务方法
			Thread.sleep(1000);
			return "ok";
		};
		for(int i=0;i<10;i++) {
			Future<String> f = executor.submit(c);
			callableList.add(f);
		}
		for(Future<String> future : callableList){  
            try {  
                System.out.println(future.get());
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        executor.shutdown();  
	}
}
