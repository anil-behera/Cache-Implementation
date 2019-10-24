package org.anil.cache;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeastFrequentUsedCache {
	
	class CacheData{
		Object data;
		int frequency;
		
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		CacheData(Object data,int freq){
			this.data=data;
			this.frequency=freq;
		}
		@Override
		public String toString() {
			return this.data.toString() + " Freq:" +this.frequency;
		}
	}
	class CacheDataComparatorByFreq implements Comparator<CacheData>{
		@Override
		public int compare(CacheData o1, CacheData o2) {
			return o1.frequency-o2.frequency;
		}
	}
	
	private final int CAPACITY;  
	private Map<Integer,CacheData> cachingData;
	LeastFrequentUsedCache(int capacity) {
		CAPACITY=capacity;
		cachingData=new ConcurrentHashMap<Integer, CacheData>();
	}
	
	private void addToCache(Integer key,CacheData data) {
		if(isFull()) {
			removeLeastUsed();
		}
		this.cachingData.put(key,data);
	}

	
	private void removeLeastUsed() {
		Integer key = this.cachingData.entrySet()
									  .stream()
									  .min(Map.Entry.comparingByValue(new CacheDataComparatorByFreq()))
									  .get()
									  .getKey();
		this.cachingData.remove(key);
	}

	private boolean isFull() {
		return this.cachingData.size()==this.CAPACITY;
	}
	
	private Object getFromCache(int key) {
		CacheData cacheData = this.cachingData.get(key);
		cacheData.setFrequency(cacheData.frequency+1);
		this.cachingData.put(key, cacheData);
		return null;
	}
	
	public void add(int key,Object data) {
		CacheData temp=new CacheData(data,0);
		addToCache(key, temp);
	}
	public Object get(int key) {
		if(cachingData.containsKey(key)) {
			return getFromCache(key);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String s1="Anil";
		String s2="Litu";
		String s3="Chintu";
		String s4="Asit";
		String s5="Behera";
		String s6="Ramesh";
		String s7="Urmila";
		
		LeastFrequentUsedCache cache= new LeastFrequentUsedCache(4);
		cache.add(1, s1);
		cache.add(2, s2);
		cache.add(3, s3);
		cache.add(4, s4);
		System.out.println(cache.cachingData);
		cache.get(1);
		cache.get(2);
		cache.get(3);
		System.out.println("After freq increase");
		System.out.println(cache.cachingData);
		cache.add(5, s5);
		System.out.println("After Adding s5");
		System.out.println(cache.cachingData);
		cache.get(5);
		System.out.println("After getting s5");
		System.out.println(cache.cachingData);
		System.out.println("After Adding s6 and s7");
		cache.add(6, s6);
		cache.get(6);
		cache.add(7, s7);
		System.out.println(cache.cachingData);
	}

}
