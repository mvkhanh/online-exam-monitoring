package pbl4.Server.Utils;

import java.sql.ResultSet;

public interface RowMapper<T> {
	T mapRow(ResultSet rs) throws Exception;
}
