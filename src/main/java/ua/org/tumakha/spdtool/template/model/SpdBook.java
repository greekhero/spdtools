package ua.org.tumakha.spdtool.template.model;

import ua.org.tumakha.util.StrUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Yuriy Tumakha
 */
public class SpdBook {

    private List<Quarter> quarters = new ArrayList<Quarter>();
    private Quarter lastQuarter;

    public void addDay(SpdBookDay spdBookDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(spdBookDay.getDate());
        int month = calendar.get(Calendar.MONTH) + 1;
        int lastQuarterNumber = lastQuarter == null ? (int) Math.ceil((double) month / 3) - 1 : lastQuarter.getNumber();
        if (month > lastQuarterNumber * 3) {
            spdBookDay.setFirst(lastQuarter == null);
            spdBookDay.setFirstInQuarter(true);
            lastQuarter = new Quarter(lastQuarterNumber + 1, month);
            quarters.add(lastQuarter);
        }
        lastQuarter.addDay(spdBookDay, month);
    }

    public List<Quarter> getQuarters() {
        return quarters;
    }

    public class Quarter {
        private Integer number;
        private List<Month> months = new ArrayList<Month>();
        private Month lastMonth;

        public Quarter(int number, int month) {
            this.number = number;
            addMonth(month);
        }

        public void addDay(SpdBookDay spdBookDay, int month) {
            if (month > lastMonth.getNumber()) {
                addMonth(month);
            }
            lastMonth.addDay(spdBookDay);
        }

        public void addMonth(int month) {
            lastMonth = new Month(month);
            months.add(lastMonth);
        }

        public Integer getNumber() {
            return number;
        }

        public List<Month> getMonths() {
            return months;
        }
    }

    public class Month {
        private Integer month;
        private List<SpdBookDay> days = new ArrayList<SpdBookDay>();

        public Month(int month) {
            this.month = month;
        }

        public Integer getNumber() {
            return month;
        }

        public String getMonth() {
            return StrUtil.formatUAMonth(month);
        }

        public void addDay(SpdBookDay day) {
            day.setFirstInMonth(days.size() == 0);
            days.add(day);
        }

        public List<SpdBookDay> getDays() {
            return days;
        }
    }

}