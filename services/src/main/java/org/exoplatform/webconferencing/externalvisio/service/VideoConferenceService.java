package org.exoplatform.webconferencing.externalvisio.service;

import org.exoplatform.webconferencing.externalvisio.entity.VideoConferenceEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference;

import java.util.List;

public interface VideoConferenceService {

    public VideoConference saveVideoConference(VideoConferenceEntity videoConferenceEntity);

    public List<VideoConference> getVideoConferences(long identityId);
}
