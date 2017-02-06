package Producer.CustomeSerializer;

import java.util.Map;
import java.nio.ByteBuffer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class CustomerSerializer implements Serializer<Customer> {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see org.apache.kafka.common.serialization.Serializer#serialize(java.lang.String, java.lang.Object)
	 * We are serializing Customer as :
	 * 4 byte int representing customerId
	 * 4 byte int representing length of customerName in UTF-8 bytes (0 if name is Null)
	 * N byte representing customerName in UTF-8
	 */
			
	public byte[] serialize(String topic, Customer data) {
		// TODO Auto-generated method stub
		
		try{
			byte[] serializedName;
			int stringSize;
			// if Customer is null, return null.
			if(data == null){
				return null;
			}else {
				/*
				 * 
				 */
				
				if(data.getName() != null){
					serializedName = data.getName().getBytes("UTF-8");
					stringSize = serializedName.length;
				}
				else{
					serializedName = new byte[0];
					stringSize = 0;
				}
			}
			ByteBuffer buffer = ByteBuffer.allocate(4+4+stringSize);
			buffer.putInt(data.getID());
			buffer.putInt(stringSize);
			buffer.put(serializedName);
			
			return buffer.array();
		}catch(Exception e){
			throw new SerializationException("Error when serializing Customer to byte[] " + e);
		}finally{
			
		}
		

	}
	
}
