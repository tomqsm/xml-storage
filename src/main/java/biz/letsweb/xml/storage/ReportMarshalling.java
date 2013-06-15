/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.letsweb.xml.storage;

import biz.letsweb.xml.generated.reporting.ObjectFactory;
import biz.letsweb.xml.generated.reporting.Report;
import java.util.List;

/**
 *
 * @author Tomasz
 */
public class ReportMarshalling {

    private ObjectFactory objectFactory;
    private final Report report;
    

    public ReportMarshalling() {
        objectFactory = new ObjectFactory();
        report = objectFactory.createReport();
        make();
    }

    public final void make() {
//        List<Report.Project> projects = new ArrayList<Report.Project>();
        Report.Project project = null;
        final List<Report.Project> projects = objectFactory.createReport().getProject();
        project = objectFactory.createReportProject();
        project.setName("mój projekt");
        Report.Stats stats = objectFactory.createReportStats();
        stats.setBlockers(12);
        stats.setCritical(4);
        report.setStats(stats);
        Report.Project.Week week = objectFactory.createReportProjectWeek();
        week.setNumber(24);
        week.setBlockers(12);
        week.setCritical(13);
        week.setOverall(123.4);
        week.setUnit(222.3);
        project.getWeek().add(week);
        projects.add(project);

        report.getProject().add(project);

    }

    public Report getReport() {
        return report;
    }
    
}
