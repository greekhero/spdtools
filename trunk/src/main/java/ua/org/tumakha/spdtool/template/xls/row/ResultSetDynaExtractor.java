package ua.org.tumakha.spdtool.template.xls.row;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yuriy Tumakha
 */
public class ResultSetDynaExtractor implements ResultSetExtractor<RowSetDynaClass> {

    @Override
    public RowSetDynaClass extractData(ResultSet rs) throws SQLException, DataAccessException {
        return new RowSetDynaClass(rs, false);
    }
}
