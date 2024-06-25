export function saveExternalVisioConnector(externalVisioConnector) {

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

export function saveConnectorOrders(visioConnectors) {

  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio`, {
    method: 'PUT',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(visioConnectors),
  }).then(resp => {
    if (resp?.ok) {
      return resp.json();
    } else {
      throw new Error('Error when saving external visio connectors');
    }
  });
}

export function updateExternalVisioConnector(externalVisioConnector) {

  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio/${externalVisioConnector.id}`, {
    method: 'PUT',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(externalVisioConnector),
  }).then((resp) => {
    if (!resp || !resp.ok) {
      throw new Error('Error updating ExternalVisioConnector');
    }
  });
}


export function deleteExternalVisioConnector(externalVisioConnector) {

  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio/${externalVisioConnector.id}`, {
    method: 'DELETE',
    credentials: 'include',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(externalVisioConnector),
  }).then((resp) => {
    if (!resp || !resp.ok) {
      throw new Error('Error deleting ExternalVisioConnector');
    }
  });
}
