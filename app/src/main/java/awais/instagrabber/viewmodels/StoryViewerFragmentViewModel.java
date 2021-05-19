package awais.instagrabber.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import awais.instagrabber.models.FeedStoryModel;
import awais.instagrabber.models.HighlightModel;
import awais.instagrabber.repositories.requests.StoryViewerOptions;
import awais.instagrabber.repositories.responses.story.Reel;
import awais.instagrabber.repositories.responses.story.StoryArchiveResponse.ArchiveResponseItem;
import awais.instagrabber.repositories.responses.story.StoryMedia;
import awais.instagrabber.repositories.responses.story.StoryResponse;
import awais.instagrabber.utils.Constants;
import awais.instagrabber.utils.CookieUtils;
import awais.instagrabber.webservices.ServiceCallback;
import awais.instagrabber.webservices.StoriesService;

import static awais.instagrabber.utils.Utils.settingsHelper;

public class StoryViewerFragmentViewModel extends ViewModel {
    private static final String TAG = StoryViewerFragmentViewModel.class.getSimpleName();
    /**
     * Tracks the current story index in {@link StoryViewerFragmentViewModel#stories}
     */
    private final MutableLiveData<Integer> currentStoryIndex = new MutableLiveData<>(0);

    private final MutableLiveData<Reel> currentReel = new MutableLiveData<>();
    private final MutableLiveData<StoryMedia> activeStoryItem = new MutableLiveData<>();
    private final MutableLiveData<Integer> activeStoryItemIndex = new MutableLiveData<>(0);

    private final StoriesService storiesService;

    private StoryViewerOptions options;
    @Nullable
    private List<?> stories;

    public StoryViewerFragmentViewModel() {
        final String cookie = settingsHelper.getString(Constants.COOKIE);
        final String csrfToken = CookieUtils.getCsrfTokenFromCookie(cookie);
        if (csrfToken == null) {
            throw new IllegalStateException("No csrf token in cookie");
        }
        final long userIdFromCookie = CookieUtils.getUserIdFromCookie(cookie);
        final String deviceId = settingsHelper.getString(Constants.DEVICE_UUID);
        storiesService = StoriesService.getInstance(csrfToken, userIdFromCookie, deviceId);
    }

    public LiveData<Reel> getCurrentReel() {
        return currentReel;
    }

    public LiveData<StoryMedia> getActiveStoryMedia() {
        return activeStoryItem;
    }

    public LiveData<Integer> getActiveStoryItemIndex() {
        return activeStoryItemIndex;
    }

    public void setActiveStoryItemIndex(final int activeStoryItemIndex) {
        final int index = Math.max(activeStoryItemIndex, 0);
        this.activeStoryItemIndex.postValue(index);
        try {
            final Reel currentReel = this.currentReel.getValue();
            if (currentReel == null || currentReel.getItems() == null || currentReel.getItems().isEmpty()) {
                activeStoryItem.postValue(null);
                return;
            }
            activeStoryItem.postValue(currentReel.getItems().get(index));
        } catch (Exception e) {
            Log.e(TAG, "onSuccess: ", e);
            activeStoryItem.postValue(null);
        }

    }

    public void setOptions(@NonNull final StoryViewerOptions options) {
        this.options = options;
    }

    public void setStories(@Nullable final List<?> stories) {
        this.stories = stories;
    }
    // to be called at first init only

    public void init() {
        final int storyIndex = getCurrentStoryIndexFromOptions(options);
        currentStoryIndex.postValue(storyIndex);
        setCurrentReel(storyIndex);
    }

    private int getCurrentStoryIndexFromOptions(@NonNull final StoryViewerOptions options) {
        int storyIndex = 0;
        switch (options.getType()) {
            case HASHTAG:
            case LOCATION:
            case USER:
            case STORY:
            case STORY_ARCHIVE:
                break;
            case HIGHLIGHT:
            case FEED_STORY:
                storyIndex = options.getStoryIndex();
                break;
        }
        return storyIndex;
    }

