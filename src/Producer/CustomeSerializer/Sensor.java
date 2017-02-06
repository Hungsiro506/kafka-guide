package Producer.CustomeSerializer;

public class Sensor {
	private final String id ;
	private final Type type;
	public enum Type {
		TEMP,
		LIGHT,
		HUMIDITY
	}
	public Sensor(String id, Type type){
		this.id = id;
		this.type = type;
	}
	public Type getType(){
		return this.type;
	}	
	public String getId(){
		return this.id;
	}
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Sensor sensor = (Sensor) o;
		
		if(!id.equals(sensor.getId())) return false;
		return sensor.type == type;
	}
	@Override
	public int hashCode(){
		int result = id.hashCode();
		result = result*31 +type.hashCode();
		return result;
	}
	public static<T> T guardNotNull(T o){
		if(o == null){
			throw new IllegalArgumentException("Parameters can't be null");
		}
		return o;
	}
}
