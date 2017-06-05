package org.ucomplex.ucomplex.Modules.Messenger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ---------------------------------------------------
 * Created by Sermilion on 04/06/2017.
 * Project: UComplex
 * ---------------------------------------------------
 * <a href="http://www.ucomplex.org">www.ucomplex.org</a>
 * <a href="http://www.github.com/sermilion>github</a>
 * ---------------------------------------------------
 */

public final class MessengerItem {

    private final int id;
    private final int from;
    private final String message;
    private final String time;
    private final int status;
    private List<MessageFile> files;

    public MessengerItem() {
        this.id = 0;
        this.from = 0;
        this.message = "";
        this.time = "";
        this.status = 0;
        this.files = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }

    public List<MessageFile> getFiles() {
        return files;
    }

    public void setFiles(List<MessageFile> files) {
        this.files = files;
    }
}