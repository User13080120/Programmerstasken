package com.example;

import java.util.Date;

public class QueryParse extends Query {

    private final Date dateFrom;
    private final Date dateTo;

    public QueryParse(int id, String serviceID, String questionType, String responseType, String responseDate) {
        super(serviceID, questionType, responseType);
        String[] date = responseDate.split(HYPHEN);
        this.dateFrom = parseDate(date[0], id);
        this.dateTo = (date.length > 1) ? parseDate(date[1], id) : MAX_DATE;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }
}

