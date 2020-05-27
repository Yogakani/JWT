/**
 * 
 */
package com.yoga.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.hazelcast.config.Config;

/**
 * @author Yoga
 *
 */
@Configuration
public class HazlecastConfig {
	
	@Bean
	@Lazy
	public Config hazleCastConfig() {
		return new Config().setInstanceName("hazleCastInstance");
	}

}