    private void setCurrentReel(final int storyIndex) {
        switch (options.getType()) {
            case HASHTAG:
            case LOCATION:
                setStoryItemsFromOptions();
                break;
            case STORY:
                setDirectStoryItems();
                break;
            case USER:
                setUserStoryItems();
                break;
            case HIGHLIGHT: {
                if (stories == null) return;
                final Object story = stories.get(storyIndex);
                if (!(story instanceof HighlightModel)) return;
                setHighlightStoryItems((HighlightModel) story);
                break;
            }
            case FEED_STORY: {
                if (stories == null) return;
                final Object story = stories.get(storyIndex);
                if (!(story instanceof FeedStoryModel)) return;
                setFeedStoryItems((FeedStoryModel) story);
                break;
            }
            case STORY_ARCHIVE: {
                if (stories == null) return;
                final Object story = stories.get(storyIndex);
                if (!(story instanceof ArchiveResponseItem)) return;
                setArchiveStoryItems((ArchiveResponseItem) story);
                break;
            }
        }
    }

    private void setFeedStoryItems(@NonNull final FeedStoryModel story) {
        final String storyMediaId = story.getStoryMediaId();
        final String username = story.getProfileModel().getUsername();
        final StoryViewerOptions fetchOptions = StoryViewerOptions.forUser(Long.parseLong(storyMediaId), username);
        // if (story.isLive()) {
        //     live = story.getFirstStoryModel();
        // }
        fetchUserStory(fetchOptions);
    }

    private void setHighlightStoryItems(@NonNull final HighlightModel story) {
        final StoryViewerOptions fetchOptions = StoryViewerOptions.forHighlight(story.getId());
        fetchUserStory(fetchOptions);
    }

    private void setUserStoryItems() {
        final String username = options.getName();
        final StoryViewerOptions fetchOptions = StoryViewerOptions.forUser(options.getId(), username);
        fetchUserStory(fetchOptions);
    }

    private void setDirectStoryItems() {
        fetchStoryItem(options.getId());
    }

    private void setStoryItemsFromOptions() {
        fetchUserStory(options);
    }

    private void setArchiveStoryItems(@NonNull final ArchiveResponseItem story) {
        // final String storyMediaId = parseStoryMediaId(story.getId());
        // currentStoryUsername = model.getTitle();
        fetchUserStory(StoryViewerOptions.forStoryArchive(story.getId()));
    }

    /**
     * Parses the Story's media ID. For user stories this is a number, but for archive stories
     * this is "archiveDay:" plus a number.
     */
    private static String parseStoryMediaId(String rawId) {
        final String regex = "(?:archiveDay:)?(.+)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(rawId);
        if (matcher.matches() && matcher.groupCount() >= 1) {
            return matcher.group(1);
        }
        return rawId;
    }

    private void fetchUserStory(@NonNull final StoryViewerOptions fetchOptions) {
        final int tempActiveStoryItemIndex = 0;
        storiesService.getUserStory(fetchOptions, new ServiceCallback<StoryResponse>() {
            @Override
            public void onSuccess(final StoryResponse response) {
                activeStoryItemIndex.postValue(tempActiveStoryItemIndex);
                Reel reel = response.getReel();
                if (reel == null) {
                    // reel is null, check reels
                    final Map<String, Reel> reels = response.getReels();
                    if (reels != null && !reels.isEmpty()) {
                        // get first reel inside reels
                        final Map.Entry<String, Reel> entry = reels.entrySet().iterator().next();
                        reel = entry.getValue();
                    }
                }
                if (reel == null) {
                    // if reel is still null, abort
                    currentReel.postValue(null);
                    activeStoryItem.postValue(null);
                    return;
                }
                currentReel.postValue(reel);
                if (reel.getItems() == null || reel.getItems().isEmpty()) {
                    activeStoryItem.postValue(null);
                    return;
                }
                try {
                    final StoryMedia storyMedia = reel.getItems().get(tempActiveStoryItemIndex);
                    activeStoryItem.postValue(storyMedia);
                } catch (Exception e) {
                    Log.e(TAG, "onSuccess: ", e);
                    activeStoryItem.postValue(null);
                }
            }

            @Override
            public void onFailure(final Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void fetchStoryItem(final long mediaId) {
        final int tempActiveStoryItemIndex = 0;
        // storiesService.fetch(mediaId, new ServiceCallback<StoryModel>() {
        //     @Override
        //     public void onSuccess(final StoryModel result) {
        //         currentReel.postValue(Collections.singletonList(result));
        //         activeStoryItemIndex.postValue(tempActiveStoryItemIndex);
        //         if (result == null) {
        //             activeStoryItem.postValue(null);
        //             return;
        //         }
        //         activeStoryItem.postValue(result);
        //     }
        //
        //     @Override
        //     public void onFailure(final Throwable t) {
        //         Log.e(TAG, "onFailure: ", t);
        //     }
        // });
    }
}