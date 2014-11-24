package com.gezbox.windmap.app.model;

/**
 * Created by zombie on 7/18/14.
 */
public class Version {
    private int version;

    private String release_note;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRelease_note() {
        return release_note;
    }

    public void setRelease_note(String release_note) {
        this.release_note = release_note;
    }
}
