/**
 * App module
 * Created by Michael DESIGAUD on 11/11/2016.
 */

import { NgModule }      from '@angular/core';
import { Http, XHRBackend, RequestOptions, HttpModule } from '@angular/http';
import { AccountEventsService } from './account/account.events.service';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { HmacHttpClient } from './utils/hmac-http-client';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { Header } from './header/header';
import {SubscribeComponent} from './subscribe/subscribe.component';
import {ForgotPwdComponent} from './subscribe/forgotPwd.component';
import {SubscribeService} from './subscribe/subscribe.service';
import {RepPwdValidator} from './subscribe/repPwdValidator.directive';
import { FormsModule} from '@angular/forms';


import { RoutesModule } from './app.routes';
import { AccountModule } from './account/account.module';
import { LoginModule } from './login/login.module';
import { UserModule } from './users/user.module';
import { UtilsModule } from './utils/utils.module';

@NgModule({
    imports:        [ HttpModule, RouterModule, BrowserModule, AccountModule, LoginModule, UserModule, UtilsModule, RoutesModule, FormsModule ],
    declarations:   [ AppComponent, Header, SubscribeComponent, ForgotPwdComponent, RepPwdValidator ],
    bootstrap:      [ AppComponent, Header ],
    providers:      [
        {provide: LocationStrategy, useClass: HashLocationStrategy},
        {
            provide: Http,
            useFactory: (xhrBackend: XHRBackend, requestOptions: RequestOptions, accountEventService: AccountEventsService) => {
               return new HmacHttpClient(xhrBackend, requestOptions, accountEventService);
            },
            deps: [XHRBackend, RequestOptions, AccountEventsService],
            multi: false
        }, SubscribeService]
})
export class AppModule { }
