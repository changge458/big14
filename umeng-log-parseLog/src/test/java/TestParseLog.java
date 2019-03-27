import com.oldboy.umeng.constant.Constants;
import com.oldboy.umeng.util.ParseKeyUtil;
import com.oldboy.umeng.util.PropUtil;

import java.util.List;

public class TestParseLog {
    public static void main(String[] args) {

        String line = "1553310769.229#192.168.79.1#1553394800819#200#{\\\"appChannel\\\":\\\"appstore\\\",\\\"appErrorLogs\\\":[{\\\"createdAtMs\\\":1552068670819,\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"logType\\\":\\\"error\\\"},{\\\"createdAtMs\\\":1555374602411,\\\"errorBrief\\\":\\\"at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)\\\",\\\"errorDetail\\\":\\\"at cn.lift.dfdfdf.control.CommandUtil.getInfo(CommandUtil.java:67) at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) at java.lang.reflect.Method.invoke(Method.java:606)\\\",\\\"logType\\\":\\\"error\\\"}],\\\"appEventLogs\\\":[{\\\"createdAtMs\\\":1554253422411,\\\"eventDurationSecs\\\":\\\"40\\\",\\\"eventId\\\":\\\"autoImport\\\",\\\"logType\\\":\\\"event\\\"},{\\\"createdAtMs\\\":1555335347411,\\\"eventDurationSecs\\\":\\\"250\\\",\\\"eventId\\\":\\\"popmenu\\\",\\\"logType\\\":\\\"event\\\"},{\\\"createdAtMs\\\":1551875361819,\\\"eventDurationSecs\\\":\\\"3000\\\",\\\"eventId\\\":\\\"autoImport\\\",\\\"logType\\\":\\\"event\\\"}],\\\"appId\\\":\\\"gaodemap\\\",\\\"appPageLogs\\\":[{\\\"createdAtMs\\\":1551344835115,\\\"logType\\\":\\\"page\\\",\\\"nextPage\\\":\\\"main.html\\\",\\\"pageId\\\":\\\"list.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"0\\\"},{\\\"createdAtMs\\\":1553766883115,\\\"logType\\\":\\\"page\\\",\\\"nextPage\\\":\\\"main.html\\\",\\\"pageId\\\":\\\"list.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"}],\\\"appPlatform\\\":\\\"ios\\\",\\\"appStartupLogs\\\":[{\\\"carrier\\\":\\\"中国电信\\\",\\\"country\\\":\\\"china\\\",\\\"createdAtMs\\\":1551615437819,\\\"logType\\\":\\\"startup\\\",\\\"network\\\":\\\"3g\\\",\\\"province\\\":\\\"shandong\\\",\\\"screenSize\\\":\\\"960 * 640\\\"}],\\\"appUsageLogs\\\":[{\\\"createdAtMs\\\":1551685341819,\\\"logType\\\":\\\"usage\\\",\\\"singleDownloadTraffic\\\":\\\"3300\\\",\\\"singleUploadTraffic\\\":\\\"128\\\",\\\"singleUseDurationSecs\\\":\\\"456\\\"}],\\\"appVersion\\\":\\\"2.0.0\\\",\\\"brand\\\":\\\"苹果\\\",\\\"deviceId\\\":\\\"dev000120\\\",\\\"deviceStyle\\\":\\\"iPhon 8\\\",\\\"osType\\\":\\\"ios8\\\",\\\"tenantId\\\":\\\"tnt501\\\"}";

        String tablename = PropUtil.getValue(Constants.STARTUP_TABLE_NAME);

        List<Object[]> list = ParseKeyUtil.parseKey(line, tablename);


        System.out.println("");

    }
}
