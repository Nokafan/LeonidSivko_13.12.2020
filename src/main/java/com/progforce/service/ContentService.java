package com.progforce.service;

import com.progforce.Action;
import com.progforce.model.Content;
import java.util.List;
import java.util.Map;

public interface ContentService {
    Map<Action, List<Content>> getContentLists(Content content);

    Content getContentFromDB();
}
