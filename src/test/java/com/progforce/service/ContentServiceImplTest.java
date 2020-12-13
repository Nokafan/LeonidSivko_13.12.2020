package com.progforce.service;

import com.progforce.Action;
import com.progforce.dao.ContentDao;
import com.progforce.dao.ContentDaoImpl;
import com.progforce.model.Content;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContentServiceImplTest {

    private static ContentService service;

    @BeforeClass
    public static void init() {
        ContentDao dao = new ContentDaoImpl();
        service = new ContentServiceImpl(dao);
    }

    @Test
    public void getContentLists_first() {
        Content content = new Content(1L, "d1", null, true);
        content.getFiles().add(new Content(2L, "d1/f1.mov", 1L, false));
        Content d1d2 = new Content(3L, "d1/d2", 1L, true);
        content.getDirectories().add(d1d2);
        d1d2.getFiles().add(new Content(2L, "d1/d2/f2.tif", 3L, false));
        d1d2.getFiles().add(new Content(2L, "d1/d2/f-n.tif", 3L, false));

        Map<Action, List<Content>> contentLists = service.getContentLists(content);
        assertEquals(2, contentLists.get(Action.ENCODE).size());
        assertEquals("d1/f1.mov", contentLists.get(Action.ENCODE).get(0).getFileName());
        assertEquals("d1/d2", contentLists.get(Action.ENCODE).get(1).getFileName());
        assertEquals(0, contentLists.get(Action.COPY).size());
    }

    @Test
    public void getContentLists_second() {
        Content content = new Content(1L, "d1", null, true);
        content.getFiles().add(new Content(2L, "d1/f1.mov", 1L, false));
        content.getFiles().add(new Content(3L, "d1/f3.txt", 1L, false));
        Content d1d2 = new Content(4L, "d1/d2", 1L, true);
        content.getDirectories().add(d1d2);
        d1d2.getFiles().add(new Content(5L, "d1/d2/f2.tif", 4L, false));
        d1d2.getFiles().add(new Content(6L, "d1/d2/f-n.tif", 4L, false));
        Content d1d2d3 = new Content(7L, "d1/d2/d3", 4L, true);
        d1d2.getDirectories().add(d1d2d3);
        d1d2d3.getFiles().add(new Content(8L, "d1/d2/d3/f4.pdf", 7L, false));

        Map<Action, List<Content>> contentLists = service.getContentLists(content);
        assertEquals(2, contentLists.get(Action.ENCODE).size());
        assertEquals("d1/f1.mov", contentLists.get(Action.ENCODE).get(0).getFileName());
        assertEquals("d1/d2", contentLists.get(Action.ENCODE).get(1).getFileName());
        assertEquals(1, contentLists.get(Action.COPY).size());
        assertEquals("d1/f3.txt", contentLists.get(Action.COPY).get(0).getFileName());
    }

    @Test
    public void getContentLists_third() {
        Content content = new Content(1L, "d1", null, true);
        content.getFiles().add(new Content(2L, "d1/f3.txt", 1L, false));
        Content d1d2 = new Content(3L, "d1/d2", 1L, true);
        content.getDirectories().add(d1d2);
        Content d1d2d3 = new Content(4L, "d1/d2/d3", 3L, true);
        d1d2.getDirectories().add(d1d2d3);
        d1d2d3.getFiles().add(new Content(5L, "d1/d2/d3/f4.pdf", 4L, false));

        Map<Action, List<Content>> contentLists = service.getContentLists(content);
        assertEquals(0, contentLists.get(Action.ENCODE).size());
        assertEquals(1, contentLists.get(Action.COPY).size());
        assertEquals("d1", contentLists.get(Action.COPY).get(0).getFileName());
    }

    @Test
    public void getContentLists_fourth() {
        Content content = new Content(1L, "d0", null, true);
        Content d0d1 = new Content(2L, "d0/d1", 1L, true);
        content.getDirectories().add(d0d1);
        d0d1.getFiles().add(new Content(3L, "d0/d1/f1.mov", 2L, false));
        d0d1.getFiles().add(new Content(4L, "d0/d1/f3.txt", 2L, false));
        Content d0d1d2 = new Content(5L, "d0/d1/d2", 2L, true);
        d0d1.getDirectories().add(d0d1d2);
        d0d1d2.getFiles().add(new Content(6L, "d0/d1/d2/f2.tif", 5L, false));
        d0d1d2.getFiles().add(new Content(7L, "d0/d1/d2/fn.tif", 5L, false));
        Content d0d1d2d3 = new Content(8L, "d0/d1/d2/d3", 5L, true);
        d0d1d2.getDirectories().add(d0d1d2d3);
        d0d1d2d3.getFiles().add(new Content(9L, "d0/d1/d2/d3/f4.pdf", 8L, false));
        Content d0d4 = new Content(10L, "d0/d4", 1L, true);
        content.getDirectories().add(d0d4);
        d0d4.getFiles().add(new Content(11L, "d0/d4/f5.xml", 10L, false));
        Content d0d4d5 = new Content(12L, "d0/d4/d5", 10L, true);
        d0d4.getDirectories().add(d0d4d5);
        Content d0d4d5d6 = new Content(13L, "d0/d4/d5/d6", 12L, true);
        d0d4d5.getDirectories().add(d0d4d5d6);
        d0d4d5d6.getFiles().add(new Content(14L, "d0/d4/d5/d6/f6.xml", 13L, false));
        Content d0d7 = new Content(15L, "d0/d7", 1L, true);
        content.getDirectories().add(d0d7);
        Content d0d7d8 = new Content(16L, "d0/d7/d8", 15L, true);
        d0d7.getDirectories().add(d0d7d8);
        d0d7d8.getFiles().add(new Content(17L, "d0/d7/d8/f7.xml", 16L, false));

        Map<Action, List<Content>> contentLists = service.getContentLists(content);
        assertEquals(2, contentLists.get(Action.ENCODE).size());
        assertEquals("d0/d1/f1.mov", contentLists.get(Action.ENCODE).get(0).getFileName());
        assertEquals("d0/d1/d2", contentLists.get(Action.ENCODE).get(1).getFileName());
        assertEquals(3, contentLists.get(Action.COPY).size());
        assertEquals("d0/d1/f3.txt", contentLists.get(Action.COPY).get(0).getFileName());
        assertEquals("d0/d4", contentLists.get(Action.COPY).get(1).getFileName());
        assertEquals("d0/d7", contentLists.get(Action.COPY).get(2).getFileName());
    }
}