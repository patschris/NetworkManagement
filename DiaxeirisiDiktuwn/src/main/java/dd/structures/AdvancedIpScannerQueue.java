package dd.structures;

import java.util.LinkedList;
import java.util.Queue;

import dd.classes.AdvancedIpScanner;


public class AdvancedIpScannerQueue {

	private static Queue <AdvancedIpScanner> ipScannerQueue = new LinkedList<AdvancedIpScanner>();
	private static AdvancedIpScannerQueue instance = null;
	
	public static AdvancedIpScannerQueue getInstance(){
		synchronized (ipScannerQueue){
			if (instance == null){
				instance = new AdvancedIpScannerQueue();
			}
			return instance;
		}
	}
	
	public void push(AdvancedIpScanner is) {
		synchronized (ipScannerQueue) {
			ipScannerQueue.add(is);
		}
	}
	
	public void popHead(){
		synchronized (ipScannerQueue) {
			ipScannerQueue.remove();
		}
	}
	
	public boolean isEmpty(){
		synchronized (ipScannerQueue) {
			return ipScannerQueue.isEmpty();
		}
	}
}