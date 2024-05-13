/**
 * External visio provider module for Web Conferencing. This script will be used to add a
 * provider to Web Conferencing module and then handle calls for portal
 * user/groups.
 */
(function($, webConferencing) {
  "use strict";
  var globalWebConferencing = typeof eXo != "undefined" && eXo && eXo.webConferencing ? eXo.webConferencing : null;
  // Use webConferencing from global eXo namespace (for non AMD uses).
  // This can be actual when running the script outside the portal page - e.g.
  // on a custom call page.
  if (!webConferencing && globalWebConferencing) {
    webConferencing = globalWebConferencing;
  }

  if (webConferencing) {

    // Start with default logger, later in configure() we'll get it for the
    // provider.
    // We know it's jitsi here.
    var log = webConferencing.getLog("externalvisio");

    /**
     * An object that implements Web Conferencing SPI contract for a call
     * provider.
     */
    function ExternalVisioProvider() {

      var self = this;
      var settings;

      /**
       * Init Jitsi provider, it will be called by Web Conferencing
       * core on addProvider() method. It is assumed that the connector will
       * initialize internals depending on the given context.
       */
      this.init = function(context) {
        var process = $.Deferred();
        process.resolve();
        return process.promise();
      };

      /**
       * Set connector settings from the server-side. Will be called by script
       * of JitsiPortlet class.
       */
      this.configure = function(newSettings) {
        settings = newSettings;
      };

      this.linkSupported = true;

      /**
       * Jitsi supports group calls.
       */
      this.groupSupported = true;

      /**
       * MUST return a call type name. If several types supported, this one is
       * assumed as major one and it will be used for referring this connector
       * in getProvider() and similar methods. This type also should listed in
       * getSupportedTypes(). Call type is the same as used in user profile.
       */
      this.getType = function() {
        return 'externalVisio';
//        if (settings) {
//          return settings.type;
//        }
      };

      /**
      * Must return if the current provider support invited users
      */
      this.supportInvitedUsers = function() {
        return false;
      };

      /**
       * MUST return all call types supported by a connector.
       */
      this.getSupportedTypes = function() {
        return ['externalVisio'];
//        if (settings) {
//          return settings.supportedTypes;
//        }
      };

      /**
       * MUST return human-readable title of a connector.
       */
      this.getTitle = function() {
        return 'ExternalVisio';
      };

      var getActiveProviders = function(identityId) {
        return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/externalVisio/${identityId}`, {
          credentials: 'include',
          method: 'GET'
        }).then(resp => {
          if (resp.ok) {
            return resp.json();
          } else {
            throw new Error('Error when retrieving active providers');
          }
        });
      }
      var startCall = function(url) {
        if (!url.match(/^(https?:\/\/|\/portal\/)/)) {
          url = `//${url}`;
        }
        window.open(url, '_blank');
      }

      this.callButton = function(context, buttonType) {
        var button = $.Deferred();
        if (context && context.currentUser) {
          context.details().then(target => {
            if (!buttonType || buttonType === "vue") {
              let activeButtons = [];
              if (context.isSpace || context.isUser) {
              const identityId = context.isSpace ? context.spaceId : context.userId;
                getActiveProviders(identityId)
                .then((activeProviders) => {
                  activeButtons = activeProviders;
                  const buttonComponents = []; // Créer une liste pour stocker les composants Vue
                  activeButtons.forEach(p => {
                    const callSettings = {};
                    callSettings.target = target;
                    callSettings.context = context;
                    callSettings.provider = self;
                    callSettings.nameConnector = p.name;
                    callSettings.urlConnector = p.url;
                    callSettings.order = p.order;
                    callSettings.onCallOpen = () => {
                      startCall(callSettings.urlConnector);
                    };
                    callButton.init(callSettings).then(comp => {
                      // Ajouter le composant Vue à la liste
                      buttonComponents.push(comp);

                      if (buttonComponents.length === activeButtons.length) {
                        buttonComponents.sort((button1, button2) => {
                          return (button1.callSettings.order - button2.callSettings.order);
                        });
                        button.resolve(buttonComponents);
                      }
                    });
                  });
                });
              } else {
                button.resolve(activeButtons);
              }
            } else {
              const message = "Button type not supported: " + buttonType;
              log.error(message);
              button.reject(message);
            }
          }).catch(err => {
            // Gérer les erreurs
            if (err && err.code == "NOT_FOUND_ERROR") {
              button.reject(err.message);
            } else {
              var msg = "Error getting context details";
              log.error(msg, err);
              button.reject(msg, err);
            }
          });
        } else {
          var msg = "Not configured or empty context";
          log.error(msg);
          button.reject(msg);
        }
        return button.promise();
      };

      this.getCallId = function(context) {
        var process = $.Deferred();
        if (context.isUser) {
          process.resolve(context.currentUser.id);
        } else {
          Vue.prototype.$identityService.getIdentityById(context.spaceId)
            .then((identity) => process.resolve(identity.remoteId));
        }
        return process.promise();
      };

      var getCallUrl = function(callId) {
        var process = $.Deferred();
        getActiveProviders(callId)
          .then((activeProviders) => {
            if(activeProviders.length>0) {
              process.resolve(activeProviders[0].url);
            }
          });
        return process.promise();
      };
      this.getCallUrl = getCallUrl;

    };

    var provider = new ExternalVisioProvider();

    // Add ExternalVisio provider into webConferencing object of global eXo namespace
    // (for non AMD uses)
    if (globalWebConferencing) {
      globalWebConferencing.externalvisio = provider;
    } else {
      log.warn("eXo.webConferencing not defined");
    }

    log.trace("< Loaded at " + location.origin + location.pathname);
    return provider;
  } else {
    window.console &&
      window.console
        .log("WARN: webConferencing not given and eXo.webConferencing not defined. ExternalVisio provider registration skipped.");
  }
})($, webConferencing, callButton);
