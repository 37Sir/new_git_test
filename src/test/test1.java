package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class test1 {
	public static void main(String[] args) {
//		steam();
//		System.out.println(5 ^ 5);
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 3);
		map.put(4, 4);
		map.put(5, 5);
		map.put(6, 6);
		System.out.println(map.floorEntry(6).getValue());
		System.out.println(map.get(7));
	}
	
	public static boolean xxx(int s) {return s > 2;}
	
	public static void futureTest() {
	    CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
	        @Override
	        public Integer get() {
	        	try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	System.out.println("1111111111");
	            return new Random().nextInt(10);
	        }
	    }).thenAccept(integer -> {
	        System.out.println(integer);
	    });
	    System.out.println("222222222");
		}
	
	
	public static void steam() {
		List<Integer> ints = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		System.out.println("ints sum is:" + ints.stream().reduce(0, (sum, item) -> sum + item));
		System.out.println(ints);
	}
}
