package awais.instagrabber.repositories.responses.story;

import java.util.Map;

public class StoryResponse {
    private final Reel reel;
    private final Map<String, Reel> reels;
    private final String status;

    public StoryResponse(final Reel reel, final Map<String, Reel> reels, final String status) {
        this.reel = reel;
        this.reels = reels;
        this.status = status;
    }

    public Reel getReel() {
        return reel;
    }

    public Map<String, Reel> getReels() {
        return reels;
    }

    public String getStatus() {
        return status;
    }
}
