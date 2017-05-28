package azkaban.jobExecutor;

import azkaban.utils.Props;
import org.apache.log4j.Logger;

public class KillingMeSoftlyJob implements Job {

  private final String jobid;
  private final Props props;
  private final Props jobProps;
  private final Logger log;
  private volatile boolean cancelled;

  public KillingMeSoftlyJob(String jobid, Props props, Props jobProps, Logger log) {
    this.jobid = jobid;
    this.props = props;
    this.jobProps = jobProps;
    this.log = log;
  }

  @Override
  public String getId() {
    return jobid;
  }

  @Override
  public void run() throws Exception {
    info("KillingMeSoftlyJob run starts");
    try {
      Thread.sleep(5000L);
    } catch (InterruptedException e) {
      info("KillingMeSoftlyJob sleep was interrupted");
    }
    info("KillingMeSoftlyJob sleep ended");
    if (isCanceled()) {
      info("KillingMeSoftlyJob sleeping before exiting");
      try {
        Thread.sleep(5000L);
      } catch (InterruptedException e) {
        info("KillingMeSoftlyJob sleep was interrupted");
      }
      info("KillingMeSoftlyJob throwing cancelled exception");
      throw new IllegalStateException("Job was cancelled!");
    }
    info("KillingMeSoftlyJob exiting");
  }

  private void info(String s) {
    log.info(s);
  }

  @Override
  public void cancel() throws Exception {
    this.cancelled = true;
    info("KillingMeSoftlyJob cancel was called");
  }

  @Override
  public double getProgress() throws Exception {
    return 0;
  }

  @Override
  public Props getJobGeneratedProperties() {
    return new Props();
  }

  @Override
  public boolean isCanceled() {
    return cancelled;
  }

}
