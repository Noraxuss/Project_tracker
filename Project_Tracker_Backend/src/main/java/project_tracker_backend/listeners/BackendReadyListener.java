package project_tracker_backend.listeners;

import lombok.NonNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BackendReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final String FLAG_FILE_PATH = "flags/backend_ready.flag";

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        CreateFlag.createFlag(FLAG_FILE_PATH);
    }
}
