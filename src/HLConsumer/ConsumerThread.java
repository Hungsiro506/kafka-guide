package HLConsumer;

import kafka.consumer.*;

import java.util.*;


public class ConsumerThread  implements Runnable {
	private KafkaStream<byte[],byte[]> stream;
	private int threadId;
	
	
	public  ConsumerThread(KafkaStream<byte[],byte[]>  pStream, int pId ) {
		this.stream = pStream;
		this.threadId = pId;
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ConsumerIterator<byte[],byte[]> msgs = this.stream.iterator();
		while(msgs.hasNext()){
			System.out.println("Message from thread " + this.threadId + " value : "+ new String(msgs.next().message()));
			
		}
		System.out.println("Shuttdown thread id : " + this.threadId);
		
	}
}
