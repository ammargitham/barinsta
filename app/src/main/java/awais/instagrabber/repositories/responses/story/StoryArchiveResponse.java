package awais.instagrabber.repositories.responses.story;

import androidx.annotation.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import awais.instagrabber.repositories.responses.MediaCandidate;
import awais.instagrabber.utils.Utils;

public class StoryArchiveResponse {
    private final List<ArchiveResponseItem> items;
    private final int numResults;
    private final boolean moreAvailable;
    private final String maxId;
    private final String status;

    public StoryArchiveResponse(final List<ArchiveResponseItem> items,
                                final int numResults,
                                final boolean moreAvailable,
                                final String maxId,
                                final String status) {
        this.items = items;
        this.numResults = numResults;
        this.moreAvailable = moreAvailable;
        this.maxId = maxId;
        this.status = status;
    }

    public List<ArchiveResponseItem> getItems() {
        return items;
    }

    public int getNumResults() {
        return numResults;
    }

    public boolean isMoreAvailable() {
        return moreAvailable;
    }

    public String getMaxId() {
        return maxId;
    }

    public String getStatus() {
        return status;
    }

    public static class ArchiveResponseItem {
        private final MediaCandidate coverImageVersion;
        private final long timestamp;
        private final int mediaCount;
        private final String id;
        private final String reelType;

        private LocalDateTime localDateTime;
        private String formattedLocalDateTime;

        public ArchiveResponseItem(final MediaCandidate coverImageVersion,
                                   final long timestamp,
                                   final int mediaCount,
                                   final String id,
                                   final String reelType) {
            this.coverImageVersion = coverImageVersion;
            this.timestamp = timestamp;
            this.mediaCount = mediaCount;
            this.id = id;
            this.reelType = reelType;
        }

        public MediaCandidate getCoverImageVersion() {
            return coverImageVersion;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public LocalDateTime getLocalDateTime() {
            if (localDateTime == null) {
                localDateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
            return localDateTime;
        }

        @NonNull
        public String getFormattedLocalDateTime() {
            // return Utils.datetimeParser.format(new Date(timestamp * 1000L));
            if (formattedLocalDateTime == null) {
                formattedLocalDateTime = getLocalDateTime().format(Utils.dateTimeFormatter);
            }
            return formattedLocalDateTime;
        }

        public int getMediaCount() {
            return mediaCount;
        }

        public String getId() {
            return id;
        }

        public String getReelType() {
            return reelType;
        }
    }
}
