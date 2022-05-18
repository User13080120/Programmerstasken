package com.example;

import java.util.Date;

public class DateLine extends Query {

    private final Date responseDate;
    private final int waitingTime;

    public DateLine(int id, String serviceID, String questionType, String responseType, String responseDate, String waitingTime) {
        super(serviceID, questionType, responseType);
        this.responseDate = parseDate(responseDate, id);
        this.waitingTime = Integer.parseInt(waitingTime);
    }

    public boolean isMatch(QueryParse queryParse) {
        if (queryParse == null) {
            return false;
        }
        String service = toPattern(queryParse.getServiceID());
        String question = toPattern(queryParse.getQuestionType());
        String response = queryParse.getResponseType();
        Date from = queryParse.getDateFrom();
        Date to = queryParse.getDateTo();

        return getServiceID().matches(service) &&
                getQuestionType().matches(question) &&
                getResponseType().equals(response) &&
                getResponseDate().compareTo(from) >= 0 &&
                getResponseDate().compareTo(to) < 0;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

}
