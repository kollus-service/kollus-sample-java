package com.kollus.kr.kollus_sample_java.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DrmCallbackRequestList implements List<DrmCallbackRequest>{
	private ArrayList<DrmCallbackRequest> requests;
	
	public DrmCallbackRequestList(){
		if(requests == null){
			requests = new ArrayList<DrmCallbackRequest>();
		}
	}

	public boolean add(DrmCallbackRequest arg0) {
		return requests.add(arg0);
	}

	public void add(int arg0, DrmCallbackRequest arg1) {
		requests.add(arg0, arg1);
	}

	public boolean addAll(Collection<? extends DrmCallbackRequest> arg0) {
		return requests.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends DrmCallbackRequest> arg1) {
		return requests.addAll(arg0, arg1);
	}

	public void clear() {
		requests.clear();
	}

	public boolean contains(Object arg0) {	
		return requests.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return requests.containsAll(arg0);
	}

	public DrmCallbackRequest get(int arg0) {
		return requests.get(arg0);
	}

	public int indexOf(Object arg0) {
		return requests.indexOf(arg0);
	}

	public boolean isEmpty() {
		return requests.isEmpty();
	}

	public Iterator<DrmCallbackRequest> iterator() {
		return requests.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return requests.lastIndexOf(arg0);
	}

	public ListIterator<DrmCallbackRequest> listIterator() {
		return requests.listIterator();
	}

	public ListIterator<DrmCallbackRequest> listIterator(int arg0) {
		return requests.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return requests.remove(arg0);
	}

	public DrmCallbackRequest remove(int arg0) {
		return requests.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return requests.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return requests.retainAll(arg0);
	}

	public DrmCallbackRequest set(int arg0, DrmCallbackRequest arg1) {
		return requests.set(arg0, arg1);
	}

	public int size() {
		return requests.size();
	}

	public List<DrmCallbackRequest> subList(int arg0, int arg1) {
		return requests.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return requests.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return requests.toArray(arg0);
	}

	public static DrmCallbackRequestList getDrmCallbackRequestFromMap(List<HashMap<String, Object>> requestMapList) throws UnsupportedEncodingException {
		DrmCallbackRequestList list = new DrmCallbackRequestList();
		for(HashMap<String, Object> map : requestMapList){
			list.add(DrmCallbackRequest.getDrmCallbackRequestFromMap(map));
		}
		return list;
	}
	
}
