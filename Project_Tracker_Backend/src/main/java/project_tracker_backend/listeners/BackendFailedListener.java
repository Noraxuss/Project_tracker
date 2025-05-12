package project_tracker_backend.listeners;

import lombok.NonNull;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BackendFailedListener implements ApplicationListener<ApplicationFailedEvent> {

    private static final String FLAG_FILE_PATH = "flags/backend_failed.flag";

    @Override
    public void onApplicationEvent(@NonNull ApplicationFailedEvent event) {
        CreateFlag.createFlag(FLAG_FILE_PATH);
    }
}
