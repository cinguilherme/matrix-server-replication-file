package gcc.main.logic.utils;

import static java.lang.System.currentTimeMillis;

public class MetricProfiling {

    private String metricName;
    private Long start;
    private Long end;

    public void startMetric(String metricName) {
        this.start = currentTimeMillis();
        System.out.println(metricName + " -> INITIATED AT:" + start);
    }

    public void endMetric() {
        this.end = currentTimeMillis();
        System.out.println(metricName + " -> ENDED AT:" + end);
        System.out.println("TOTAL TIME: " + (Long) (end - start));
    }

}
