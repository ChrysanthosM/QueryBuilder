package qb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import qb.builder.DbFieldInstances;
import qb.builder.DbTableInstances;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AppConfig {
    @SuppressWarnings("unused")
    private final DbFieldInstances dbFieldInstances;
    @SuppressWarnings("unused")
    private final DbTableInstances dbTableInstances;
}
