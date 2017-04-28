"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
const core_1 = require("@angular/core");
const http_1 = require("@angular/http");
require("rxjs/add/operator/map");
const account_1 = require("../account/account");
const account_events_service_1 = require("../account/account.events.service");
const securityToken_1 = require("../security/securityToken");
const AppUtils = require("../utils/app.utils");
const router_1 = require("@angular/router");
let LoginService = class LoginService {
    constructor(http, accountEventService, router) {
        this.http = http;
        this.accountEventService = accountEventService;
        this.router = router;
    }
    authenticate(username, password, code) {
        let headers = new http_1.Headers();
        headers.append('Content-Type', 'application/json');
        return this.http.post(AppUtils.BACKEND_API_ROOT_URL + AppUtils.BACKEND_API_AUTHENTICATE_PATH, JSON.stringify({ login: username, password: password, code: code }), { headers: headers })
            .map((res) => {
            let securityToken = new securityToken_1.SecurityToken({
                publicSecret: res.headers.get(AppUtils.HEADER_X_SECRET),
                securityLevel: res.headers.get(AppUtils.HEADER_WWW_AUTHENTICATE)
            });
            localStorage.setItem(AppUtils.CSRF_CLAIM_HEADER, res.headers.get(AppUtils.CSRF_CLAIM_HEADER));
            localStorage.setItem(AppUtils.STORAGE_ACCOUNT_TOKEN, res.text());
            localStorage.setItem(AppUtils.STORAGE_SECURITY_TOKEN, JSON.stringify(securityToken));
            let account = new account_1.Account(res.json());
            this.sendLoginSuccess(account);
            return account;
        });
    }
    sendLoginSuccess(account) {
        if (!account) {
            account = new account_1.Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
        }
        this.accountEventService.loginSuccess(account);
    }
    isAuthenticated() {
        return !!localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN);
    }
    removeAccount() {
        localStorage.removeItem(AppUtils.STORAGE_ACCOUNT_TOKEN);
        localStorage.removeItem(AppUtils.STORAGE_SECURITY_TOKEN);
        localStorage.removeItem(AppUtils.CSRF_CLAIM_HEADER);
    }
    logout(callServer = true) {
        console.log('Logging out');
        if (callServer) {
            console.log('Logging out call server');
            this.http.get(AppUtils.BACKEND_API_ROOT_URL + '/logout').subscribe(() => {
                this.accountEventService.logout(new account_1.Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN))));
                this.removeAccount();
                this.router.navigate(['/authenticate/default/']);
            });
        }
        else {
            this.removeAccount();
            this.router.navigate(['/authenticate/default/']);
        }
    }
    isAuthorized(roles) {
        let authorized = false;
        if (this.isAuthenticated() && roles) {
            let account = new account_1.Account(JSON.parse(localStorage.getItem(AppUtils.STORAGE_ACCOUNT_TOKEN)));
            if (account && account.authorities) {
                roles.forEach((role) => {
                    if (account.authorities.indexOf(role) !== -1) {
                        authorized = true;
                    }
                });
            }
        }
        return authorized;
    }
};
LoginService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http, account_events_service_1.AccountEventsService, router_1.Router])
], LoginService);
exports.LoginService = LoginService;
//# sourceMappingURL=login.service.js.map