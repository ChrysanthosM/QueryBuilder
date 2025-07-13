package app.builder.core;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class InstancesConfig {
    @SuppressWarnings("unused")
    private final DbFieldInstances dbFieldInstances;
    @SuppressWarnings("unused")
    private final DbTableInstances dbTableInstances;
}
