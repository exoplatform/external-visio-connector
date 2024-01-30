export function saveExternalVisioConnector(externalVisioName, isUserConnector, isSpaceConnector) {
  const externalVisioConnector = {
    name: externalVisioName,
    activeForUsers: isUserConnector,
    activeForSpaces: isSpaceConnector,
    enabled: isUserConnector || isSpaceConnector
  };
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio`, {
    method: 'POST',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(externalVisioConnector),
  }).then((resp) => {
    if (!resp || !resp.ok) {
      throw new Error('Error saving ExternalVisioConnector');
    }
  });
}

export function getExternalVisioConnectors(enabled) {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio/connectors?enabled=${enabled}`, {
    credentials: 'include',
    method: 'GET'
  }).then(resp => {
    if (resp?.ok) {
      return resp.json();
    } else {
      throw new Error('Error when retrieving external visio connectors');
    }
  });
}
  