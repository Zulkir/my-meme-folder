package com.mymemefolder.mmfgateway.controllers;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String name;
    private List<Folder> children;

    public Folder() {}

    public Folder(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public Folder(String name, List<Folder> children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Folder> getChildren() {
        return children;
    }

    public String toJson() {
        var builder = new StringBuilder();
        toJson(builder);
        return builder.toString();
    }

    private void toJson(StringBuilder builder) {
        builder.append('{');
        builder.append("\"name\":\"");
        builder.append(name);
        builder.append("\",children:");
        childrenToJson(builder);
        builder.append('}');
    }

    public String childrenToJson() {
        var builder = new StringBuilder();
        childrenToJson(builder);
        return builder.toString();
    }

    private void childrenToJson(StringBuilder builder) {
        builder.append('[');
        var printedChildBefore = false;
        for (var child : children) {
            if (printedChildBefore)
                builder.append(',');
            else
                printedChildBefore = true;
            child.toJson(builder);
        }
        builder.append(']');
    }
}
