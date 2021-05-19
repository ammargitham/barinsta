package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

public class StoryQuestion extends StorySticker {
    private final QuestionSticker questionSticker;

    public StoryQuestion(final float x,
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
                         final QuestionSticker questionSticker) {
        super(x, y, z, width, height, rotation, isPinned, isHidden, isSticker, isFbSticker, displayType);
        this.questionSticker = questionSticker;
    }

    public QuestionSticker getQuestionSticker() {
        return questionSticker;
    }

    @NonNull
    @Override
    public Type getType() {
        return Type.QUESTION;
    }

    public static class QuestionSticker {
        private final String questionType;
        private final long questionId;
        private final String question;
        private final long mediaId;
        private final String textColor;
        private final String backgroundColor;
        private final boolean viewerCanInteract;
        private final String profilePicUrl;

        public QuestionSticker(final String questionType,
                               final long questionId,
                               final String question,
                               final long mediaId,
                               final String textColor,
                               final String backgroundColor,
                               final boolean viewerCanInteract, final String profilePicUrl) {
            this.questionType = questionType;
            this.questionId = questionId;
            this.question = question;
            this.mediaId = mediaId;
            this.textColor = textColor;
            this.backgroundColor = backgroundColor;
            this.viewerCanInteract = viewerCanInteract;
            this.profilePicUrl = profilePicUrl;
        }

        public String getQuestionType() {
            return questionType;
        }

        public long getQuestionId() {
            return questionId;
        }

        public String getQuestion() {
            return question;
        }

        public long getMediaId() {
            return mediaId;
        }

        public String getTextColor() {
            return textColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public boolean isViewerCanInteract() {
            return viewerCanInteract;
        }

        public String getProfilePicUrl() {
            return profilePicUrl;
        }
    }
}
