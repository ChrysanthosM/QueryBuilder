package qb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import qb.core.DbFieldInstances;
import qb.core.DbTableInstances;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final DbFieldInstances dbFieldInstances;
    private final DbTableInstances dbTableInstances;
}
