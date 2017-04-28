import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {Login} from './login/login';
import {Users} from './users/users';
import {User} from './users/user';
import {SubscribeComponent} from './subscribe/subscribe.component';

export const routes:Routes = [
	{path: '', component: Login},
    {path: 'authenticate/:code', component: Login},
	{path: 'users', component: Users, },
    {path: 'user/:id', component: User,},
    {path: 'subscribe', component: SubscribeComponent}
];

export const RoutesModule: ModuleWithProviders = RouterModule.forRoot(routes);

