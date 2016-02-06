package org.ftcbootstrap.demos;


import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.utils.ErrorUtil;

public class TelemetryTest extends ActiveOpMode {

  private int cnt;

  protected void onInit() {

  }

  @Override
  protected void activeLoop() throws InterruptedException {
    if ( cnt > 15) {
      getTelemetryUtil().addData(cnt + "C", "message");
      getTelemetryUtil().addData(cnt + "B", "message");
      getTelemetryUtil().addData(cnt + "A", "message");
      cnt--;

    }
    else{
      if ( cnt == 15) {
        getTelemetryUtil().addData("17B", "message3");
        cnt--;
      }
    }
  }


  @Override
  public void runOpMode() throws InterruptedException {

    try {
      onInit();
    }
    catch (Throwable e ) {
      ErrorUtil.handleCatchAllException(e, getTelemetryUtil());
    }

    waitForStart();
    getTelemetryUtil().reset();

    cnt = 20;
    while (opModeIsActive()) {

      try {
        activeLoop();
      }
      catch (Throwable e ) {
        ErrorUtil.handleCatchAllException(e, getTelemetryUtil());
      }

      getTelemetryUtil().sendTelemetry();

      waitOneFullHardwareCycle();
    }
  }







}
