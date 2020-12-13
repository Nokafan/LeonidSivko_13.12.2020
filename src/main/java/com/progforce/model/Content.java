package com.progforce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Content {
    private Long id;
    private String fileName;
    private Long parentId;
    private Boolean isDirectory;
    private List<Content> directories;
    private List<Content> files;

    public Content() {
    }

    public Content(Long id, String fileName, Long parentId, Boolean isDirectory) {
        this.id = id;
        this.fileName = fileName;
        this.parentId = parentId;
        this.isDirectory = isDirectory;
        if (isDirectory) {
            directories = new ArrayList<>();
            files = new ArrayList<>();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        return fileName.equals(content.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public List<Content> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Content> directories) {
        this.directories = directories;
    }

    public List<Content> getFiles() {
        return files;
    }

    public void setFiles(List<Content> files) {
        this.files = files;
    }
}
