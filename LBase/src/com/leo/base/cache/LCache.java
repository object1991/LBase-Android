package com.leo.base.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.leo.base.application.LApplication;

/**
 * 
 * @author Chen Lei
 *
 * @param <E>
 */
public class LCache<E> {

	private int cacheLimit = LApplication.getInstance().getCacheCount();

	private LinkedList<String> cacheList;

	private Map<String, E> cacheMap;

	public LCache() {
		cacheList = new LinkedList<String>();
		cacheMap = new HashMap<String, E>();
	}

	public void setCacheLimit(int num) {
		cacheLimit = num;   
	}
	
	public void put(String key, E value) {
		cacheList.offer(key);
		if(cacheList.size() > cacheLimit) {
			String mKey = cacheList.poll();
			cacheMap.remove(mKey);
		}
		cacheMap.put(key, value);
	}

	public boolean isHaveCache(String key) {
		return cacheMap.containsKey(key);
	}
	
	public E get(String key) {
		return cacheMap.get(key);
	}
	
	public void remove(String key) {
		cacheMap.remove(key);
	}

	public void clearAll() {
		cacheList.clear();
		cacheMap.clear();
	}

}
