package com.outreach.github;

import com.google.gson.JsonArray;

public class GetTickets {

    public static Tickets getTickets(Integer page_number) {

        if (page_number == null) {
            throw new IllegalArgumentException("Cannot send a null number");
        }

        if (page_number < 0) {
            throw new IllegalArgumentException("Cannot have a negative number");
        }
    }

    public class Tickets {
        Integer page_number;
        JsonArray ticket;

        public Integer getPage_number() {
            return page_number;
        }

        public void setPage_number(Integer page_number) {
            this.page_number = page_number;
        }

        public JsonArray getTicket() {
            return ticket;
        }

        public void setTicket(JsonArray ticket) {
            this.ticket = ticket;
        } 
    }
}