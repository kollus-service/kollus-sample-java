package com.kollus.kr.kollus_sample_java.data;
/**
 * play callback playsection vo
 * @author Yang Hyeon Deok
 * @since 2017. 7. 6.
 */
public class PlaySection {
	private int start_time =0;
	private int end_time =0;
	public int getStart_time() {
		return start_time;
	}
	public int getEnd_time() {
		return end_time;
	}
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}
	public PlaySection(int start_time, int end_time) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
	
}
