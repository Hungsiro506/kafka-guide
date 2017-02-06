package ConsumerGroup;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;


public class ConsumerTest implements Runnable {
	private KafkaStream m_stream;
	private int m_numThread;
	public ConsumerTest(KafkaStream a_stream, int a_threadNumber){
		this.m_stream = a_stream;
		this.m_numThread = a_threadNumber;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ConsumerIterator<byte[],byte[]> it = m_stream.iterator();
		while(it.hasNext()){
			System.out.println("Thead " + m_numThread + " : " + new String(it.next().message()));
		}
		System.out.println("Shutting down thread : " + m_numThread);
	}
}
