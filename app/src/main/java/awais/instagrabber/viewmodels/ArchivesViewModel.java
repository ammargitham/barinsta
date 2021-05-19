package awais.instagrabber.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import awais.instagrabber.repositories.responses.story.StoryArchiveResponse.ArchiveResponseItem;

public class ArchivesViewModel extends ViewModel implements StoriesViewModel<ArchiveResponseItem> {
    private final MutableLiveData<List<ArchiveResponseItem>> list = new MutableLiveData<>();

    public LiveData<List<ArchiveResponseItem>> getList() {
        return list;
    }

    @Override
    public void setList(final List<ArchiveResponseItem> result) {
        list.postValue(result);
    }
}