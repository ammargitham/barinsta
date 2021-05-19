package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

import java.util.List;

public class StoryQuiz extends StorySticker {
    private final QuizSticker quizSticker;

    public StoryQuiz(final float x,
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
                     final QuizSticker quizSticker) {
        super(x, y, z, width, height, rotation, isPinned, isHidden, isSticker, isFbSticker, displayType);
        this.quizSticker = quizSticker;
    }

    public QuizSticker getQuizSticker() {
        return quizSticker;
    }

    @NonNull
    @Override
    public Type getType() {
        return Type.QUIZ;
    }

    public static class QuizSticker {
        private final String id;
        private final long quizId;
        private final String question;
        private final List<Tally> tallies;
        private final int correctAnswer;
        private final boolean viewerCanAnswer;
        private final boolean finished;
        private final String textColor;
        private final String startBackgroundColor;
        private final String endBackgroundColor;

        public QuizSticker(final String id,
                           final long quizId,
                           final String question,
                           final List<Tally> tallies,
                           final int correctAnswer,
                           final boolean viewerCanAnswer,
                           final boolean finished,
                           final String textColor,
                           final String startBackgroundColor,
                           final String endBackgroundColor) {
            this.id = id;
            this.quizId = quizId;
            this.question = question;
            this.tallies = tallies;
            this.correctAnswer = correctAnswer;
            this.viewerCanAnswer = viewerCanAnswer;
            this.finished = finished;
            this.textColor = textColor;
            this.startBackgroundColor = startBackgroundColor;
            this.endBackgroundColor = endBackgroundColor;
        }

        public String getId() {
            return id;
        }

        public long getQuizId() {
            return quizId;
        }

        public String getQuestion() {
            return question;
        }

        public List<Tally> getTallies() {
            return tallies;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }

        public boolean isViewerCanAnswer() {
            return viewerCanAnswer;
        }

        public boolean isFinished() {
            return finished;
        }

        public String getTextColor() {
            return textColor;
        }

        public String getStartBackgroundColor() {
            return startBackgroundColor;
        }

        public String getEndBackgroundColor() {
            return endBackgroundColor;
        }
    }
}
