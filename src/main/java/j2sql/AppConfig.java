package j2sql;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import j2sql.core.DbFieldInstances;
import j2sql.core.DbTableInstances;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final DbFieldInstances dbFieldInstances;
    private final DbTableInstances dbTableInstances;
}
