package ru.test.event.registry;

import org.junit.Test;
import ru.test.event.SendPhotoEvent;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author svip
 *         2017-08-06
 */
public class EventRegistryImplTest {
    @Test
    public void testGetLastMinuteEventsCount() throws Exception {
        EventRegistry eventRegistry = new EventRegistryImpl();
        long currentTimeStamp = new Date().getTime();
        eventRegistry.register(new SendPhotoEvent(1, new Date(currentTimeStamp - 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(2, new Date(currentTimeStamp - 57 * 1000)));
        eventRegistry.register(new SendPhotoEvent(3, new Date(currentTimeStamp - 30 * 1000)));
        eventRegistry.register(new SendPhotoEvent(4, new Date(currentTimeStamp - 10 * 1000)));
        eventRegistry.register(new SendPhotoEvent(5, new Date(currentTimeStamp - 2 * 1000)));
        eventRegistry.register(new SendPhotoEvent(6, new Date(currentTimeStamp)));
        eventRegistry.register(new SendPhotoEvent(7, new Date(currentTimeStamp + 2 * 1000)));
        eventRegistry.register(new SendPhotoEvent(8, new Date(currentTimeStamp + 60 * 1000)));
        assertEquals(5, eventRegistry.getLastMinuteEventsCount());
    }

    @Test
    public void testGetLastHourEventsCount() throws Exception {
        EventRegistry eventRegistry = new EventRegistryImpl();
        long currentTimeStamp = new Date().getTime();
        eventRegistry.register(new SendPhotoEvent(1, new Date(currentTimeStamp - 60 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(2, new Date(currentTimeStamp - 55 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(3, new Date(currentTimeStamp - 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(4, new Date(currentTimeStamp)));
        eventRegistry.register(new SendPhotoEvent(5, new Date(currentTimeStamp + 60 * 1000)));
        assertEquals(3, eventRegistry.getLastHourEventsCount());
    }

    @Test
    public void testGetLastDayEventsCount() throws Exception {
        EventRegistry eventRegistry = new EventRegistryImpl();
        long currentTimeStamp = new Date().getTime();
        eventRegistry.register(new SendPhotoEvent(1, new Date(currentTimeStamp - 24 * 60 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(2, new Date(currentTimeStamp - 23 * 60 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(3, new Date(currentTimeStamp - 23 * 60 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(4, new Date(currentTimeStamp - 60 * 60 * 1000)));
        eventRegistry.register(new SendPhotoEvent(5, new Date(currentTimeStamp)));
        eventRegistry.register(new SendPhotoEvent(6, new Date(currentTimeStamp + 60 * 60 * 1000)));
        assertEquals(4, eventRegistry.getLastDayEventsCount());
    }
}