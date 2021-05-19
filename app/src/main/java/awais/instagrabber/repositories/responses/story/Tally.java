package awais.instagrabber.repositories.responses.story;

public class Tally {
    private final String text;
    private final float fontSize;
    private final long count;

    public Tally(final String text, final float fontSize, final long count) {
        this.text = text;
        this.fontSize = fontSize;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public float getFontSize() {
        return fontSize;
    }

    public long getCount() {
        return count;
    }
}
