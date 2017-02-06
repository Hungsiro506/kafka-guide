package ProducerWithSimplePartition;

import java.util.*;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
public class TestProducer {
	public static void main(String [] args){
		final long EVENTS = 5000;
		Random rnd = new Random();
		
		ProducerConfig config = createProducerConfig();
		Producer<String,String> producer = new Producer(config);
		
		for(long nEvents = 0; nEvents <EVENTS;nEvents++){
			
			long timestamp = new Date().getTime();
			String ip = "192.168.4." + rnd.nextInt(255);
			String msg = timestamp + ",www.example.com," + ip;
			
			KeyedMessage<String,String> payload = new KeyedMessage<String,String>("page_visits",ip,msg);
			producer.send(payload);
		}
		
		producer.close();
		System.out.println("Done");
		
		
	}
	
	private static ProducerConfig createProducerConfig(){
		Properties props = new Properties();
		props.put("metadata.broker.list", "172.17.0.3:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "ProducerWithSimplePartition.SimplePartitioner");
        props.put("request.required.acks", "1");
        return new ProducerConfig(props);
	}
	/*
	 * ZK_IP=$(sudo docker inspect --format '{{ .NetworkSettings.IPAddress }}' zookeeper)
	 * KAFKA_IP=$(sudo docker inspect --format '{{ .NetworkSettings.IPAddress }}' kafka)
	 * sudo docker run --rm ches/kafka kafka-topics.sh --create --topic test --replication-factor 1 --partitions 1 --zookeeper $ZK_IP:2181
		view comsumer :
		sudo docker run --rm ches/kafka kafka-console-consumer.sh --zookeeper &ZK_IP:2181 --topic page_visits --from-beginning
	 * */
}
