export function saveVideoConference(videoConference) {

  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/videoConference`, {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(videoConference),
  }).then((resp) => {
    if (!resp || !resp.ok) {
      throw new Error('Error saving VideoConference');
    }
  });
}

export function getVideoConferences(identityId) {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/videoConference?identityId=${identityId}`, {
    credentials: 'include',
    method: 'GET'
  }).then(resp => {
    if (resp?.ok) {
      return resp.json();
    } else {
      throw new Error('Error when retrieving video conferences');
    }
  });
}


