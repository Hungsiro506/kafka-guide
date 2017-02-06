package ProducerWithSimplePartition;
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;
public class SimplePartitioner implements Partitioner{
	
	public SimplePartitioner(VerifiableProperties props){
		
	}
	@Override
	public int partition(Object key, int numPartition) {
		// TODO Auto-generated method stub
		int partition = 0;
		String stringKey = (String) key;
		int offset = (int) stringKey.lastIndexOf('.');
		if(offset > 0){
			partition = Integer.parseInt(stringKey.substring(offset +1)) % numPartition;
		}
		/*
		 * the logic take the key, which we expect to be the IP address, find the last octet and does a modulo 
		 * operation on the number of partition defined within Kafka for the topic. The benefit of this 
		 * partitioning logic is all web visits from the same source IP end up in the same Partition. 
		 * */
		return partition;
	}
	
}
