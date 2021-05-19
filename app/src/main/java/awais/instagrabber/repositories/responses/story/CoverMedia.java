package awais.instagrabber.repositories.responses.story;

import awais.instagrabber.repositories.responses.MediaCandidate;

public class CoverMedia {
    private final MediaCandidate croppedImageVersion;

    public CoverMedia(final MediaCandidate croppedImageVersion) {
        this.croppedImageVersion = croppedImageVersion;
    }

    public MediaCandidate getCroppedImageVersion() {
        return croppedImageVersion;
    }
}
