package phase3.core.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Session {
	
	public final String token;
	private Map<String, Object> attributes = new HashMap<>();
	private long lastAccessed = System.currentTimeMillis();
	@Value("${session.max.inactive.interval:1}")
	private long maxInactiveInterval;
	private static final int TOKEN_MAX_LENGTH = 15;
	
	{
		this.token = UUID.randomUUID().toString().replace("-", "").substring(0, TOKEN_MAX_LENGTH);
		resetLastAccess();
	}
	
	@PostConstruct
	private void init() {
		this.maxInactiveInterval = TimeUnit.MINUTES.toMillis(maxInactiveInterval);
	}

	public void resetLastAccess() {
		this.lastAccessed = System.currentTimeMillis();
	}
	
	
	public long getLastAccessed() {
		return lastAccessed;
	}

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}
	
	
	

}
