package no.bouvet.topicmap.core;

import net.ontopia.topicmaps.query.core.QueryResultIF;

public interface RowMapper {
	public void addRow(QueryResultIF qr);

	public Object getResult();
}
