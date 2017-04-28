"use strict";
//Headers HTTP
exports.HEADER_X_SECRET = 'X-Secret';
exports.HEADER_X_TOKEN_ACCESS = 'X-TokenAccess';
exports.HEADER_X_DIGEST = 'X-Digest';
exports.HEADER_X_ONCE = 'X-Once';
exports.HEADER_WWW_AUTHENTICATE = 'WWW-Authenticate';
exports.HEADER_AUTHENTICATION = 'Authentication';
exports.CSRF_CLAIM_HEADER = "X-HMAC-CSRF";
//Local storage keys
exports.STORAGE_ACCOUNT_TOKEN = 'hmacApp-account';
exports.STORAGE_SECURITY_TOKEN = 'hmacApp-security';
//Common http root api
exports.BACKEND_API_PATH = 'api';
exports.BACKEND_API_AUTHENTICATE_PATH = '/authenticate';
exports.BACKEND_API_ROOT_URL = 'api';
class UrlMatcher {
    static matches(url) {
        console.log("IN MATCHES" + url);
        return url.indexOf(exports.BACKEND_API_PATH) !== -1
            && url.indexOf(exports.BACKEND_API_PATH + exports.BACKEND_API_AUTHENTICATE_PATH) === -1;
    }
}
exports.UrlMatcher = UrlMatcher;
// *** OLD ***   comment by a.franceschini@reply.it
//  The following lines are not needed anymore. 
//   Since May 2017 we removed the address of the server from this file (and from server code)
//export const BACKEND_API_PATH:string = '/api';
//export const BACKEND_API_ROOT_URL:string = 'http://localhost:8080'+BACKEND_API_PATH;
//export const BACKEND_API_ROOT_URL:string = 'http://95.110.227.45:8080/reply-webseed-angular2-1.0'+BACKEND_API_PATH;
//# sourceMappingURL=app.utils.js.map