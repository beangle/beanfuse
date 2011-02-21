package org.beanfuse.rule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beanfuse.rule.Context;
import org.beanfuse.text.Message;

public class SimpleContext implements Context {

    private List errors = new ArrayList();

    private List messages = new ArrayList();

    private Map params = new HashMap();

    public void addError(Message message) {
        errors.add(message);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List getErrors() {
        return errors;
    }

    public List getMessages() {
        return messages;
    }

    public Map getParams() {
        return params;
    }
}
