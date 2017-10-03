package dd.structures;

import java.util.LinkedList;
import java.util.Queue;

import dd.classes.Wireshark;

public class WiresharkQueue {
	private static Queue <Wireshark> wiresharkQueue  = new LinkedList<Wireshark >();
	private static WiresharkQueue  instance = null;
	
	public static WiresharkQueue  getInstance(){
		synchronized (wiresharkQueue ){
			if (instance == null){
				instance = new WiresharkQueue();
			}
			return instance;
		}
	}
	
	public void push(Wireshark w) {
		synchronized (wiresharkQueue) {
			wiresharkQueue.add(w);
		}
	}
	
	public void popHead(){
		synchronized (wiresharkQueue) {
			wiresharkQueue.remove();
		}
	}
	
	public boolean isEmpty(){
		synchronized (wiresharkQueue) {
			return wiresharkQueue.isEmpty();
		}
	}
}