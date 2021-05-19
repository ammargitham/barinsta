package awais.instagrabber.repositories.responses.story;

import java.util.List;

public class StoryCallToAction {
    private final List<Link> links;

    public StoryCallToAction(final List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinks() {
        return links;
    }

    public static class Link {
        private final int linkType;
        private final String webUri;
        private final String androidClass;
        // private final String package;
        private final String deeplinkUri;
        private final String callToActionTitle;
        private final String redirectUri;
        // "leadGenFormId":"",
        // "igUserId":"",
        private final String tapAndHoldContext;

        public Link(final int linkType,
                    final String webUri,
                    final String androidClass,
                    final String deeplinkUri,
                    final String callToActionTitle,
                    final String redirectUri,
                    final String tapAndHoldContext) {
            this.linkType = linkType;
            this.webUri = webUri;
            this.androidClass = androidClass;
            this.deeplinkUri = deeplinkUri;
            this.callToActionTitle = callToActionTitle;
            this.redirectUri = redirectUri;
            this.tapAndHoldContext = tapAndHoldContext;
        }

        public int getLinkType() {
            return linkType;
        }

        public String getWebUri() {
            return webUri;
        }

        public String getAndroidClass() {
            return androidClass;
        }

        public String getDeeplinkUri() {
            return deeplinkUri;
        }

        public String getCallToActionTitle() {
            return callToActionTitle;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public String getTapAndHoldContext() {
            return tapAndHoldContext;
        }
    }
}
