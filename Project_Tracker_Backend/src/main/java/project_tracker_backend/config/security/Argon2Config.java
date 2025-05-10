package project_tracker_backend.config.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Argon2Config {

    public Integer getMemoryUsageForEncryption() {
        long totalRAM = SystemInfo.getTotalMemory();
        int memoryCost;

        if (totalRAM > 8192) { // 8GB+
            memoryCost = 262144; // 256MB
        } else if (totalRAM > 4096) { // 4-8GB
            memoryCost = 131072; // 128MB
        } else if (totalRAM > 2048) { // 2-4GB
            memoryCost = 65536; // 64MB
        } else { // < 2GB
            memoryCost = 32768; // 32MB
        }
        return memoryCost;
    }

    /**
     * Dynamically determines Argon2 iteration count based on CPU speed.
     * Faster CPUs get more iterations, slower CPUs get fewer.
     */
    public Integer getIterationAmount() {
        int cpuSpeedMHz = SystemInfo.getCpuSpeedMHz();
        if (cpuSpeedMHz > 3000) { // High-end CPU
            return 3;
        } else { // Low-end CPU
            return 2;
        }
    }

    public Integer getParallelism() {
        return SystemInfo.getLogicalCores();
    }
}
