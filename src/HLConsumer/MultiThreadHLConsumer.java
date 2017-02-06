package HLConsumer;
import kafka.consumer.*;
import kafka.consumer.ConsumerConnector;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import configurations.Constants;
import kafka.javaapi.consumer.*;

public class MultiThreadHLConsumer {
	private ExecutorService executor;
	private final kafka.javaapi.consumer.ConsumerConnector consumer;
	private final String topic;
	
	public MultiThreadHLConsumer( String zookeeper,String groupId,String topic){
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(groupId,zookeeper));
		this.topic = topic;
	}

	private static ConsumerConfig createConsumerConfig(String groupId, String zookeeper){
	Properties properties = new Properties();
	properties.put("zookeeper.connect", zookeeper);
	properties.put("group.id", groupId);
    properties.put("zookeeper.session.timeout.ms", "500");
    properties.put("zookeeper.sync.time.ms", "250");
    properties.put("auto.commit.interval.ms", "1000");
	ConsumerConfig config = new ConsumerConfig(properties);		
		return config;
	}
	
	public void run(int threadCount){
		Map<String,Integer> consumerCount = new HashMap<String,Integer>();
		consumerCount.put(topic, new Integer(threadCount));
		
		Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap = consumer.createMessageStreams(consumerCount);
		
		List<KafkaStream<byte[],byte[]>> streams = consumerMap.get(topic);
		
		executor = Executors.newFixedThreadPool(threadCount);
		int threadNumberCount  = 0;
		for(final KafkaStream stream : streams){
			executor.submit(new ConsumerThread(stream,threadNumberCount));
			threadNumberCount++;
			
		}
	     /* try { // without this wait the subsequent shutdown happens immediately before any messages are delivered
	            Thread.sleep(100000000);
	        } catch (InterruptedException ie) {

	        }
	        if (consumer != null) {
	            consumer.shutdown();
	        }
	        if (executor != null) {
	            executor.shutdown();
	        }
	        */
		
	}
    public static void main(String[] args) {
        String topic = "page_visits";
        int threadCount = 5;
        MultiThreadHLConsumer multiThreadHLConsumer = new MultiThreadHLConsumer(Constants.ZOOKEEPER, "testgroup", topic);
        multiThreadHLConsumer.run(threadCount);
    }

	
}
