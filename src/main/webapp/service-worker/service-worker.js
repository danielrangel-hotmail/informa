
workbox.core.skipWaiting();

workbox.core.clientsClaim();

// (function () {
//   'use strict';
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   /**
//    * Adapts the service worker to its runtime environment.
//    *
//    * Mostly, this is used to mock out identifiers which are otherwise read
//    * from the global scope.
//    */
//   class Adapter {
//     constructor(scope) {
//       // Suffixing `ngsw` with the baseHref to avoid clash of cache names
//       // for SWs with different scopes on the same domain.
//       const baseHref = this.parseUrl(scope.registration.scope).path;
//       this.cacheNamePrefix = 'ngsw:' + baseHref;
//     }
//     /**
//      * Wrapper around the `Request` constructor.
//      */
//     newRequest(input, init) {
//       return new Request(input, init);
//     }
//     /**
//      * Wrapper around the `Response` constructor.
//      */
//     newResponse(body, init) { return new Response(body, init); }
//     /**
//      * Wrapper around the `Headers` constructor.
//      */
//     newHeaders(headers) { return new Headers(headers); }
//     /**
//      * Test if a given object is an instance of `Client`.
//      */
//     isClient(source) { return (source instanceof Client); }
//     /**
//      * Read the current UNIX time in milliseconds.
//      */
//     get time() { return Date.now(); }
//     /**
//      * Extract the pathname of a URL.
//      */
//     parseUrl(url, relativeTo) {
//       // Workaround a Safari bug, see
//       // https://github.com/angular/angular/issues/31061#issuecomment-503637978
//       const parsed = !relativeTo ? new URL(url) : new URL(url, relativeTo);
//       return { origin: parsed.origin, path: parsed.pathname, search: parsed.search };
//     }
//     /**
//      * Wait for a given amount of time before completing a Promise.
//      */
//     timeout(ms) {
//       return new Promise(resolve => { setTimeout(() => resolve(), ms); });
//     }
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//
//   function errorToString(error) {
//     if (error instanceof Error) {
//       return `${error.message}\n${error.stack}`;
//     }
//     else {
//       return `${error}`;
//     }
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   var __awaiter$3 = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
//     return new (P || (P = Promise))(function (resolve, reject) {
//       function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
//       function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
//       function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
//       step((generator = generator.apply(thisArg, _arguments || [])).next());
//     });
//   };
//   const DEBUG_LOG_BUFFER_SIZE = 100;
//   class DebugHandler {
//     constructor(driver, adapter) {
//       this.driver = driver;
//       this.adapter = adapter;
//       // There are two debug log message arrays. debugLogA records new debugging messages.
//       // Once it reaches DEBUG_LOG_BUFFER_SIZE, the array is moved to debugLogB and a new
//       // array is assigned to debugLogA. This ensures that insertion to the debug log is
//       // always O(1) no matter the number of logged messages, and that the total number
//       // of messages in the log never exceeds 2 * DEBUG_LOG_BUFFER_SIZE.
//       this.debugLogA = [];
//       this.debugLogB = [];
//     }
//     handleFetch(req) {
//       return __awaiter$3(this, void 0, void 0, function* () {
//         const [state, versions, idle] = yield Promise.all([
//           this.driver.debugState(),
//           this.driver.debugVersions(),
//           this.driver.debugIdleState(),
//         ]);
//         const msgState = `NGSW Debug Info:
//
// Driver state: ${state.state} (${state.why})
// Latest manifest hash: ${state.latestHash || 'none'}
// Last update check: ${this.since(state.lastUpdateCheck)}`;
//         const msgVersions = versions
//         .map(version => `=== Version ${version.hash} ===
//
// Clients: ${version.clients.join(', ')}`)
//         .join('\n\n');
//         const msgIdle = `=== Idle Task Queue ===
// Last update tick: ${this.since(idle.lastTrigger)}
// Last update run: ${this.since(idle.lastRun)}
// Task queue:
// ${idle.queue.map(v => ' * ' + v).join('\n')}
//
// Debug log:
// ${this.formatDebugLog(this.debugLogB)}
// ${this.formatDebugLog(this.debugLogA)}
// `;
//         return this.adapter.newResponse(`${msgState}
//
// ${msgVersions}
//
// ${msgIdle}`, { headers: this.adapter.newHeaders({ 'Content-Type': 'text/plain' }) });
//       });
//     }
//     since(time) {
//       if (time === null) {
//         return 'never';
//       }
//       let age = this.adapter.time - time;
//       const days = Math.floor(age / 86400000);
//       age = age % 86400000;
//       const hours = Math.floor(age / 3600000);
//       age = age % 3600000;
//       const minutes = Math.floor(age / 60000);
//       age = age % 60000;
//       const seconds = Math.floor(age / 1000);
//       const millis = age % 1000;
//       return '' + (days > 0 ? `${days}d` : '') + (hours > 0 ? `${hours}h` : '') +
//         (minutes > 0 ? `${minutes}m` : '') + (seconds > 0 ? `${seconds}s` : '') +
//         (millis > 0 ? `${millis}u` : '');
//     }
//     log(value, context = '') {
//       // Rotate the buffers if debugLogA has grown too large.
//       if (this.debugLogA.length === DEBUG_LOG_BUFFER_SIZE) {
//         this.debugLogB = this.debugLogA;
//         this.debugLogA = [];
//       }
//       // Convert errors to string for logging.
//       if (typeof value !== 'string') {
//         value = this.errorToString(value);
//       }
//       // Log the message.
//       this.debugLogA.push({ value, time: this.adapter.time, context });
//     }
//     errorToString(err) { return `${err.name}(${err.message}, ${err.stack})`; }
//     formatDebugLog(log) {
//       return log.map(entry => `[${this.since(entry.time)}] ${entry.value} ${entry.context}`)
//       .join('\n');
//     }
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   var __awaiter$4 = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
//     return new (P || (P = Promise))(function (resolve, reject) {
//       function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
//       function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
//       function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
//       step((generator = generator.apply(thisArg, _arguments || [])).next());
//     });
//   };
//   class IdleScheduler {
//     constructor(adapter, threshold, debug) {
//       this.adapter = adapter;
//       this.threshold = threshold;
//       this.debug = debug;
//       this.queue = [];
//       this.scheduled = null;
//       this.empty = Promise.resolve();
//       this.emptyResolve = null;
//       this.lastTrigger = null;
//       this.lastRun = null;
//     }
//     trigger() {
//       return __awaiter$4(this, void 0, void 0, function* () {
//         this.lastTrigger = this.adapter.time;
//         if (this.queue.length === 0) {
//           return;
//         }
//         if (this.scheduled !== null) {
//           this.scheduled.cancel = true;
//         }
//         const scheduled = {
//           cancel: false,
//         };
//         this.scheduled = scheduled;
//         yield this.adapter.timeout(this.threshold);
//         if (scheduled.cancel) {
//           return;
//         }
//         this.scheduled = null;
//         yield this.execute();
//       });
//     }
//     execute() {
//       return __awaiter$4(this, void 0, void 0, function* () {
//         this.lastRun = this.adapter.time;
//         while (this.queue.length > 0) {
//           const queue = this.queue;
//           this.queue = [];
//           yield queue.reduce((previous, task) => __awaiter$4(this, void 0, void 0, function* () {
//             yield previous;
//             try {
//               yield task.run();
//             }
//             catch (err) {
//               this.debug.log(err, `while running idle task ${task.desc}`);
//             }
//           }), Promise.resolve());
//         }
//         if (this.emptyResolve !== null) {
//           this.emptyResolve();
//           this.emptyResolve = null;
//         }
//         this.empty = Promise.resolve();
//       });
//     }
//     schedule(desc, run) {
//       this.queue.push({ desc, run });
//       if (this.emptyResolve === null) {
//         this.empty = new Promise(resolve => { this.emptyResolve = resolve; });
//       }
//     }
//     get size() { return this.queue.length; }
//     get taskDescriptions() { return this.queue.map(task => task.desc); }
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   function isMsgCheckForUpdates(msg) {
//     return msg.action === 'CHECK_FOR_UPDATES';
//   }
//   function isMsgActivateUpdate(msg) {
//     return msg.action === 'ACTIVATE_UPDATE';
//   }
//
//   function tryParseJSON (jsonString){
//     try {
//       return jsonString.json();
//     }
//     catch (e) { }
//
//     return false;
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   var __awaiter$5 = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
//     return new (P || (P = Promise))(function (resolve, reject) {
//       function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
//       function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
//       function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
//       step((generator = generator.apply(thisArg, _arguments || [])).next());
//     });
//   };
//   const IDLE_THRESHOLD = 5000;
//   const SUPPORTED_CONFIG_VERSION = 1;
//   const NOTIFICATION_OPTION_NAMES = [
//     'actions', 'badge', 'body', 'data', 'dir', 'icon', 'image', 'lang', 'renotify',
//     'requireInteraction', 'silent', 'tag', 'timestamp', 'title', 'vibrate'
//   ];
//   var DriverReadyState;
//   (function (DriverReadyState) {
//     // The SW is operating in a normal mode, responding to all traffic.
//     DriverReadyState[DriverReadyState["NORMAL"] = 0] = "NORMAL";
//     // The SW does not have a clean installation of the latest version of the app, but older
//     // cached versions are safe to use so long as they don't try to fetch new dependencies.
//     // This is a degraded state.
//     DriverReadyState[DriverReadyState["EXISTING_CLIENTS_ONLY"] = 1] = "EXISTING_CLIENTS_ONLY";
//     // The SW has decided that caching is completely unreliable, and is forgoing request
//     // handling until the next restart.
//     DriverReadyState[DriverReadyState["SAFE_MODE"] = 2] = "SAFE_MODE";
//   })(DriverReadyState || (DriverReadyState = {}));
//   class Driver {
//     constructor(scope) {
//       // Set up all the event handlers that the SW needs.
//       this.scope = scope;
//       /**
//        * Tracks the current readiness condition under which the SW is operating. This controls
//        * whether the SW attempts to respond to some or all requests.
//        */
//       this.state = DriverReadyState.NORMAL;
//       this.stateMessage = '(nominal)';
//       /**
//        * Tracks whether the SW is in an initialized state or not. Before initialization,
//        * it's not legal to respond to requests.
//        */
//       this.initialized = null;
//       this.lastUpdateCheck = null;
//
//       // The activate event is triggered when this version of the service worker is
//       // first activated.
//       this.scope.addEventListener('activate', (event) => {
//         // Rather than wait for the first fetch event, which may not arrive until
//         // the next time the application is loaded, the SW takes advantage of the
//         // activation event to schedule initialization. However, if this were run
//         // in the context of the 'activate' event, waitUntil() here would cause fetch
//         // events to block until initialization completed. Thus, the SW does a
//         // postMessage() to itself, to schedule a new event loop iteration with an
//         // entirely separate event context. The SW will be kept alive by waitUntil()
//         // within that separate context while initialization proceeds, while at the
//         // same time the activation event is allowed to resolve and traffic starts
//         // being served.
//         if (this.scope.registration.active !== null) {
//           this.scope.registration.active.postMessage({ action: 'INITIALIZE' });
//         }
//       });
//
//       // Handle the fetch, message, and push events.
//       this.scope.addEventListener('message', (event) => this.onMessage(event));
//       this.scope.addEventListener('push', (event) => this.onPush(event));
//       this.scope.addEventListener('notificationclick', (event) => this.onClick(event));
//
//       // The debugger generates debug pages in response to debugging requests.
//       this.debugger = new DebugHandler(this, this.adapter);
//       // The IdleScheduler will execute idle tasks after a given delay.
//       this.idle = new IdleScheduler(this.adapter, IDLE_THRESHOLD, this.debugger);
//     }
//     /**
//      * The handler for message events.
//      */
//     onMessage(event) {
//       // Ignore message events when the SW is in safe mode, for now.
//       if (this.state === DriverReadyState.SAFE_MODE) {
//         return;
//       }
//       // If the message doesn't have the expected signature, ignore it.
//       const data = event.data;
//       if (!data || !data.action) {
//         return;
//       }
//       event.waitUntil((() => __awaiter$5(this, void 0, void 0, function* () {
//         // Initialization is the only event which is sent directly from the SW to itself, and thus
//         // `event.source` is not a `Client`. Handle it here, before the check for `Client` sources.
//         if (data.action === 'INITIALIZE') {
//           return this.ensureInitialized(event);
//         }
//         // Only messages from true clients are accepted past this point.
//         // This is essentially a typecast.
//         if (!this.adapter.isClient(event.source)) {
//           return;
//         }
//         // Handle the message and keep the SW alive until it's handled.
//         yield this.ensureInitialized(event);
//         yield this.handleMessage(data, event.source);
//       }))());
//     }
//     onPush(msg) {
//       // Push notifications without data have no effect.
//       if (!msg.data) {
//         return;
//       }
//       let dataJson = tryParseJSON(msg.data);
//       if (!dataJson) {
//         return;
//       }
//       // Handle the push and keep the SW alive until it's handled.
//       msg.waitUntil(this.handlePush(dataJson));
//     }
//     onClick(event) {
//       // Handle the click event and keep the SW alive until it's handled.
//       event.waitUntil(this.handleClick(event.notification, event.action));
//     }
//     ensureInitialized(event) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         // Since the SW may have just been started, it may or may not have been initialized already.
//         // `this.initialized` will be `null` if initialization has not yet been attempted, or will be a
//         // `Promise` which will resolve (successfully or unsuccessfully) if it has.
//         if (this.initialized !== null) {
//           return this.initialized;
//         }
//         // Initialization has not yet been attempted, so attempt it. This should only ever happen once
//         // per SW instantiation.
//         try {
//           this.initialized = this.initialize();
//           yield this.initialized;
//         }
//         catch (error) {
//           // If initialization fails, the SW needs to enter a safe state, where it declines to respond
//           // to network requests.
//           this.state = DriverReadyState.SAFE_MODE;
//           this.stateMessage = `Initialization failed due to error: ${errorToString(error)}`;
//           throw error;
//         }
//         finally {
//           // Regardless if initialization succeeded, background tasks still need to happen.
//           event.waitUntil(this.idle.trigger());
//         }
//       });
//     }
//     handleMessage(msg, from) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         if (isMsgCheckForUpdates(msg)) {
//           const action = (() => __awaiter$5(this, void 0, void 0, function* () { yield this.checkForUpdate(); }))();
//           yield this.reportStatus(from, action, msg.statusNonce);
//         }
//         else if (isMsgActivateUpdate(msg)) {
//           yield this.reportStatus(from, this.updateClient(from), msg.statusNonce);
//         }
//       });
//     }
//     handlePush(data) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         yield this.broadcast({
//           type: 'PUSH',
//           data,
//         });
//         if (!data.notification || !data.notification.title) {
//           return;
//         }
//         const desc = data.notification;
//         let options = {};
//         NOTIFICATION_OPTION_NAMES.filter(name => desc.hasOwnProperty(name))
//         .forEach(name => options[name] = desc[name]);
//         yield this.scope.registration.showNotification(desc['title'], options);
//       });
//     }
//     handleClick(notification, action) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         notification.close();
//         const options = {};
//         // The filter uses `name in notification` because the properties are on the prototype so
//         // hasOwnProperty does not work here
//         NOTIFICATION_OPTION_NAMES.filter(name => name in notification)
//         .forEach(name => options[name] = notification[name]);
//         yield this.broadcast({
//           type: 'NOTIFICATION_CLICK',
//           data: { action, notification: options },
//         });
//       });
//     }
//     reportStatus(client, promise, nonce) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         const response = { type: 'STATUS', nonce, status: true };
//         try {
//           yield promise;
//           client.postMessage(response);
//         }
//         catch (e) {
//           client.postMessage(Object.assign({}, response, { status: false, error: e.toString() }));
//         }
//       });
//     }
//     broadcast(msg) {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         const clients = yield this.scope.clients.matchAll();
//         clients.forEach(client => { client.postMessage(msg); });
//       });
//     }
//     debugState() {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         return {
//           state: DriverReadyState[this.state],
//           why: this.stateMessage,
//           latestHash: this.latestHash,
//           lastUpdateCheck: this.lastUpdateCheck,
//         };
//       });
//     }
//     debugVersions() {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         // Build list of versions.
//         return Array.from(this.versions.keys()).map(hash => {
//           const version = this.versions.get(hash);
//           const clients = Array.from(this.clientVersionMap.entries())
//           .filter(([clientId, version]) => version === hash)
//           .map(([clientId, version]) => clientId);
//           return {
//             hash,
//             manifest: version.manifest, clients,
//             status: '',
//           };
//         });
//       });
//     }
//     debugIdleState() {
//       return __awaiter$5(this, void 0, void 0, function* () {
//         return {
//           queue: this.idle.taskDescriptions,
//           lastTrigger: this.idle.lastTrigger,
//           lastRun: this.idle.lastRun,
//         };
//       });
//     }
//   }
//
//   /**
//    * @license
//    * Copyright Google Inc. All Rights Reserved.
//    *
//    * Use of this source code is governed by an MIT-style license that can be
//    * found in the LICENSE file at https://angular.io/license
//    */
//   const scope = self;
//   const adapter = new Adapter(scope);
//   const driver = new Driver(scope, adapter);
//
// }());

/**
 * The workboxSW.precacheAndRoute() method efficiently caches and responds to
 * requests for URLs in the manifest.
 * See https://goo.gl/S9QRab
 */
self.__precacheManifest = [].concat(self.__precacheManifest || []);
workbox.precaching.precacheAndRoute(self.__precacheManifest, {});
