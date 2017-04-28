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
require("rxjs/add/operator/toPromise");
let SubscribeService = class SubscribeService {
    constructor(http) {
        this.http = http;
        this.subscribeUrl = 'api/register'; // URL to web api
    }
    // Subscribe
    post(person) {
        let headers = new http_1.Headers({
            'Content-Type': 'application/json'
        });
        return this.http
            .post(this.subscribeUrl, JSON.stringify(person), { headers: headers })
            .toPromise()
            .then(res => { console.log(res); return res.text(); })
            .catch(this.handleError);
        /*.then(res => {console.log(res.json()); return res.json() as Person; })*/
    }
    handleError(error) {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
};
SubscribeService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], SubscribeService);
exports.SubscribeService = SubscribeService;
//# sourceMappingURL=subscribe.service.js.map