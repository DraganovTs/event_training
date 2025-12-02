package org.homemade.common.config;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// ... existing code ...
@Configuration
public class AxonXStreamConfig {

    private static final String[] TRUSTED_PACKAGES = {"org.homemade.**"};

    @Bean
    @Primary
    public Serializer xStreamSerializer() {
        XStream xstream = configureXStream();
        return XStreamSerializer.builder()
                .xStream(xstream)
                .build();
    }

    private XStream configureXStream() {
        XStream xstream = new XStream();
        XStream.setupDefaultSecurity(xstream);
        xstream.addPermission(new WildcardTypePermission(TRUSTED_PACKAGES));
        return xstream;
    }
}
