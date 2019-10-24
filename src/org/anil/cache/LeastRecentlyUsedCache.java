package org.anil.cache;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LeastRecentlyUsedCache {
	
	Deque<Integer> queue=new LinkedList<Integer>();
	Set<Integer> keys= new HashSet<Integer>();

	public static void main(String[] args) {
		
	}

}
