package dd.structures;

import java.util.LinkedList;
import java.util.Queue;

import dd.classes.XirrusWifiInspector;

public class XirrusWifiInspectorQueue {
	private static Queue <XirrusWifiInspector> xirrusWifiInspectorQueue = new LinkedList<XirrusWifiInspector>();
	private static XirrusWifiInspectorQueue instance = null;
	
	public static XirrusWifiInspectorQueue getInstance(){
		synchronized (xirrusWifiInspectorQueue){
			if (instance == null){
				instance = new XirrusWifiInspectorQueue();
			}
			return instance;
		}
	}
	
	public void push(XirrusWifiInspector xwi) {
		synchronized (xirrusWifiInspectorQueue) {
			xirrusWifiInspectorQueue.add(xwi);
		}
	}
	
	public XirrusWifiInspector takeFirst(){
		synchronized (xirrusWifiInspectorQueue) {
			return xirrusWifiInspectorQueue.peek();
		}
	}
	
	public void removeHead(){
		synchronized (xirrusWifiInspectorQueue) {
			xirrusWifiInspectorQueue.remove();
		}
	}
	
	public boolean isEmpty(){
		synchronized (xirrusWifiInspectorQueue) {
			return xirrusWifiInspectorQueue.isEmpty();
		}
	}
}