window.require(['SHARED/webConferencing','SHARED/webConferencingPortlet','SHARED/webConferencing_externalvisio'], function(webConferencing, webConferencingPortlet, webConferencing_externalvisio) {
//  if (webConferencing_externalvisio) {
//    fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/externalvisio/connectorsettings`, {
//      credentials: 'include',
//      method: 'GET',
//    }).then((resp) => {
//      if (!resp || !resp.ok) {
//        throw new Error('Error while getting external visio provider configuration');
//      } else {
//        return resp.json();
//      }
//    }).then((data) => {
  const data = '';
  webConferencingPortlet.start();
  webConferencing_externalvisio.configure(data);
  webConferencing.addProvider(webConferencing_externalvisio);
//    });
//  }
});
