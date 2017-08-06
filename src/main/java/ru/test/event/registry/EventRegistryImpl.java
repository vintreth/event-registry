package ru.test.event.registry;

import ru.test.event.Event;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Реализация реестра для учета событий
 * Реализация непотокобезопасна, т.к. подразумевается, что запись событий будет происходить синхронно
 *
 * @author svip
 *         2017-08-06
 */
public class EventRegistryImpl implements EventRegistry {
    private static final String MINUTE_ID_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String SECOND_ID_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final long ONE_MINUTE_TIMESTAMP_OFFSET = 60 * 1000;

    /**
     * Мапа с id конкретной минуты и блоком с данными о событиях, зарегистрированных в эту минуту
     */
    private final Map<String, Block> idToBlockMap = new HashMap<>();

    private enum Period {
        MINUTE(60 * 1000),
        HOUR(60 * 60 * 1000),
        DAY(24 * 60 * 60 * 1000);

        final long value;

        Period(long value) {
            this.value = value;
        }
    }

    @Override
    public void register(Event event) {
        System.out.println("Registering new event " + event.getName() + " #" + event.getId());
        String minuteId = getMinuteId(event.getDate().getTime());
        Block block = idToBlockMap.get(minuteId);
        if (block == null) {
            System.out.println("Creating block for minute " + minuteId);
            block = new Block();
            idToBlockMap.put(minuteId, block);
        }
        block.addEvent(event);
    }

    private static String getMinuteId(long eventTimestamp) {
        return new SimpleDateFormat(MINUTE_ID_PATTERN).format(eventTimestamp);
    }

    private static String getSecondId(long eventTimestamp) {
        return new SimpleDateFormat(SECOND_ID_PATTERN).format(eventTimestamp);
    }

    @Override
    public long getLastMinuteEventsCount() {
        return getEventsCount(Period.MINUTE);
    }

    @Override
    public long getLastHourEventsCount() {
        return getEventsCount(Period.HOUR);
    }

    @Override
    public long getLastDayEventsCount() {
        return getEventsCount(Period.DAY);
    }

    private long getEventsCount(Period period) {
        long toTimestamp = new Date().getTime();
        long fromTimestamp = toTimestamp - period.value;
        System.out.println(
            "Getting events count from " + getSecondId(fromTimestamp) + " to " + getSecondId(toTimestamp));
        long eventsCount = 0;
        long timestamp = fromTimestamp;
        do {
            String minuteId = getMinuteId(timestamp);
            Block block = idToBlockMap.get(minuteId);
            if (block != null) {
                if (timestamp == fromTimestamp) {
                    eventsCount += block.obtainCountAfterTimestamp(timestamp);
                } else if (timestamp == toTimestamp) {
                    eventsCount += block.obtainCountBeforeTimestamp(timestamp);
                } else if (fromTimestamp < timestamp && timestamp < toTimestamp) {
                    eventsCount += block.size();
                }
            }
            timestamp += ONE_MINUTE_TIMESTAMP_OFFSET;
        } while (timestamp <= toTimestamp);
        return eventsCount;
    }

    /**
     * Блок с данными о событиях за минуту
     */
    private static class Block {
        private final List<Event> events = new LinkedList<>();

        /**
         * Добавление события в блок
         * @param event событие
         */
        void addEvent(Event event) {
            events.add(event);
        }

        /**
         * Размер блока
         * @return размер блока
         */
        int size() {
            return events.size();
        }

        /**
         * Получение количества событий в блоке начиная с элемента, который больше переданной метки времени
         *
         * @param timestamp метка времени, с которой начинается подсчет
         * @return число событий
         */
        private long obtainCountAfterTimestamp(long timestamp) {
            long count = 0;
            for (Event event : events) {
                if (event.getDate().getTime() > timestamp) {
                    count++;
                }
            }
            return count;
        }

        /**
         * Получение количества событий в блоке до элемента, который больше переданной метки времени
         *
         * @param timestamp метка времени, до которой происходит подсчет
         * @return число событий
         */
        private long obtainCountBeforeTimestamp(long timestamp) {
            long count = 0;
            ListIterator<Event> listIterator = events.listIterator(events.size());
            while (listIterator.hasPrevious()) {
                if (listIterator.previous().getDate().getTime() <= timestamp) {
                    count++;
                }
            }
            return count;
        }
    }
}
