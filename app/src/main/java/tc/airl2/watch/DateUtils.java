package tc.airl2.watch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class DateUtils {
    /**
     * 获取当前日期的字符串表示，格式为 "yyyy-MM-dd 周w"
     *
     * @return 格式化后的日期字符串
     */
    public static String getCurrentDateString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = dateFormat.format(calendar.getTime());
        String weekDay = getWeekDayString(calendar.get(Calendar.DAY_OF_WEEK));
        return dateStr + " 周" + weekDay;
    }

    /**
     * 根据数字获取对应的星期几的字符串
     *
     * @param dayOfWeek 数字表示的星期几（1-7，1代表周日）
     * @return 星期几的字符串
     */
    private static String getWeekDayString(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "未知";
        }
    }
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }
}
