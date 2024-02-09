package org.exoplatform.webconferencing.externalvisio.dao;

import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;
import org.exoplatform.webconferencing.externalvisio.entity.ExternalVisioConnectorEntity;
import org.exoplatform.webconferencing.externalvisio.entity.VideoConferenceEntity;
import org.exoplatform.webconferencing.externalvisio.rest.model.VideoConference;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

public class VideoConferenceDAO extends GenericDAOJPAImpl<VideoConferenceEntity, Long> {

  public List<VideoConference> getVideoConferences(long identityId) {
    TypedQuery<VideoConference> query = getEntityManager()
                                                                .createNamedQuery("VideoConference.getVideoConferences",
                                                                        VideoConference.class)
                                                                .setParameter("identity", identityId);

    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return Collections.emptyList();
    }
  }

}
