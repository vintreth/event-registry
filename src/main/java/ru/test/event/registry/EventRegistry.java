package ru.test.event.registry;

import ru.test.event.Event;

/**
 * Реестр для учета событий
 *
 * @author svip
 *         2017-08-06
 */
public interface EventRegistry {
    /**
     * Учитывает событие в реестре
     *
     * @param event объект события
     */
    void register(Event event);

    /**
     * Возвращает число событий за последнюю минуту
     *
     * @return количество событий
     */
    long getLastMinuteEventsCount();

    /**
     * Возвращает число событий за последний час
     *
     * @return количество событий
     */
    long getLastHourEventsCount();

    /**
     * Возвращает число событий за последние сутки
     *
     * @return количество событий
     */
    long getLastDayEventsCount();
}
