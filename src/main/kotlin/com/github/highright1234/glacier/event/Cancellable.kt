package com.github.highright1234.glacier.event;

public interface Cancellable {
    void setCancelled(boolean value);
    boolean isCancelled();
}
