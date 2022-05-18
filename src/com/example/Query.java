package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Query {

    public static final String SPACE = " ";
    public static final String ASTERISK = "*";
    public static final String MATCH_ANY = ".*";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String TIME_LINE = "C";
    public static final String QUERY_LINE = "D";
    public static final String HYPHEN = "-";
    public static final Date MAX_DATE = new Date(Long.MAX_VALUE);
    public static final int LINE_TYPE = 0;
    public static final int SERVICE_ID = 1;
    public static final int QUESTION_TYPE = 2;
    public static final int RESPONSE_TYPE = 3;
    public static final int RESPONSE_DATE = 4;
    public static final int WAITING_TIME = 5;
    private final String serviceID;
    private final String questionType;
    private final String responseType;
    private static final List<DateLine> DATE_LINES = new ArrayList<>();
    private static final StringBuilder output = new StringBuilder();

    public Query(String serviceID, String questionType, String responseType) {
        this.serviceID = serviceID;
        this.questionType = questionType;
        this.responseType = responseType;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getResponseType() {
        return responseType;
    }

    public static String parse(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            String[] fields = input.get(i).split(SPACE);
            if (fields[LINE_TYPE].equals(TIME_LINE)) {
                DATE_LINES.add(new DateLine(i, fields[SERVICE_ID], fields[QUESTION_TYPE], fields[RESPONSE_TYPE],
                        fields[RESPONSE_DATE], fields[WAITING_TIME]));
            } else if (fields[LINE_TYPE].equals(QUERY_LINE)) {
                analyze(new QueryParse(i, fields[SERVICE_ID], fields[QUESTION_TYPE], fields[RESPONSE_TYPE],
                        fields[RESPONSE_DATE]));
            }
        }
        return output.toString();
    }

    private static void analyze(QueryParse queryParse) {
        int count = 0;
        int waitingTime = 0;
        for (DateLine dateLine : DATE_LINES) {
            if (dateLine.isMatch(queryParse)) {
                waitingTime += dateLine.getWaitingTime();
                count++;
            }
        }
        output.append(count > 0 ? String.valueOf(waitingTime / count) : HYPHEN)
                .append(System.lineSeparator());
    }

    public Date parseDate(String date, int id) {
        Date result = MAX_DATE;
        try {
            result = new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            System.out.printf("Invalid date format in line %s", id);
        }
        return result;
    }

    public String toPattern(String s) {
        return s.equals(ASTERISK) ? MATCH_ANY : s.concat(MATCH_ANY);
    }

}