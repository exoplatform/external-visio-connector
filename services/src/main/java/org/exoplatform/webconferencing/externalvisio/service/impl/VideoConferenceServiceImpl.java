package org.exoplatform.webconferencing.externalvisio.service.impl;

import org.exoplatform.webconferencing.externalvisio.dao.VideoConferenceDAO;
import org.exoplatform.webconferencing.externalvisio.entity.VideoConferenceEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference;
import org.exoplatform.webconferencing.externalvisio.rest.util.EntityBuilder;
import org.exoplatform.webconferencing.externalvisio.service.VideoConferenceService;

import java.util.List;

public class VideoConferenceServiceImpl implements VideoConferenceService {
    private final VideoConferenceDAO videoConferenceDAO;
    public VideoConferenceServiceImpl(VideoConferenceDAO videoConferenceDAO) {
        this.videoConferenceDAO = videoConferenceDAO;
    }

    @Override
    public VideoConference saveVideoConference(VideoConferenceEntity videoConferenceEntity) {
        if (videoConferenceEntity == null) {
            throw new IllegalArgumentException("videoConferenceEntity is mandatory");
        }
        if(videoConferenceEntity.getId() == null) {
            videoConferenceEntity = videoConferenceDAO.create(videoConferenceEntity);
        } else {
            videoConferenceEntity = videoConferenceDAO.update(videoConferenceEntity);
        }
        return EntityBuilder.fromEntity(videoConferenceEntity);

    }

    @Override
    public List<VideoConference> getVideoConferences(long identityId) {
        return videoConferenceDAO.getVideoConferences(identityId);
    }
}
