package org.homemade.common.config;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonXStreamConfig {
    @Bean
    @Primary
    public Serializer xStreamSerializer() {
        XStream xstream = new XStream();

        XStream.setupDefaultSecurity(xstream);

        xstream.addPermission(new WildcardTypePermission(new String[]{
                "org.homemade.**"
        }));

        return XStreamSerializer.builder()
                .xStream(xstream)
                .build();
    }
}
