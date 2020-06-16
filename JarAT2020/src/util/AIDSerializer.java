package util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import agents.AID;

public class AIDSerializer extends JsonSerializer<AID> {

	ObjectMapper om = new ObjectMapper();
	
	@Override
	public void serialize(AID value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		StringWriter writer = new StringWriter();
        om.writeValue(writer, value);
        gen.writeFieldName(writer.toString());
		
	}

}
