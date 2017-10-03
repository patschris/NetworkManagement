package dd.structures;

import java.util.LinkedList;
import java.util.Queue;

import dd.classes.WirelessNetView;

public class WirelessNetViewQueue {
	
	private static Queue <WirelessNetView> wirelessNetViewQueue = new LinkedList<WirelessNetView>();
	private static WirelessNetViewQueue instance = null;

	public static WirelessNetViewQueue getInstance(){
		synchronized (wirelessNetViewQueue){
			if (instance == null){
				instance = new WirelessNetViewQueue();
			}
			return instance;
		}
	}
	
	public void push(WirelessNetView wnv) {
		synchronized (wirelessNetViewQueue) {
			wirelessNetViewQueue.add(wnv);
		}
	}
	
	public void popHead(){
		synchronized (wirelessNetViewQueue) {
			wirelessNetViewQueue.remove();
		}
	}
	
	public boolean isEmpty(){
		synchronized (wirelessNetViewQueue) {
			return wirelessNetViewQueue.isEmpty();
		}
	}
	
	public String forDatabase(){
		synchronized (wirelessNetViewQueue) {
			String s = new String();
			s+="Insert into WirelessNetView values ";
			while (wirelessNetViewQueue.isEmpty()==false) {
				s+=wirelessNetViewQueue.peek().toString();
				s+=wirelessNetViewQueue.remove();
				s+=",";
			}
			s+=";";
			return s;
		}
	}
}