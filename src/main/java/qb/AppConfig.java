package qb;

import qb.builder.DbFieldInstances;
import qb.builder.DbTableInstances;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final DbFieldInstances dbFieldInstances;
    private final DbTableInstances dbTableInstances;
}
