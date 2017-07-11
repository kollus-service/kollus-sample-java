package com.kollus.kr.kollus_sample_java.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DrmCallbackResponseList implements List<DrmCallbackResponse> {
	private ArrayList<DrmCallbackResponse> responses;

	public DrmCallbackResponseList() {
		if (responses == null) {
			responses = new ArrayList<DrmCallbackResponse>();
		}
	}

	public boolean add(DrmCallbackResponse arg0) {
		return responses.add(arg0);
	}

	public void add(int arg0, DrmCallbackResponse arg1) {
		responses.add(arg0, arg1);
	}

	public boolean addAll(Collection<? extends DrmCallbackResponse> arg0) {
		return responses.addAll(arg0);
	}

	public boolean addAll(int arg0, Collection<? extends DrmCallbackResponse> arg1) {
		return responses.addAll(arg0, arg1);
	}

	public void clear() {
		responses.clear();
	}

	public boolean contains(Object arg0) {
		return responses.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return responses.containsAll(arg0);
	}

	public DrmCallbackResponse get(int arg0) {
		return responses.get(arg0);
	}

	public int indexOf(Object arg0) {
		return responses.indexOf(arg0);
	}

	public boolean isEmpty() {
		return responses.isEmpty();
	}

	public Iterator<DrmCallbackResponse> iterator() {
		return responses.iterator();
	}

	public int lastIndexOf(Object arg0) {
		return responses.lastIndexOf(arg0);
	}

	public ListIterator<DrmCallbackResponse> listIterator() {
		return responses.listIterator();
	}

	public ListIterator<DrmCallbackResponse> listIterator(int arg0) {
		return responses.listIterator(arg0);
	}

	public boolean remove(Object arg0) {
		return responses.remove(arg0);
	}

	public DrmCallbackResponse remove(int arg0) {
		return responses.remove(arg0);
	}

	public boolean removeAll(Collection<?> arg0) {
		return responses.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) {
		return responses.retainAll(arg0);
	}

	public DrmCallbackResponse set(int arg0, DrmCallbackResponse arg1) {
		return responses.set(arg0, arg1);
	}

	public int size() {
		return responses.size();
	}

	public List<DrmCallbackResponse> subList(int arg0, int arg1) {
		return responses.subList(arg0, arg1);
	}

	public Object[] toArray() {
		return responses.toArray();
	}

	public <T> T[] toArray(T[] arg0) {
		return responses.toArray(arg0);
	}

	public String toBody() throws JsonProcessingException {
		List<HashMap<String, Object>> mapList = null;
		if (this.responses.size() > 0) {
			mapList = new ArrayList<HashMap<String, Object>>();
			for(DrmCallbackResponse response : this.responses){
				mapList.add(response.toMapFromObject());
			}
		}
		String result = null;
		if (mapList != null) {
			HashMap<String, Object> resultMapList = new HashMap<String, Object>();
			resultMapList.put("data", mapList);
			result = (new ObjectMapper()).writeValueAsString(resultMapList);
		}
		return result;
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		return builder.toString();
	}
}
