package com.progforce;

import com.progforce.dao.ContentDao;
import com.progforce.dao.ContentDaoImpl;
import com.progforce.model.Content;
import com.progforce.service.ContentService;
import com.progforce.service.ContentServiceImpl;
import java.util.List;
import java.util.Map;

public class ProgforceApp {
    public static void main(String[] args) {
        ContentDao dao = new ContentDaoImpl();
        ContentService service = new ContentServiceImpl(dao);
        Content contentFromDB = service.getContentFromDB();
        Map<Action, List<Content>> contentLists = service.getContentLists(contentFromDB);
        printContentLists(contentLists);
    }

    private static void printContentLists(Map<Action, List<Content>> contentLists) {
        System.out.print("Список файлов\\директорий на кодирование:");
        List<Content> toEncode = contentLists.get(Action.ENCODE);
        if (toEncode.isEmpty()) {
            System.out.println("-----");
        } else {
            System.out.println();
            toEncode.forEach(content -> System.out.println(content.getFileName()));
        }
        System.out.print("Список файлов\\директорий на копирование:");
        List<Content> toCopy = contentLists.get(Action.COPY);
        if (toCopy.isEmpty()) {
            System.out.println("-----");
        } else {
            System.out.println();
            toCopy.forEach(content -> System.out.println(content.getFileName()));
        }
    }
}
