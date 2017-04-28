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
const router_1 = require("@angular/router");
const person_1 = require("./person");
const subscribe_service_1 = require("./subscribe.service");
let SubscribeComponent = class SubscribeComponent {
    constructor(router, subscribeService) {
        this.subscribeService = subscribeService;
        this.person = new person_1.Person('', '', '', '');
        this.active = true;
        this.passwordFieldType = 'password';
        this.captchaUrl = 'api/showCaptcha';
        this.router = router;
    }
    newPerson() {
        this.person = new person_1.Person('', '', '', '');
        this.active = false;
        setTimeout(() => this.active = true, 0);
    }
    get diagnostic() {
        return JSON.stringify(this.person);
    }
    hideShowPassword() {
        if (this.passwordFieldType == 'password')
            this.passwordFieldType = 'text';
        else
            this.passwordFieldType = 'password';
    }
    onBlurEmail() {
        console.log('ONBLUREMAIL');
    }
    register() {
        this.subscribeService.post(this.person).then(rc => {
            this.returnedCode = rc;
            console.log('returnedCode: ' + rc);
            if (rc == 'registration_completed') {
                this.router.navigate(['/authenticate', rc]);
            }
            if (rc == 'wrong_captcha') {
                this.reloadCaptcha();
            }
        });
    }
    reloadCaptcha() {
        /*this.router.navigate(['/authenticate', 'registration_completed']); */
        if (this.captchaUrl.includes('captchaTempArg1'))
            this.captchaUrl = 'api/showCaptcha?captchaTempArg2=1';
        else
            this.captchaUrl = 'api/showCaptcha?captchaTempArg1=1';
    }
    ngOnInit() { }
};
SubscribeComponent = __decorate([
    core_1.Component({
        selector: 'subscription',
        templateUrl: 'app/subscribe/subscribe.component.html',
        styleUrls: ['app/subscribe/subscribe.component.css']
    }),
    __metadata("design:paramtypes", [router_1.Router, subscribe_service_1.SubscribeService])
], SubscribeComponent);
exports.SubscribeComponent = SubscribeComponent;
//# sourceMappingURL=subscribe.component.js.map