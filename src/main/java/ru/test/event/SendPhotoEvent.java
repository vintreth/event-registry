package ru.test.event;

import java.util.Date;

/**
 * Событие отправки фотографии
 *
 * @author svip
 *         2017-08-06
 */
public class SendPhotoEvent implements Event {
    private static final String NAME = "SendPhotoEvent";

    private final long id;
    private final Date date;

    public SendPhotoEvent(long id) {
        this.id = id;
        this.date = new Date();
    }

    public SendPhotoEvent(long id, Date date) {
        this.id = id;
        this.date = date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Date getDate() {
        return date;
    }
}
