package com.progforce.service;

import com.progforce.Action;
import com.progforce.dao.ContentDao;
import com.progforce.model.Content;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ContentServiceImpl implements ContentService {
    public static final String TIF_EXTENSION = ".tif";
    public static final String MOV_EXTENSION = ".mov";
    public static final String AVI_EXTENSION = ".avi";
    private final ContentDao dao;

    public ContentServiceImpl(ContentDao dao) {
        this.dao = dao;
    }

    @Override
    public Content getContentFromDB() {
        return dao.getContent()
                .orElseThrow(() -> new RuntimeException("DB contains no Content"));
    }

    @Override
    public Map<Action, List<Content>> getContentLists(Content content) {
        List<Content> toEncode = new ArrayList<>();
        List<Content> toCopy = new ArrayList<>();
        fillContentLists(content, toEncode, toCopy);
        toEncode.sort(Comparator.comparing(Content::getId));
        toCopy.sort(Comparator.comparing(Content::getId));
        return Map.of(Action.ENCODE, toEncode, Action.COPY, toCopy);
    }

    private void fillContentLists(Content content, List<Content> toEncode, List<Content> toCopy) {
        if (isContainTif(content)) {
            toEncode.add(content);
            return;
        }
        for (Content directory : content.getDirectories()) {
            fillContentLists(directory, toEncode, toCopy);
        }
        if (toCopy.containsAll(content.getDirectories()) && isAllFilesToCopy(content)) {
            toCopy.removeAll(content.getDirectories());
            toCopy.add(content);
            return;
        }
        for (Content file : content.getFiles()) {
            if (isFileToEncode(file)) {
                toEncode.add(file);
            } else {
                toCopy.add(file);
            }
        }
    }

    private boolean isContainTif(Content content) {
        return !content.getFiles().isEmpty()
               && content.getFiles().stream()
                       .allMatch(file -> file.getFileName().endsWith(TIF_EXTENSION));
    }

    private boolean isAllFilesToCopy(Content content) {
        return content.getFiles().stream()
                .noneMatch(this::isFileToEncode);
    }

    private boolean isFileToEncode(Content content) {
        return content.getFileName().endsWith(MOV_EXTENSION)
               || content.getFileName().endsWith(AVI_EXTENSION);
    }
}
