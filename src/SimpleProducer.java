//import util.properties packages
import java.util.Properties;
import java.util.concurrent.ExecutionException;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.Callback;
//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

//Create java class named “SimpleProducer”
public class SimpleProducer {
	
   private static  Producer<String, String> producer;
   public static void main(String[] args) throws Exception{
      
      // Check arguments length value
     
      //Assign topicName to string variable
      String topicName = "test111016";
      
      // create instance for properties to access producer configs   
      Properties props = new Properties();
      
      //Assign localhost id
      props.put("bootstrap.servers", "172.17.0.3:9092"); //172.17.0.3 : docker KAFKA IP
      //props.put("bootstrap.servers", "localhost:9092");
      
      //Set acknowledgements for producer requests.      
      props.put("acks", "all");
      
      //If the request fails, the producer can automatically retry,
      props.put("retries", 0);
      
      //Specify buffer size in config
      props.put("batch.size", 16384);
      
      //Reduce the no of requests less than 0   
      props.put("linger.ms", 1);
      
      //The buffer.memory controls the total amount of memory available to the producer for buffering.   
      props.put("buffer.memory", 33554432);
      
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
         
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      
      producer = new KafkaProducer
         <String, String>(props);
            
      //for(int i = 0; i < 10; i++)

      fireAndForgetSend(topicName);
      
   }
   private static void fireAndForgetSend(String topicName){
	   System.out.println("  Fire and forget sending a message ");
	   int i =0;
	      while(true){
	    	  producer.send(new ProducerRecord<String, String>(topicName, 
	            Integer.toString(i), Integer.toString(i)));
	          System.out.println("Message sent successfully : " + i);
	          i++;
	      }
   }
   private static void synchronousSend(String topicName){
	   System.out.println("Synchronous sending a message  ");
	   int i  = 0;
	   while(true){
		   try {
			producer.send(new ProducerRecord<String,String>(topicName,
					   Integer.toString(i),Integer.toString(i))).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			if(e !=  null){
			e.printStackTrace();}
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			if(e !=  null){
				e.printStackTrace();}
		}
		   
	   }
   }
   private static  void asynchronoudSend(String topicName){
	   System.out.println("Asynchronous sending   a  message");
		   int i  = 0;
		   while(true){  
				producer.send(new ProducerRecord<String,String>(topicName,
						   Integer.toString(i),Integer.toString(i)),new DemoProducerCallBack());
		   }
   }
   private static class DemoProducerCallBack implements Callback{
	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception e) {
		// TODO Auto-generated method stub
		if( e!= null ){
			e.printStackTrace();
		}
		else{
			System.out.println("Callback return a RecordMetadata Object at:" + recordMetadata.timestamp());
		}
	}
	   
   }
}