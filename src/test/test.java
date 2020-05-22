package test;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class test {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newSingleThreadExecutor();	
	    CompletableFuture<Integer> f = CompletableFuture.supplyAsync(new MySupplier(), exec);
	    System.out.println(f.isDone()); // False
	    CompletableFuture<Integer> f2 = f.thenApply(new PlusOne());
	    System.out.println("11111111111111");
	    exec.execute(new th());
	    System.out.println(f2.get()); // Waits until the "calculation" is done, then prints 2
	    
	    Random r = new Random();
	    System.out.println(r.nextInt(10000));
	}

	static class MySupplier implements Supplier<Integer> {

	    @Override
	    public Integer get() {
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            //Do nothing
	        }
	        return 1;
	    }
	}
	
	static class PlusOne implements Function<Integer, Integer> {

	    @Override
	    public Integer apply(Integer x) {
	        return x + 1;
	    }
	}
	
	static class th implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("0000000000000");
		}
		
	}
}
