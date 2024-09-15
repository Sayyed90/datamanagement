import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { UserManagementComponent } from './user-management/user-management.component';
import { UserSignupComponent } from './user-signup/user-signup.component';
import { DataManagementComponent } from './data-management/data-management.component';
import { authGuard } from './auth.guard';

export const routes: Routes = [
    {
        path: '', redirectTo: '/login', pathMatch: 'full'
    },
    {
        path: 'login', component: UserManagementComponent
    },
    {
        path: 'signup', component: UserSignupComponent
    },

    {
        path: 'datapage', component: DataManagementComponent, canActivate: [authGuard]
    }
];


@NgModule({
imports:[RouterModule.forRoot(routes)],
exports: [RouterModule]
})
export class AppRoutingModule{}
