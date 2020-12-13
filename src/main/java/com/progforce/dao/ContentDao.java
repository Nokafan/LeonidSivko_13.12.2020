package com.progforce.dao;

import com.progforce.model.Content;
import java.util.Optional;

public interface ContentDao {
    Optional<Content> getContent();
}
