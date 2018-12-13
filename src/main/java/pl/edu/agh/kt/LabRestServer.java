package pl.edu.agh.kt;

import java.io.IOException;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class LabRestServer extends ServerResource {
	protected static Logger log = LoggerFactory.getLogger(LabRestServer.class);

	@Get("json")
	public String handleGet() throws JsonProcessingException {
		log.info("handleGet");
		return serialize(new Timeout(Flows.getIdleTimeout(), Flows.getHardTimeout()));
	}

	@Post("json")
	public String handlePost(String text) throws JsonProcessingException, IOException {
		log.info("handlePost");
		Timeout timeout = new Timeout();
		timeout = deserialize(text, Timeout.class);
		Flows.setIdleTimeout(timeout.getIdleTimeout());
		Flows.setHardTimeout(timeout.getHardTimeout());

		return serialize(timeout);
	}

	private static final ObjectMapper mapper;
}
