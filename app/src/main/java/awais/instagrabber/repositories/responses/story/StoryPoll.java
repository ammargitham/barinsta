package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

import java.util.List;

public class StoryPoll extends StorySticker {
    private final PollSticker pollSticker;

    public StoryPoll(final float x,
                     final float y,
                     final int z,
                     final float width,
                     final float height,
                     final float rotation,
                     final int isPinned,
                     final int isHidden,
                     final int isSticker,
                     final int isFbSticker,
                     final String displayType,
                     final PollSticker pollSticker) {
        super(x, y, z, width, height, rotation, isPinned, isHidden, isSticker, isFbSticker, displayType);
        this.pollSticker = pollSticker;
    }

    public PollSticker getPollSticker() {
        return pollSticker;
    }

    @NonNull
    @Override
    public Type getType() {
        return Type.POLL;
    }

    public static class PollSticker {
        private final String id;
        private final long pollId;
        private final String question;
        private final List<Tally> tallies;
        private final boolean viewerCanVote;
        private final boolean isSharedResult;
        private final boolean finished;

        public PollSticker(final String id,
                           final long pollId,
                           final String question,
                           final List<Tally> tallies,
                           final boolean viewerCanVote,
                           final boolean isSharedResult,
                           final boolean finished) {
            this.id = id;
            this.pollId = pollId;
            this.question = question;
            this.tallies = tallies;
            this.viewerCanVote = viewerCanVote;
            this.isSharedResult = isSharedResult;
            this.finished = finished;
        }

        public String getId() {
            return id;
        }

        public long getPollId() {
            return pollId;
        }

        public String getQuestion() {
            return question;
        }

        public List<Tally> getTallies() {
            return tallies;
        }

        public boolean isViewerCanVote() {
            return viewerCanVote;
        }

        public boolean isSharedResult() {
            return isSharedResult;
        }

        public boolean isFinished() {
            return finished;
        }
    }
}
