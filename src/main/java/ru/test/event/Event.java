package ru.test.event;

import java.util.Date;

/**
 * Событие в системе
 *
 * @author svip
 *         2017-08-06
 */
public interface Event {
    /**
     * Идентификатор события
     *
     * @return идентификатор события
     */
    long getId();

    /**
     * Название события
     *
     * @return название события
     */
    String getName();

    /**
     * Время события
     *
     * @return время события
     */
    Date getDate();
}
