package ua.org.tumakha.spdtool.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Yuriy Tumakha
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1308009705873172345L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reportId;

    @Column(length = 255)
    private String name;

    @Column(length = 1000, nullable = false)
    private String sql;

    @Column(length = 50)
    private String template;

    private int sortOrder;

    private boolean enabled;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int orderBy) {
        this.sortOrder = orderBy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
