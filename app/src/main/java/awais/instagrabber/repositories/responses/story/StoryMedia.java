package awais.instagrabber.repositories.responses.story;

import java.util.List;
import java.util.Map;

import awais.instagrabber.models.enums.MediaItemType;
import awais.instagrabber.repositories.responses.Audio;
import awais.instagrabber.repositories.responses.Caption;
import awais.instagrabber.repositories.responses.ImageVersions2;
import awais.instagrabber.repositories.responses.Location;
import awais.instagrabber.repositories.responses.Media;
import awais.instagrabber.repositories.responses.User;
import awais.instagrabber.repositories.responses.Usertags;
import awais.instagrabber.repositories.responses.VideoVersion;
import awais.instagrabber.repositories.responses.feed.EndOfFeedDemarcator;

public class StoryMedia extends Media {
    private final boolean canReply;
    private final boolean canReshare;
    private final String linkText;
    private final List<StoryPoll> storyPolls;
    private final List<StoryQuiz> storyQuizs;
    private final List<StoryCallToAction> storyCta;
    private final List<StorySlider> storySliders;
    private final List<StoryQuestion> storyQuestions;
    private final List<ReelMention> reelMentions;

    public StoryMedia(final String pk,
                      final String id,
                      final String code,
                      final long takenAt,
                      final User user,
                      final boolean canViewerReshare,
                      final ImageVersions2 imageVersions2,
                      final int originalWidth,
                      final int originalHeight,
                      final MediaItemType mediaType,
                      final boolean commentLikesEnabled,
                      final boolean commentsDisabled,
                      final long nextMaxId,
                      final long commentCount,
                      final long likeCount,
                      final boolean hasLiked,
                      final boolean isReelMedia,
                      final List<VideoVersion> videoVersions,
                      final boolean hasAudio,
                      final double videoDuration,
                      final long viewCount,
                      final Caption caption,
                      final boolean canViewerSave,
                      final Audio audio,
                      final String title,
                      final List<Media> carouselMedia,
                      final Location location,
                      final Usertags usertags,
                      final boolean isSidecarChild,
                      final boolean hasViewerSaved,
                      final Map<String, Object> injected,
                      final EndOfFeedDemarcator endOfFeedDemarcator,
                      final boolean canReply,
                      final boolean canReshare,
                      final List<StoryPoll> storyPolls,
                      final List<StoryQuiz> storyQuizs,
                      final String linkText,
                      final List<StoryCallToAction> storyCta,
                      final List<StorySlider> storySliders,
                      final List<StoryQuestion> storyQuestions,
                      final List<ReelMention> reelMentions) {
        super(pk, id, code, takenAt, user, canViewerReshare, imageVersions2, originalWidth, originalHeight, mediaType, commentLikesEnabled,
              commentsDisabled, nextMaxId, commentCount, likeCount, hasLiked, isReelMedia, videoVersions, hasAudio, videoDuration, viewCount, caption,
              canViewerSave, audio, title, carouselMedia, location, usertags, isSidecarChild, hasViewerSaved, injected, endOfFeedDemarcator);
        this.canReply = canReply;
        this.canReshare = canReshare;
        this.storyPolls = storyPolls;
        this.storyQuizs = storyQuizs;
        this.linkText = linkText;
        this.storyCta = storyCta;
        this.storySliders = storySliders;
        this.storyQuestions = storyQuestions;
        this.reelMentions = reelMentions;
    }

    public boolean canReply() {
        return canReply;
    }

    public boolean canReshare() {
        return canReshare;
    }

    public List<StoryPoll> getStoryPolls() {
        return storyPolls;
    }

    public List<StoryQuiz> getStoryQuizs() {
        return storyQuizs;
    }

    public String getLinkText() {
        return linkText;
    }

    public List<StoryCallToAction> getStoryCta() {
        return storyCta;
    }

    public List<StorySlider> getStorySliders() {
        return storySliders;
    }

    public List<StoryQuestion> getStoryQuestions() {
        return storyQuestions;
    }

    public List<ReelMention> getReelMentions() {
        return reelMentions;
    }
}
