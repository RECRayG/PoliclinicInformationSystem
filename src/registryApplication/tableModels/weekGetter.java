package registryApplication.tableModels;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class weekGetter
{
    private final static ZoneId TZ = ZoneId.of("Pacific/Auckland");
    private final static String format = "EEE, dd-MM";
    private final int offset;
    private LocalDate currentDay;

    private LocalDate pn;
    private LocalDate vt;
    private LocalDate sr;
    private LocalDate cht;
    private LocalDate pt;
    private LocalDate sb;
    private LocalDate vs;

    public weekGetter(int offset)
    {
        this.offset = offset;
        this.currentDay = LocalDate.now();
        switch(currentDay.getDayOfWeek().getValue())
        {
            case 1:
                this.pn = currentDay.plusDays(offset);
                break;
            case 2:
                this.vt = currentDay.plusDays(offset);
                break;
            case 3:
                this.sr = currentDay.plusDays(offset);
                break;
            case 4:
                this.cht = currentDay.plusDays(offset);
                break;
            case 5:
                this.pt = currentDay.plusDays(offset);
                break;
            case 6:
                this.sb = currentDay.plusDays(offset);
                break;
            case 7:
                this.vs = currentDay.plusDays(offset);
                break;
        }

        if(this.pn != null)
        {
            this.vt = this.pn.plusDays(1);
            this.sr = this.vt.plusDays(1);
            this.cht = this.sr.plusDays(1);
            this.pt = this.cht.plusDays(1);
            this.sb = this.pt.plusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.vt != null)
        {
            this.pn = this.vt.minusDays(1);
            this.sr = this.vt.plusDays(1);
            this.cht = this.sr.plusDays(1);
            this.pt = this.cht.plusDays(1);
            this.sb = this.pt.plusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.sr != null)
        {
            this.vt = this.sr.minusDays(1);
            this.pn = this.vt.minusDays(1);
            this.cht = this.sr.plusDays(1);
            this.pt = this.cht.plusDays(1);
            this.sb = this.pt.plusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.cht != null)
        {
            this.sr = this.cht.minusDays(1);
            this.vt = this.sr.minusDays(1);
            this.pn = this.vt.minusDays(1);
            this.pt = this.cht.plusDays(1);
            this.sb = this.pt.plusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.pt != null)
        {
            this.cht = this.pt.minusDays(1);
            this.sr = this.cht.minusDays(1);
            this.vt = this.sr.minusDays(1);
            this.pn = this.vt.minusDays(1);
            this.sb = this.pt.plusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.sb != null)
        {
            this.pt = this.sb.minusDays(1);
            this.cht = this.pt.minusDays(1);
            this.sr = this.cht.minusDays(1);
            this.vt = this.sr.minusDays(1);
            this.pn = this.vt.minusDays(1);
            this.vs = this.sb.plusDays(1);
        }
        else if(this.vs != null)
        {
            this.sb = this.vs.minusDays(1);
            this.pt = this.sb.minusDays(1);
            this.cht = this.pt.minusDays(1);
            this.sr = this.cht.minusDays(1);
            this.vt = this.sr.minusDays(1);
            this.pn = this.vt.minusDays(1);
        }
    }

    public ArrayList<LocalDate> getWeeksDays()
    {
        ArrayList<LocalDate> weeksDays = new ArrayList();

        weeksDays.add(this.pn);
        weeksDays.add(this.vt);
        weeksDays.add(this.sr);
        weeksDays.add(this.cht);
        weeksDays.add(this.pt);
        weeksDays.add(this.sb);
        weeksDays.add(this.vs);

        return weeksDays;
    }

    public int whatWeek()
    {
        if(offset == 0)
        {
            return 1;
        }
        else if(offset == 7)
        {
            return 2;
        }
        else if(offset == 14)
        {
            return 3;
        }
        else return 1;
    }

    public boolean isPn()
    {
        return currentDay.isAfter(this.vs);
    }

    public boolean isVt()
    {
        return currentDay.isAfter(this.pn);
    }

    public boolean isSr()
    {
        return currentDay.isAfter(this.vt);
    }

    public boolean isCht()
    {
        return currentDay.isAfter(this.sr);
    }

    public boolean isPt()
    {
        return currentDay.isAfter(this.cht);
    }

    public boolean isSb()
    {
        return currentDay.isAfter(this.pt);
    }

    public boolean isVs()
    {
        return currentDay.isAfter(this.sb);
    }

    public LocalDate getPnDate()
    {
        return this.pn;
    }

    public LocalDate getVtDate()
    {
        return this.vt;
    }

    public LocalDate getSrDate()
    {
        return this.sr;
    }

    public LocalDate getChtDate()
    {
        return this.cht;
    }

    public LocalDate getPtDate()
    {
        return this.pt;
    }

    public LocalDate getSbDate()
    {
        return this.sb;
    }

    public LocalDate getVsDate()
    {
        return this.vs;
    }

    public String getPn()
    {
        //return Date.from(pn.atStartOfDay().atZone(TZ).toInstant());
        return new SimpleDateFormat(format).format(Date.from(pn.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getVt()
    {
        return new SimpleDateFormat(format).format(Date.from(vt.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getSr()
    {
        return new SimpleDateFormat(format).format(Date.from(sr.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getCht()
    {
        return new SimpleDateFormat(format).format(Date.from(cht.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getPt()
    {
        return new SimpleDateFormat(format).format(Date.from(pt.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getSb()
    {
        return new SimpleDateFormat(format).format(Date.from(sb.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public String getVs()
    {
        return new SimpleDateFormat(format).format(Date.from(vs.atStartOfDay().plusDays(1).atZone(TZ).toInstant()));
    }

    public int getOffset()
    {
        return this.offset;
    }
}
