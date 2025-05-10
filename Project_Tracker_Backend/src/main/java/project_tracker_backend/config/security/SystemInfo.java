package project_tracker_backend.config.security;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemInfo {

    /**
     * Gets the total system RAM in megabytes.
     * @return Total RAM in MB
     */
    public static long getTotalMemory() {
        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return osBean.getTotalPhysicalMemorySize() / (1024 * 1024); // Convert bytes to MB
    }

    /**
     * Gets the number of available logical CPU cores.
     * @return Number of logical cores
     */
    public static int getLogicalCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Gets the estimated CPU speed in MHz.
     * Works on Linux and Windows.
     * @return CPU speed in MHz, or -1 if unknown
     */
    public static int getCpuSpeedMHz() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Process process;
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("wmic cpu get MaxClockSpeed");
            } else {
                process = Runtime.getRuntime().exec("lscpu | grep 'MHz'");
            }
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[^0-9]", "").trim();
                if (!line.isEmpty()) {
                    return Integer.parseInt(line);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // If detection fails
    }
}
