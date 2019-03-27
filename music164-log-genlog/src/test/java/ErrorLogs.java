import com.oldboy.umeng.common.AppErrorLog;

public class ErrorLogs {

    private AppErrorLog[] appErrorLogs;

    public ErrorLogs(AppErrorLog[] appErrorLogs) {
        this.appErrorLogs = appErrorLogs;
    }

    public AppErrorLog[] getAppErrorLogs() {
        return appErrorLogs;
    }

    public void setAppErrorLogs(AppErrorLog[] appErrorLogs) {
        this.appErrorLogs = appErrorLogs;
    }
}
